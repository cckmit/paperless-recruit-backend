package com.xiaohuashifu.recruit.pay.service.do0;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 描述：订单日志数据对象
 *
 * @author xhsf
 * @create 2021/1/8 00:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("trade_log")
public class TradeLogDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNumber;
    private Integer totalAmount;
    private String tradeSubject;
    private Integer expireTime;
    private String paymentMethod;
    private String qrCode;
    private String tradeNumber;
    private Integer buyerPayAmount;
    private String cancelAction;
    private String tradeStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
