package com.zwl.model.exception;

import com.zwl.model.baseresult.Result;
import com.zwl.model.baseresult.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
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
    Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        Result response = new Result();
        response.setCode(ResultCodeEnum.FAIL);
        response.setMessage("操作失败！");
        return response;
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    Result handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        Result response = new Result();
        response.setCode(ResultCodeEnum.FAIL);
        response.setMessage(e.getMessage());
        return response;
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        Result response = new Result();
        response.setCode(ResultCodeEnum.FAIL);
        response.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }
}