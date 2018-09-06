package com.zwl.model.vo;

/**
 * 微信公众号获取JS权限返回信息
 */
public class JsApiTokenVo {

    /**
     * 获取错误编码
     */
    private Integer errcode;
    /**
     * 消息文本
     */
    private String errmsg;
    /**
     * 返回的ticket
     */
    private String ticket;
    /**
     * 有效期 7200 秒
     */
    private Long expires_in;

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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }
}
