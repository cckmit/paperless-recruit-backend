package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.external.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.po.UpdateOrganizationLogoPO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationOrganizationLabelDO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.UUID;

/**
 * 描述：组织服务
 *
 * @author xhsf
 * @create 2020/12/8 21:56
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Reference
    private OrganizationLabelService organizationLabelService;

    @Reference
    private UserService userService;

    @Reference
    private RoleService roleService;

    @Reference
    private ObjectStorageService objectStorageService;

    private final OrganizationMapper organizationMapper;

    /**
     * 组织默认角色编号
     */
    private static final Long ORGANIZATION_DEFAULT_ROLE_ID = 13L;

    /**
     * 创建组织邮件验证码标题
     */
    private static final String CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE = "创建组织";

    /**
     * 组织最大的标签数
     */
    private static final int MAX_ORGANIZATION_LABEL_NUMBER = 3;

    /**
     * 组织标签锁定键模式
     */
    private static final String ORGANIZATION_LABELS_LOCK_KEY_PATTERN = "organization:{0}:labels";

    /**
     * 组织名锁定键模式
     */
    private static final String ORGANIZATION_NAME_LOCK_KEY_PATTERN = "organization:name:{0}";

    /**
     * 组织 logo 的锁定键模式
     */
    private static final String ORGANIZATION_LOGO_LOCK_KEY_PATTERN = "organization:{0}:logo";

    /**
     * 组织 logo url 的前缀
     */
    private static final String ORGANIZATION_LOGO_URL_PREFIX = "organization/logo/";

    public OrganizationServiceImpl(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    /**
     * 创建组织，需要没有使用过的邮箱，用于注册组织的主体账号
     *
     * @errorCode InvalidParameter: 邮箱或验证码或密码格式错误
     *              OperationConflict: 邮箱已经存在
     *              OperationConflict.Lock: 无法获取关于该邮箱的锁
     *              InvalidParameter.AuthCode.Incorrect: 邮箱验证码错误
     *              UnknownError: 注册主体失败
     *
     * @param email 组织主体的邮箱
     * @param authCode 邮箱验证码
     * @param password 密码
     * @return OrganizationDTO 组织对象
     */
    @Override
    public Result<OrganizationDTO> createOrganization(String email, String authCode, String password) {
        // 注册主体账号
        Result<UserDTO> signUpResult = userService.signUpByEmailAuthCode(email, authCode, password);
        // 注册失败
        if (!signUpResult.isSuccess()) {
            return Result.fail(signUpResult.getErrorCode(), signUpResult.getErrorMessage());
        }

        // 赋予主体组织的基本权限
        UserDTO userDTO = signUpResult.getData();
        roleService.saveUserRole(userDTO.getId(), ORGANIZATION_DEFAULT_ROLE_ID);

        // 创建组织
        OrganizationDO organizationDO = new OrganizationDO.Builder().userId(userDTO.getId()).build();
        organizationMapper.insertOrganization(organizationDO);

        // 获取组织
        return getOrganization(organizationDO.getId());
    }

    /**
     * 添加组织的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 组织标签数量超过规定数量
     *              OperationConflict.Lock: 获取组织标签的锁失败
     *
     * @param organizationId 组织编号
     * @param labelName 标签名
     * @return 添加后的组织对象
     */
    @Override
    @DistributedLock(value = ORGANIZATION_LABELS_LOCK_KEY_PATTERN, parameters = "#{#organizationId}",
            errorMessage = "Failed to acquire organization labels lock.")
    public Result<OrganizationDTO> addLabel(Long organizationId, String labelName) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(organizationId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断该组织是否已经存在该标签
        int count = organizationMapper.countLabelByOrganizationIdAndLabelName(organizationId, labelName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                    "The organization already owns this label.");
        }

        // 判断标签数量是否大于 MAX_ORGANIZATION_LABEL_NUMBER
        count = organizationMapper.countLabelByOrganizationId(organizationId);
        if (count >= MAX_ORGANIZATION_LABEL_NUMBER) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                    "The number of label must not be greater than "
                            + MAX_ORGANIZATION_LABEL_NUMBER + ".");
        }

        // 组织标签的引用数增加
        organizationLabelService.increaseReferenceNumberOrSaveOrganizationLabel(labelName);

        // 添加组织的标签
        OrganizationOrganizationLabelDO organizationOrganizationLabelDO =
                new OrganizationOrganizationLabelDO.Builder()
                        .organizationId(organizationId)
                        .labelName(labelName)
                        .build();
        organizationMapper.insertLabel(organizationOrganizationLabelDO);

        // 获取添加标签后的组织对象
        return getOrganization(organizationId);
    }

    /**
     * 删除组织的标签
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 该标签不存在
     *
     * @param organizationId 组织编号
     * @param labelName 标签名
     * @return 删除标签后的组织
     */
    @Override
    public Result<OrganizationDTO> removeLabel(Long organizationId, String labelName) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(organizationId);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断该组织是否拥有该标签
        int count = organizationMapper.countLabelByOrganizationIdAndLabelName(organizationId, labelName);
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label does not exist.");
        }

        // 删除标签
        organizationMapper.deleteLabelByOrganizationIdAndLabelName(organizationId, labelName);

        // 获取删除标签后的组织对象
        return getOrganization(organizationId);
    }

    /**
     * 获取组织
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotFound: 该编号的组织不存在
     *
     * @param id 组织编号
     * @return OrganizationDTO
     */
    @Override
    public Result<OrganizationDTO> getOrganization(Long id) {
        OrganizationDO organizationDO = organizationMapper.getOrganization(id);
        // 组织不存在
        if (organizationDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The organization does not exist.");
        }

        // 封装成 DTO
        List<String> labels = organizationMapper.listOrganizationLabelNamesByOrganizationId(organizationDO.getId());
        OrganizationDTO organizationDTO = new OrganizationDTO.Builder()
                .id(organizationDO.getId())
                .userId(organizationDO.getUserId())
                .organizationName(organizationDO.getOrganizationName())
                .abbreviationOrganizationName(organizationDO.getAbbreviationOrganizationName())
                .introduction(organizationDO.getIntroduction())
                .logoUrl(organizationDO.getLogoUrl())
                .memberNumber(organizationDO.getMemberNumber())
                .labels(labels)
                .available(organizationDO.getAvailable()).build();
        return Result.success(organizationDTO);
    }

    @Override
    public Result<PageInfo<OrganizationDTO>> listOrganizations(@NotNull OrganizationQuery query) {
        return null;
    }

    /**
     * 更新组织名
     *
     * @errorCode InvalidParameter: 组织编号或组织名格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              OperationConflict: 新组织名已经存在
     *              OperationConflict.Lock: 获取组织名的锁失败
     *
     * @param id 组织编号
     * @param newOrganizationName 新组织名
     * @return 更新后的组织
     */
    @Override
    @DistributedLock(value = ORGANIZATION_NAME_LOCK_KEY_PATTERN, parameters = "#{#newOrganizationName}",
            errorMessage = "Failed to acquire organizationName lock.")
    public Result<OrganizationDTO> updateOrganizationName(Long id, String newOrganizationName) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 判断组织名存不存在
        int count = organizationMapper.countByOrganizationName(newOrganizationName);
        if (count > 0) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The organization name already exist.");
        }

        // 更新组织名
        organizationMapper.updateOrganizationName(id, newOrganizationName);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    /**
     * 更新组织名缩写
     *
     * @errorCode InvalidParameter: 组织编号或组织名缩写格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *
     * @param id 组织编号
     * @param newAbbreviationOrganizationName 新组织名缩写
     * @return 更新后的组织
     */
    @Override
    public Result<OrganizationDTO> updateAbbreviationOrganizationName(Long id, String newAbbreviationOrganizationName) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 更新组织名缩写
        organizationMapper.updateAbbreviationOrganizationName(id, newAbbreviationOrganizationName);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    /**
     * 更新组织介绍
     *
     * @errorCode InvalidParameter: 组织编号或组织介绍格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *
     * @param id 组织编号
     * @param newIntroduction 新组织介绍
     * @return 更新后的组织
     */
    @Override
    public Result<OrganizationDTO> updateIntroduction(Long id, String newIntroduction) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 更新组织介绍
        organizationMapper.updateIntroduction(id, newIntroduction);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    /**
     * 更新组织 Logo
     *
     * @errorCode InvalidParameter: 组织编号或组织 logo 格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *              InternalError: 上传文件失败
     *              OperationConflict.Lock: 获取组织 logo 的锁失败
     *
     * @param updateOrganizationLogoPO 更新 logo 的参数对象
     * @return 更新后的组织
     */
    @Override
    @DistributedLock(value = ORGANIZATION_LOGO_LOCK_KEY_PATTERN, parameters = "#{#updateOrganizationLogoPO.id}",
            errorMessage = "Failed to acquire organization logo lock.")
    public Result<OrganizationDTO> updateLogo(UpdateOrganizationLogoPO updateOrganizationLogoPO) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(updateOrganizationLogoPO.getId());
        if (!checkResult.isSuccess()) {
            return checkResult;
        }

        // 获取组织 logoUrl
        String logoUrl = organizationMapper.getOrganizationLogoUrlByOrganizationId(updateOrganizationLogoPO.getId());
        // 若原来的 logoUrl 为空，则随机产生一个
        boolean needUpdateLogoUrl = false;
        if (StringUtils.isBlank(logoUrl)) {
            needUpdateLogoUrl = true;
            logoUrl = ORGANIZATION_LOGO_URL_PREFIX + UUID.randomUUID().toString()
                    + updateOrganizationLogoPO.getId() + updateOrganizationLogoPO.getLogoExtensionName();
        }

        // 更新 logo
        objectStorageService.putObject(logoUrl, updateOrganizationLogoPO.getLogo());

        // 若需要更新 logoUrl 到数据库，则更新
        if (needUpdateLogoUrl) {
            organizationMapper.updateLogoUrl(updateOrganizationLogoPO.getId(), logoUrl);
        }

        // 更新后的组织信息
        return getOrganization(updateOrganizationLogoPO.getId());
    }

    @Override
    public Result<Void> increaseMemberNumber(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<Void> decreaseMemberNumber(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> disableOrganization(@NotNull @Positive Long id) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> enableOrganization(@NotNull @Positive Long id) {
        return null;
    }

    /**
     * 检查组织状态
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden: 组织不可用
     *
     * @param organizationId 组织编号
     * @return 检查结果
     */
    @Override
    public Result<OrganizationDTO> checkOrganizationStatus(Long organizationId) {
        // 判断组织存不存在
        OrganizationDO organizationDO = organizationMapper.getOrganization(organizationId);
        if (organizationDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organization does not exist.");
        }

        // 判断组织是否可用
        if (!organizationDO.getAvailable()) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN, "The organization unavailable.");
        }

        return Result.success();
    }

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱或标题格式错误
     *              OperationConflict: 该邮箱已经被注册，无法发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *
     * @param email 邮箱
     * @return 发送结果
     */
    @Override
    public Result<Void> sendEmailAuthCodeForSignUp(String email) {
        return userService.sendEmailAuthCodeForSignUp(email, CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE);
    }

}
