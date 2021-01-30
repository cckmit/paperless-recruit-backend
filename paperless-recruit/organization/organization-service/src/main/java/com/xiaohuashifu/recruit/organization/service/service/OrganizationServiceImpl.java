package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.aspect.annotation.DistributedLock;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.constant.OrganizationConstants;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.assembler.OrganizationAssembler;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.oss.api.response.ObjectInfoResponse;
import com.xiaohuashifu.recruit.oss.api.service.ObjectStorageService;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final OrganizationAssembler organizationAssembler;

    /**
     * 组织默认角色编号
     */
    private static final Long ORGANIZATION_DEFAULT_ROLE_ID = 14L;

    /**
     * 创建组织邮件验证码标题
     */
    private static final String CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE = "创建组织";

    /**
     * 组织标签锁定键模式
     */
    private static final String ORGANIZATION_LABELS_LOCK_KEY_PATTERN = "organization:{0}:labels";

    /**
     * 组织名锁定键模式
     */
    private static final String ORGANIZATION_NAME_LOCK_KEY_PATTERN = "organization:name:{0}";

    public OrganizationServiceImpl(OrganizationMapper organizationMapper, OrganizationAssembler organizationAssembler) {
        this.organizationMapper = organizationMapper;
        this.organizationAssembler = organizationAssembler;
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
        OrganizationDO organizationDO = OrganizationDO.builder().userId(userDTO.getId()).build();
        organizationMapper.insertOrganization(organizationDO);

        // 获取组织
        return getOrganization(organizationDO.getId());
    }

    /**
     * 添加组织的标签
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotAvailable: 标签不可用
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该标签已经存在
     *              OperationConflict.OverLimit: 组织标签数量超过规定数量
     *              OperationConflict.Lock: 获取组织标签的锁失败
     *
     * @param id 组织编号
     * @param label 标签名
     * @return 添加后的组织对象
     */
    @Override
    @DistributedLock(value = ORGANIZATION_LABELS_LOCK_KEY_PATTERN, parameters = "#{#id}",
            errorMessage = "Failed to acquire organization labels lock.")
    public Result<OrganizationDTO> addLabel(Long id, String label) {
        // 检查组织状态
        Result<Object> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 判断标签数量是否大于 MAX_ORGANIZATION_LABEL_NUMBER
        int count = organizationMapper.countLabels(id);
        if (count >= OrganizationConstants.MAX_ORGANIZATION_LABEL_NUMBER) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT_OVER_LIMIT,
                    "The number of labels must not be greater than "
                            + OrganizationConstants.MAX_ORGANIZATION_LABEL_NUMBER + ".");
        }

        // 判断该标签是否可用
        Result<Void> checkOrganizationLabelResult = organizationLabelService.isValidOrganizationLabel(label);
        if (!checkOrganizationLabelResult.isSuccess()) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_AVAILABLE, "The label unavailable.");
        }

        // 添加组织的标签
        count = organizationMapper.addLabel(id, label);
        // 如果失败表示该标签已经存在
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT,
                    "The organization already owns this label.");
        }

        // 组织标签的引用数增加
        organizationLabelService.increaseReferenceNumberOrSaveOrganizationLabel(label);

        // 获取添加标签后的组织对象
        return getOrganization(id);
    }

    /**
     * 删除组织的标签
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict: 该标签不存在
     *
     * @param id 组织编号
     * @param label 标签名
     * @return 删除标签后的组织
     */
    @Override
    public Result<OrganizationDTO> removeLabel(Long id, String label) {
        // 检查组织状态
        Result<Object> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 删除标签
        int count = organizationMapper.removeLabel(id, label);
        // 如果删除失败表示该标签不存在
        if (count < 1) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The label does not exist.");
        }

        // 获取删除标签后的组织对象
        return getOrganization(id);
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
        return Result.success(organizationAssembler.organizationDOToOrganizationDTO(organizationDO));
    }

    /**
     * 获取组织
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 用户编号格式错误
     *              InvalidParameter.NotFound: 该用户编号的组织不存在
     *
     * @param userId 用户编号
     * @return OrganizationDTO
     */
    @Override
    public Result<OrganizationDTO> getOrganizationByUserId(Long userId) {
        OrganizationDO organizationDO = organizationMapper.getOrganizationByUserId(userId);
        // 组织不存在
        if (organizationDO == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_FOUND,
                    "The organization does not exist.");
        }

        // 封装成 DTO
        return Result.success(organizationAssembler.organizationDOToOrganizationDTO(organizationDO));
    }

    /**
     * 查询组织
     *
     * @errorCode InvalidParameter: 查询参数格式错误
     *
     * @param query 查询参数
     * @return PageInfo<OrganizationDTO> 查询结果，可能返回空列表
     */
    @Override
    public Result<PageInfo<OrganizationDTO>> listOrganizations(OrganizationQuery query) {
        List<OrganizationDO> organizationDOList = organizationMapper.listOrganizations(query);
        List<OrganizationDTO> organizationDTOList = organizationDOList
                .stream()
                .map(organizationAssembler::organizationDOToOrganizationDTO)
                .collect(Collectors.toList());
        PageInfo<OrganizationDTO> pageInfo = new PageInfo<>(organizationDTOList);
        return Result.success(pageInfo);
    }

    /**
     * 更新组织名
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 组织编号或组织名格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
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
        Result<Object> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
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
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 组织编号或组织名缩写格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @param newAbbreviationOrganizationName 新组织名缩写
     * @return 更新后的组织
     */
    @Override
    public Result<OrganizationDTO> updateAbbreviationOrganizationName(Long id, String newAbbreviationOrganizationName) {
        // 检查组织状态
        Result<Object> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新组织名缩写
        organizationMapper.updateAbbreviationOrganizationName(id, newAbbreviationOrganizationName);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    /**
     * 更新组织介绍
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 组织编号或组织介绍格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @param newIntroduction 新组织介绍
     * @return 更新后的组织
     */
    @Override
    public Result<OrganizationDTO> updateIntroduction(Long id, String newIntroduction) {
        // 检查组织状态
        Result<Object> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 更新组织介绍
        organizationMapper.updateIntroduction(id, newIntroduction);

        // 获取更新后的组织对象
        return getOrganization(id);
    }

    /**
     * 更新组织 Logo
     *
     * @permission 必须是该组织的主体用户
     *
     * @errorCode InvalidParameter: 更新参数格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *              OperationConflict.Lock: 获取组织 logo 的锁失败
     *              UnprocessableEntity.NotExist 所要链接的对象不存在
     *              OperationConflict.Linked 对象已经链接
     *              OperationConflict.Deleted 对象已经删除
     *              InternalError 链接对象失败
     *
     * @param id 组织编号
     * @param logoUrl logoUrl
     * @return 更新后的组织
     */
    @Override
    public Result<OrganizationDTO> updateLogo(Long id, String logoUrl) {
        // 检查组织状态
        Result<Object> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return Result.fail(checkResult);
        }

        // 链接 logo
        Result<ObjectInfoResponse> linkResult = objectStorageService.linkObject(logoUrl);
        if (linkResult.isFailure()) {
            return Result.fail(linkResult);
        }

        // 更新 logoUrl 到数据库
        organizationMapper.updateLogoUrl(id, logoUrl);

        // 更新后的组织信息
        return getOrganization(id);
    }

    /**
     * 禁用组织，禁用组织会导致组织主体无法再对组织进行操作，且组织无法报名等
     *
     * @permission 必须是管理员
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 组织已经被禁用
     *
     * @param id 组织编号
     * @return 禁用后的组织
     */
    @Override
    public Result<OrganizationDTO> disableOrganization(Long id) {
        // 判断组织存不存在
        Boolean available = organizationMapper.getAvailable(id);
        if (available == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organization does not exist.");
        }

        // 判断组织是否已经被禁用
        if (!available) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The organization already unavailable.");
        }

        // 禁用组织
        organizationMapper.updateAvailable(id, false);

        // 获取禁用后的组织对象
        return getOrganization(id);
    }

    /**
     * 解禁组织
     *
     * @permission 必须是管理员
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              OperationConflict: 组织已经可用
     *
     * @param id 组织编号
     * @return 解禁后的组织
     */
    @Override
    public Result<OrganizationDTO> enableOrganization(Long id) {
        // 判断组织存不存在
        Boolean available = organizationMapper.getAvailable(id);
        if (available == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organization does not exist.");
        }

        // 判断组织是否可用
        if (available) {
            return Result.fail(ErrorCodeEnum.OPERATION_CONFLICT, "The organization already available.");
        }

        // 解禁组织
        organizationMapper.updateAvailable(id, true);

        // 获取解禁后的组织对象
        return getOrganization(id);
    }

    /**
     * 发送注册账号时使用的邮箱验证码
     *
     * @errorCode InvalidParameter: 邮箱或标题格式错误
     *              OperationConflict: 该邮箱已经被注册，无法发送验证码
     *              UnknownError: 发送邮件验证码失败 | 邮箱地址错误 | 网络延迟
     *              TooManyRequests: 请求太频繁
     *
     * @param email 邮箱
     * @return 发送结果
     */
    @Override
    public Result<Void> sendEmailAuthCodeForSignUp(String email) {
        return userService.sendEmailAuthCodeForSignUp(email, CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE);
    }

    /**
     * 通过组织编号获取用户编号
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 用户编号，可能返回 null，若组织不存在
     */
    @Override
    public Long getUserId(Long id) {
        return organizationMapper.getUserId(id);
    }

    /**
     * 验证组织的主体
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @param userId 主体编号
     * @return 若是返回 true，不是返回 false
     */
    @Override
    public Boolean authenticatePrincipal(Long id, Long userId) {
        return Objects.equals(organizationMapper.getUserId(id), userId);
    }

    /**
     * 通过组织编号判断组织是否存在
     *
     * @private 内部方法
     *
     * @param id 组织编号
     * @return 组织是否存在
     */
    @Override
    public boolean organizationExists(Long id) {
        return organizationMapper.count(id) > 0;
    }

    /**
     * 增加成员数，+1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 增加成员数后的组织对象
     */
    @Override
    public Result<OrganizationDTO> increaseNumberOfMembers(Long id) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 增加成员数
        organizationMapper.increaseMemberNumber(id);

        // 添加成员数后的组织对象
        return getOrganization(id);
    }

    /**
     * 减少成员数，-1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 减少成员数后的组织对象
     */
    @Override
    public Result<OrganizationDTO> decreaseNumberOfMembers(Long id) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 减少成员数
        organizationMapper.decreaseMemberNumber(id);

        // 减少成员数后的组织对象
        return getOrganization(id);
    }

    /**
     * 部门数加1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 增加部门数后的组织对象
     */
    @Override
    public Result<OrganizationDTO> increaseNumberOfDepartments(Long id) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 增加部门数量
        organizationMapper.increaseNumberOfDepartments(id);

        // 增加部门数后的组织对象
        return getOrganization(id);
    }

    /**
     * 部门数减1
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter: 组织编号格式错误
     *              InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 减少部门数后的组织对象
     */
    @Override
    public Result<OrganizationDTO> decreaseNumberOfDepartments(Long id) {
        // 检查组织状态
        Result<OrganizationDTO> checkResult = checkOrganizationStatus(id);
        if (checkResult.isFailure()) {
            return checkResult;
        }

        // 增加部门数量
        organizationMapper.decreaseNumberOfDepartments(id);

        // 减少部门数后的组织对象
        return getOrganization(id);
    }

    /**
     * 检查组织是否存在，
     *
     * @private 内部方法
     *
     * @errorCode InvalidParameter.NotExist: 组织不存在
     *              Forbidden.Unavailable: 组织不可用
     *
     * @param id 组织编号
     * @return 检查结果
     */
    @Override
    public <T> Result<T> checkOrganizationStatus(Long id) {
        // 判断组织存不存在
        Boolean available = organizationMapper.getAvailable(id);
        if (available == null) {
            return Result.fail(ErrorCodeEnum.INVALID_PARAMETER_NOT_EXIST,
                    "The organization does not exist.");
        }

        // 判断组织是否可用
        if (!available) {
            return Result.fail(ErrorCodeEnum.FORBIDDEN_UNAVAILABLE);
        }

        // 通过检查
        return Result.success();
    }

    /**
     * 删除组织的标签，通过标签名
     * 小心使用，一次性会删除所有的拥有该标签的组织的这个标签
     *
     * @private 内部方法
     *
     * @param label 标签名
     * @return 被删除标签的组织数量
     */
    @Override
    public int removeLabels(String label) {
        return organizationMapper.removeLabels(label);
    }

}
