package com.xiaohuashifu.recruit.pay.service.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 描述：订单查询结果数据传输对象
 *
 * @author xhsf
 * @create 2021/1/7 14:45
 */
@Data
@Builder
public class QueryResultDTO {

    private Integer buyerPayAmount;

    private Integer totalAmount;

    private String orderStatus;

}
