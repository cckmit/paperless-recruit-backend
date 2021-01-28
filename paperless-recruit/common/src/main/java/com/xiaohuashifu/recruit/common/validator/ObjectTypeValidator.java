package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.ObjectType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * 描述: 对象类型校验器
 *
 * @author xhsf
 * @create 2021-1-29
 */
public class ObjectTypeValidator implements ConstraintValidator<ObjectType, String> {

    /**
     * 支持的对象类型
     */
    private String[] supportedObjectTypes;

    /**
     * 额外支持的对象类型
     */
    private String[] additionalSupportedObjectTypes;

    /**
     * 初始化
     *
     * @param objectType ObjectType
     */
    @Override
    public void initialize(ObjectType objectType) {
        this.supportedObjectTypes = objectType.supportedObjectTypes();
        this.additionalSupportedObjectTypes = objectType.additionalSupportedObjectTypes();
    }

    /**
     * 校验
     *
     * @param objectType 对象类型
     * @param constraintValidatorContext ConstraintValidatorContext
     * @return 是否通过校验
     */
    @Override
    public boolean isValid(String objectType, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (objectType == null || objectType.length() == 0) {
            return true;
        }

        // 判断是否在支持的对象类型列表里
        for (String supportedObjectType : supportedObjectTypes) {
            if (Objects.equals(supportedObjectType, objectType)) {
                return true;
            }
        }

        // 判断是否在额外支持的对象类型列表里
        for (String additionalSupportedObjectType: additionalSupportedObjectTypes) {
            if (Objects.equals(additionalSupportedObjectType, objectType)) {
                return true;
            }
        }

        // 对象类型不被支持，添加提示信息
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
        StringBuilder objectTypes = new StringBuilder();
        for (String supportedObjectType : supportedObjectTypes) {
            objectTypes.append(supportedObjectType).append(" | ");
        }
        for (String additionalSupportedObjectType : additionalSupportedObjectTypes) {
            objectTypes.append(additionalSupportedObjectType).append(" | ");
        }

        if (objectTypes.length() > 0) {
            objectTypes.delete(objectTypes.length() - 3, objectTypes.length());
        }

        objectTypes.insert(0, "The object type must be one of [");
        objectTypes.append("].");

        // 添加提示信息
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(objectTypes.toString())
                .addConstraintViolation();
    }

}
