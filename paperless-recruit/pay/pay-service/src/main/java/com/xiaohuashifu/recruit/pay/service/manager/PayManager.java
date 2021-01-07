package com.xiaohuashifu.recruit.pay.service.manager;

import com.xiaohuashifu.recruit.common.result.Result;
import com.xiaohuashifu.recruit.pay.service.dto.CancelResultDTO;
import com.xiaohuashifu.recruit.pay.service.dto.PreCreateDTO;
import com.xiaohuashifu.recruit.pay.service.dto.QueryResultDTO;
import com.xiaohuashifu.recruit.pay.service.dto.RefundResultDTO;

/**
 * 描述：
 *
 * @author xhsf
 * @create 2021/1/6 19:48
 */
public interface PayManager {

    Result<String> preCreate(PreCreateDTO payPreCreateDTO);

    Result<QueryResultDTO> query(String orderNumber);

    Result<CancelResultDTO> cancel(String orderNumber);

    Result<RefundResultDTO> refund(String orderNumber, Integer refundAmount);
}
