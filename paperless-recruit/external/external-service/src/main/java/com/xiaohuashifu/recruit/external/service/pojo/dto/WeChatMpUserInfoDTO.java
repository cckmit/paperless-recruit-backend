package com.xiaohuashifu.recruit.external.service.pojo.dto;

import com.xiaohuashifu.recruit.common.constant.GenderEnum;

/**
 * 描述：解密微信小程序的 encryptedData 后得到的数据
 *
 * @author xhsf
 * @create 2020/12/4 20:40
 */
public class WeChatMpUserInfoDTO {
    private String openId;
    private String nickName;
    private GenderEnum gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private WeChatMpWatermarkDTO watermark;

    public WeChatMpUserInfoDTO() {
    }

    public WeChatMpUserInfoDTO(String openId, String nickName, GenderEnum gender, String city, String province,
                               String country, String avatarUrl, String unionId, WeChatMpWatermarkDTO watermark) {
        this.openId = openId;
        this.nickName = nickName;
        this.gender = gender;
        this.city = city;
        this.province = province;
        this.country = country;
        this.avatarUrl = avatarUrl;
        this.unionId = unionId;
        this.watermark = watermark;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public WeChatMpWatermarkDTO getWatermark() {
        return watermark;
    }

    public void setWatermark(WeChatMpWatermarkDTO watermark) {
        this.watermark = watermark;
    }

    @Override
    public String toString() {
        return "WeChatMpUserInfoDTO{" +
                "openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", unionId='" + unionId + '\'' +
                ", watermark=" + watermark +
                '}';
    }
}
