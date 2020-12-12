package com.xiaohuashifu.recruit.organization.service.service;

import com.github.pagehelper.PageInfo;
import com.xiaohuashifu.recruit.common.result.ErrorCodeEnum;
import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.organization.api.dto.OrganizationDTO;
import com.xiaohuashifu.recruit.organization.api.po.CreateOrganizationPO;
import com.xiaohuashifu.recruit.organization.api.query.OrganizationQuery;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationLabelService;
import com.xiaohuashifu.recruit.organization.api.service.OrganizationService;
import com.xiaohuashifu.recruit.organization.service.dao.OrganizationMapper;
import com.xiaohuashifu.recruit.organization.service.do0.OrganizationDO;
import com.xiaohuashifu.recruit.user.api.dto.UserDTO;
import com.xiaohuashifu.recruit.user.api.service.RoleService;
import com.xiaohuashifu.recruit.user.api.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;

import javax.validation.constraints.*;
import java.util.List;

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

    private final OrganizationMapper organizationMapper;

    /**
     * 组织默认角色编号
     */
    private static final Long ORGANIZATION_DEFAULT_ROLE_ID = 13L;

    /**
     * 创建组织邮件验证码标题
     */
    private static final String CREATE_ORGANIZATION_EMAIL_AUTH_CODE_TITLE = "创建组织";

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
     * 创建组织，需要没有使用过的邮箱，用于注册组织的主体账号
     *
     * @errorCode InvalidParameter: 创建参数格式错误 | 包含非法标签 |
     *
     * @param createOrganizationPO 创建组织的参数对象
     * @return OrganizationDTO
     */
    @Override
    public Result<OrganizationDTO> createOrganization(CreateOrganizationPO createOrganizationPO) {
        // 判断是否包含非法标签
        List<String> labels = createOrganizationPO.getLabels();
        if (labels != null) {
            for (String label : labels) {
                if (!organizationLabelService.isValidOrganizationLabel(label).isSuccess()) {
                    return Result.fail(ErrorCodeEnum.INVALID_PARAMETER, "Contains invalid labels.");
                }
            }
        }

        // 创建组织主体账号
//        userService.signUpUser()
        return null;
    }

    @Override
    public Result<Void> addLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName) {
        return null;
    }

    @Override
    public Result<Void> removeLabel(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 4) String labelName) {
        return null;
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

    @Override
    public Result<OrganizationDTO> updateOrganizationName(@NotNull @Positive Long id, @NotBlank @Size(min = 2, max = 20) String newOrganizationName) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateAbbreviationOrganizationName(@NotNull @Positive Long id, @NotBlank @Size(min = 2, max = 5) String newAbbreviationOrganizationName) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateIntroduction(@NotNull @Positive Long id, @NotBlank @Size(min = 1, max = 400) String newIntroduction) {
        return null;
    }

    @Override
    public Result<OrganizationDTO> updateLogo(@NotNull @Positive Long id, @NotEmpty @Size(min = 1, max = 10240) byte[] newLogo) {
        return null;
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
