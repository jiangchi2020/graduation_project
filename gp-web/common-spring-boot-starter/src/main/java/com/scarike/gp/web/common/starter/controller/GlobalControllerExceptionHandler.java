package com.scarike.gp.web.common.starter.controller;

import com.scarike.gp.web.common.grpc.NLPCallException;
import com.scarike.gp.web.common.http.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({NLPCallException.class})
    public SimpleResponse<Object> doOnIncomprehensibleQuery(NLPCallException e){
        return SimpleResponse.fail(e.getMessage());
    }
    @ExceptionHandler({BindException.class, MethodArgumentTypeMismatchException.class})
    public SimpleResponse<Object> doOnClientError(Exception e){
        return SimpleResponse.badRequest();
    }
    @ExceptionHandler(Exception.class)
    public SimpleResponse<Object> doOnOtherException(Exception e){
        log.error("未预料的异常：",e);
        return SimpleResponse.serverError();
    }
}
