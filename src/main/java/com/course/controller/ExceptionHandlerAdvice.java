package com.course.controller;


import com.course.vo.ApiError;
import com.course.vo.ResResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by linxiao on 2017/12/21.
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception ex) {
        ResResult resResult = new ResResult();
        resResult.setMsg("未知错误");
        resResult.setCode(500);

        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        resResult.setData( apiError );

        return new ResponseEntity<>(resResult, apiError.getStatus());
    }

}
