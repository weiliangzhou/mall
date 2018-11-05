package com.zwl.baseController;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;

/**
 * @author 二师兄超级帅
 * @Title: BaseController
 * @ProjectName parent
 * @Description: TODO
 * @date 2018/11/514:34
 */
public class BaseController {
    /**
     * 成功 没带data
     */
    public String setSuccessResult() {
        return JSON.toJSONString(new Result(BaseResultConstants.HTTP_CODE_200, BaseResultConstants.HTTP_CODE_200_VALUE, null));

    }

    /**
     * 成功 带data
     */
    public String setSuccessResult(Object data) {
        return JSON.toJSONString(new Result(BaseResultConstants.HTTP_CODE_200, BaseResultConstants.HTTP_CODE_200_VALUE, data));
    }

    /**
     * 成功  带信息
     */
    public String setSuccessResult(String code, String msg) {
        return JSON.toJSONString(new Result(code, msg, null));
    }

    /**
     * 失败
     */
    public String setFailResult(String code, String msg) {
        return JSON.toJSONString(new Result(code, msg, null));

    }

    /**
     * 失败
     */
    public String setFailResult(String code, String msg, Object data) {
        return JSON.toJSONString(new Result(code, msg, data));

    }
}
