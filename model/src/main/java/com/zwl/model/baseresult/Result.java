package com.zwl.model.baseresult;


import com.terran4j.commons.api2doc.annotations.ApiComment;

/**
 * 统一的Json返回类
 */
public class Result {
    @ApiComment(value = "code", sample = "200 205 4031 900 ")
    private String code;
    @ApiComment(value = "code", sample = "成功 操作失败 参数为空 token无效")
    private String message;
    @ApiComment(value = "data", sample = "数据")
    private Object data;

    public Result() {
        this.setCode("200");
        this.setData("");
        this.setMessage("成功！");
    }

    public Result(ResultCodeEnum code) {
        this.setCode(code);
        this.setData("");
        this.setMessage(code.msg());
    }

    public Result(ResultCodeEnum code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public Result(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public Result(String code, String message, Object o) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(o);
    }

    public String getCode() {
        return code;
    }

    public void setCode(ResultCodeEnum code) {
        this.code = code.val();
        this.message = code.msg();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
