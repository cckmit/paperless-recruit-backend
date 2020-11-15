package com.xiaohuashifu.recruit.common.filter;

import javax.validation.ConstraintViolation;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述：自定义的异常信息封装类
 *
 * @author: xhsf
 * @email: 827032783@qq.com
 * @create: 2020/11/15 17:20
 */
public class CustomConstraintViolation implements Serializable {
    private String interpolatedMessage;
    private String propertyPath;
    private String messageTemplate;
    private String rootBeanClass;

    public CustomConstraintViolation(String interpolatedMessage, String propertyPath, String messageTemplate,
                                     String rootBeanClass) {
        this.interpolatedMessage = interpolatedMessage;
        this.propertyPath = propertyPath;
        this.messageTemplate = messageTemplate;
        this.rootBeanClass = rootBeanClass;
    }

    public static CustomConstraintViolation buildCustomConstraintViolation(ConstraintViolation<?> constraintViolation) {
        return new CustomConstraintViolation(constraintViolation.getMessage(),
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessageTemplate(),
                constraintViolation.getRootBeanClass().getName());
    }

    public String getInterpolatedMessage() {
        return interpolatedMessage;
    }

    public void setInterpolatedMessage(String interpolatedMessage) {
        this.interpolatedMessage = interpolatedMessage;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public String getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public String getRootBeanClass() {
        return rootBeanClass;
    }

    public void setRootBeanClass(String rootBeanClass) {
        this.rootBeanClass = rootBeanClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomConstraintViolation that = (CustomConstraintViolation) o;
        return Objects.equals(interpolatedMessage, that.interpolatedMessage) &&
                Objects.equals(propertyPath, that.propertyPath) &&
                Objects.equals(messageTemplate, that.messageTemplate) &&
                Objects.equals(rootBeanClass, that.rootBeanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interpolatedMessage, propertyPath, messageTemplate, rootBeanClass);
    }

    @Override
    public String toString() {
        return "CustomConstraintViolationImpl{" +
                "interpolatedMessage='" + interpolatedMessage + '\'' +
                ", propertyPath=" + propertyPath +
                ", messageTemplate='" + messageTemplate + '\'' +
                ", rootBeanClass=" + rootBeanClass +
                '}';
    }
}

