package com.xiaohuashifu.recruit.authentication.service.enhancer;

/**
 * 描述：JWT 里附加的用户信息
 *
 * @author: xhsf
 * @create: 2020/11/24 21:20
 */
public class JwtTokenUserInfo {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private Boolean available;

    public JwtTokenUserInfo() {}

    public JwtTokenUserInfo(Long id, String username, String phone, String email, Boolean available) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "JwtTokenUserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", available=" + available +
                '}';
    }
}
