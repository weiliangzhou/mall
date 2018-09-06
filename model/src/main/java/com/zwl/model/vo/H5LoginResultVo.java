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
    private Integer errorcode;
    private String errormsg;

    public H5LoginResultVo(String tokent, String gzhOpenId, String userId, Integer errorcode, String errorMsg) {
        this.token = tokent;
        this.gzhOpenId = gzhOpenId;
        this.userId = userId;
        this.errorcode = errorcode;
        this.errormsg = errorMsg;
    }

    public H5LoginResultVo() {
    }

    public Integer getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(Integer errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
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
}
