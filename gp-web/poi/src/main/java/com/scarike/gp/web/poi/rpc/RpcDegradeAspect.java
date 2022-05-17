package com.scarike.gp.web.poi.rpc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RpcDegradeAspect {

    @Autowired
    private RpcDegradeHandler handler;

    @Around(value = "execution(* com.scarike.gp.web.poi.rpc.NLPService.*(..))")
    public Object doOnDegrade2(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            result = handler.apply(throwable,pjp.getArgs());
        }
        return result;
    }
}
