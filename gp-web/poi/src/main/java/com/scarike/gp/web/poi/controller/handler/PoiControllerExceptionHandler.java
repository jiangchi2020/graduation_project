package com.scarike.gp.web.poi.controller.handler;

import com.scarike.gp.web.common.http.SimpleResponse;
import com.scarike.gp.web.poi.controller.PoiController;
import com.scarike.gp.web.poi.exception.IncomprehensibleQueryException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {PoiController.class})
public class PoiControllerExceptionHandler {
    @ExceptionHandler({IncomprehensibleQueryException.class})
    public SimpleResponse<Object> doOnIncomprehensibleQuery(IncomprehensibleQueryException e){
        return SimpleResponse.fail(e.getMessage());
    }
}
