package com.xiaohuashifu.recruit.external.service.pojo.dto;

/**
 * 描述: 封装AccessTokenDTO的各种属性
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-02-28 16:46
 */
public class AccessTokenDTO {

    private String access_token;
    private Integer expires_in;
    private Integer errcode;
    private String errmsg;

    public AccessTokenDTO() {
    }

    public AccessTokenDTO(String access_token, Integer expires_in, Integer errcode, String errmsg) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
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
        return "AccessTokenDTO{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
