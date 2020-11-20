package com.xiaohuashifu.recruit.external.service.pojo.dto;

/**
 * 描述:微信小程序接口的返回值
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-31 19:22
 */
public class WeChatMpResponseDTO {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    public WeChatMpResponseDTO() {
    }

    public WeChatMpResponseDTO(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "WeChatMpResponseDTO{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
