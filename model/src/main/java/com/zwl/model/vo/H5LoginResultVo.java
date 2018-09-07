package com.zwl.model.vo;

/**
 * 用户登录信息返回
 */
public class H5LoginResultVo {
    /**
     * 用户token
     */
    private String token;
    /**
     * 公众号 openId
     */
    private String gzhOpenId;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 是否第一次登录
     */
    private Boolean firstLogin;

    public H5LoginResultVo(String tokent, String gzhOpenId, String userId, Boolean firstLogin) {
        this.token = tokent;
        this.gzhOpenId = gzhOpenId;
        this.userId = userId;
        this.firstLogin = firstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGzhOpenId() {
        return gzhOpenId;
    }

    public void setGzhOpenId(String gzhOpenId) {
        this.gzhOpenId = gzhOpenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
