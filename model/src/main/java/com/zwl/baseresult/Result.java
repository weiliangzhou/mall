package com.zwl.baseresult;


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
//    private PageResult pageRult;


    public Result() {
        this.setCode(ResultCodeEnum.SUCCESS);
        this.setMessage("成功！");
    }

    public Result(ResultCodeEnum code) {
        this.setCode(code);
        this.setMessage(code.msg());
    }

    public Result(ResultCodeEnum code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

 /*   public Result(ResultCodeEnum code, String message, Object data) {

        this.setCode(code);
        this.setMessage(message);

        //根据不同的结果 做处理
        if(data instanceof EntityBeanSet){
            //添加分页
            EntityBeanSet ebs = (EntityBeanSet)data;
            this.setPageCount(ebs.getPageCount());
            this.setPageNum(ebs.getPageNum());
            this.setData(ebs.getResult());
        }else{
            this.setData(data);
        }

    }*/

/*
    public String toString() {

        JSONObject json = new JSONObject();
        try {
            json.put("code", code);
            json.put("message", message);
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
*/

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
   /* public PageResult getPageRult() {
        return pageRult;
    }

    public void setPageRult(PageResult pageRult) {
        this.pageRult = pageRult;
    }*/

}
