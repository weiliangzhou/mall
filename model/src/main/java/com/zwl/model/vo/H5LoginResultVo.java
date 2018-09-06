package com.zwl.model.vo;

/**
 * 用户登录信息返回
 */
public class H5LoginResultVo {
    /**
     * 用户tokent
     */
    private String tokent;
    /**
     * 公众号 openId
     */
    private String gzhOpenId;
    /**
     * 用户编号
     */
    private String userId;

    public H5LoginResultVo(String tokent, String gzhOpenId, String userId) {
        this.tokent = tokent;
        this.gzhOpenId = gzhOpenId;
        this.userId = userId;
    }

    public H5LoginResultVo() {
    }

    public String getTokent() {
        return tokent;
    }

    public void setTokent(String tokent) {
        this.tokent = tokent;
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
