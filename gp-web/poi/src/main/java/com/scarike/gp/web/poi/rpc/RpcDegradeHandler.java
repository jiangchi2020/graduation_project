package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.grpc.NlpResponse;
import io.grpc.StatusRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RpcDegradeHandler {

    private static final NlpResponse RPC_DEGRADE_RESPONSE;

    static {
        RPC_DEGRADE_RESPONSE = new NlpResponse();
        RPC_DEGRADE_RESPONSE.setStatus(1);
        RPC_DEGRADE_RESPONSE.setMessage("NLP Service Error");
    }

    @Around(value = "execution(* com.scarike.gp.web.poi.rpc.NLPService.*(..))")
    public Object doOnDegrade(ProceedingJoinPoint pjp) {
        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            if (throwable instanceof StatusRuntimeException) {
                result = RPC_DEGRADE_RESPONSE;
            } else {
                NlpResponse response = new NlpResponse();
                response.setStatus(1);
                response.setMessage(throwable.getMessage());
                result = response;
            }
        }
        return result;
    }
}
