package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.grpc.NlpResponse;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnMissingBean(RpcDegradeHandler.class)
public class DefaultRpcDegradeHandler implements RpcDegradeHandler {

    private static final NlpResponse RPC_DEGRADE_RESPONSE;

    static {
        RPC_DEGRADE_RESPONSE = new NlpResponse();
        RPC_DEGRADE_RESPONSE.setStatus(1);
        RPC_DEGRADE_RESPONSE.setMessage("NLP Service Error");
    }

    @Override
    public NlpResponse apply(Throwable e,Object... args) {
        log.error("Error For NLP Request:cased by:{}",e.toString());
        if (e instanceof StatusRuntimeException) {
            return RPC_DEGRADE_RESPONSE;
        } else {
            NlpResponse response = new NlpResponse();
            response.setStatus(1);
            response.setMessage(e.getMessage());
            return response;
        }
    }
}
