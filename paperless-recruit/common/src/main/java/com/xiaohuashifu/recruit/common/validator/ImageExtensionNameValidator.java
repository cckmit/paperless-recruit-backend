package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.ImageExtensionName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 描述: 图片扩展名校验器
 *
 * @author xhsf
 * @create 2020-12-13
 */
public class ImageExtensionNameValidator implements ConstraintValidator<ImageExtensionName, String> {

    /**
     * 支持的图片扩展名
     */
    private String[] supportImageExtensionNames;

    /**
     * 额外支持的图片扩展名
     */
    private String[] additionalSupportImageExtensionNames;

    /**
     * 初始化
     *
     * @param imageExtensionName ImageExtensionName
     */
    @Override
    public void initialize(ImageExtensionName imageExtensionName) {
        this.supportImageExtensionNames = imageExtensionName.supportedImageExtensionNames();
        this.additionalSupportImageExtensionNames = imageExtensionName.additionalSupportImageExtensionNames();
    }

    /**
     * 校验
     *
     * @param imageExtensionName 图片扩展名
     * @param constraintValidatorContext ConstraintValidatorContext
     * @return 是否通过校验
     */
    @Override
    public boolean isValid(String imageExtensionName, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (imageExtensionName == null || imageExtensionName.length() == 0) {
            return true;
        }

        // 判断是否在支持的扩展名列表里
        for (String supportImageExtensionName : supportImageExtensionNames) {
            if (Objects.equals(supportImageExtensionName, imageExtensionName)) {
                return true;
            }
        }

        // 判断是否在额外支持的扩展名列表里
        for (String additionalSupportImageExtensionName: additionalSupportImageExtensionNames) {
            if (Objects.equals(additionalSupportImageExtensionName, imageExtensionName)) {
                return true;
            }
        }

        // 扩展名不被支持，添加提示信息
        addViolationMessage(constraintValidatorContext);
        return false;
    }

    /**
     * 添加校验错误提示信息
     *
     * @param constraintValidatorContext ConstraintValidatorContext
     */
    private void addViolationMessage(ConstraintValidatorContext constraintValidatorContext) {
        // 构造提示信息
        StringBuilder extensionNames = new StringBuilder();
        for (String supportImageExtensionName : supportImageExtensionNames) {
            extensionNames.append(supportImageExtensionName).append(" | ");
        }
        for (String additionalSupportImageExtensionName: additionalSupportImageExtensionNames) {
            extensionNames.append(additionalSupportImageExtensionName).append(" | ");
        }

        if (extensionNames.length() > 0) {
            extensionNames.delete(extensionNames.length() - 3, extensionNames.length());
        }

        extensionNames.insert(0, "The extension name must be one of [");
        extensionNames.append("].");

        // 添加提示信息
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(extensionNames.toString())
                .addConstraintViolation();
    }

}
