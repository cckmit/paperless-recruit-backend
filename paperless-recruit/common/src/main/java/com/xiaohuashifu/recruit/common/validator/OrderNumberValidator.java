package com.xiaohuashifu.recruit.common.validator;

import com.xiaohuashifu.recruit.common.validator.annotation.OrderNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 订单号校验，必须 {7位业务号}{yyyyMMddHHmmss}{5位1秒内的自增序号} 全数字
 *
 * @author xhsf
 * @create 2021-01-08
 */
public class OrderNumberValidator implements ConstraintValidator<OrderNumber, String> {

    /**
     * 订单号正则
     */
    public static final String ORDER_NUMBER_REGEX =
            "^\\d{11}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])([01]\\d|2[0-3])([0-5]\\d){2}\\d{5}$";

    /**
     * 构造静态的匹配模式
     */
    public static final Pattern p = Pattern.compile(ORDER_NUMBER_REGEX);

    @Override
    public boolean isValid(String orderNumber, ConstraintValidatorContext constraintValidatorContext) {
        // 不检查空的情况
        if (orderNumber == null || orderNumber.length() == 0) {
            return true;
        }

        // 格式校验
        Matcher matcher = p.matcher(orderNumber);
        return matcher.matches();
    }

}
