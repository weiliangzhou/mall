package com.zwl.model.exception;

import com.alibaba.fastjson.JSON;
import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 二师兄超级帅 on 2018/7/8.
 * * @ControllerAdvice + @ExceptionHandler 实现全局的 Controller 层的异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    String handleException(Exception e) {
        log.error(e.getMessage(), e);
        Result response = new Result();
        response.setCode("205");
        response.setMessage("操作失败！");
        return JSON.toJSONString(response);
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    String handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        Result response = new Result();
        response.setCode("205");
        response.setMessage(e.getMessage());
        return JSON.toJSONString(response);
    }


    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    String handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        Result response = new Result();
        response.setCode("205");
        response.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return JSON.toJSONString(response);
    }
}