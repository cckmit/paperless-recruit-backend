package com.xiaohuashifu.recruit.registration.api.po;

import com.xiaohuashifu.recruit.common.validator.annotation.ImageExtensionName;
import com.xiaohuashifu.recruit.registration.api.constant.ApplicationFormConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 描述：报名表头像参数对象
 *
 * @author xhsf
 * @create 2020/12/28 22:08
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class ApplicationFormAvatarPO implements Serializable {

    /**
     * 头像
     */
    @NotNull(message = "The avatar can't be null.")
    @Size(max = ApplicationFormConstants.MAX_AVATAR_LENGTH, message = "The avatar must not be greater than "
            + ApplicationFormConstants.MAX_AVATAR_LENGTH + ".")
    protected byte[] avatar;

    /**
     * 头像的扩展名
     */
    @NotBlank(message = "The extensionName can't be blank.")
    @ImageExtensionName
    protected String extensionName;

}
