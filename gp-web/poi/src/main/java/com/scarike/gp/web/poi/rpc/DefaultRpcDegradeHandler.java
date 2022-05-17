package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.common.grpc.Keyword;
import com.scarike.gp.web.common.grpc.NLPCallException;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnMissingBean(RpcDegradeHandler.class)
public class DefaultRpcDegradeHandler implements RpcDegradeHandler {

    @Override
    public Keyword apply(Throwable e, Object... args) {
        log.error("Error For NLP Request:cased by:{}",e.toString());
        if (e instanceof StatusRuntimeException) {
            throw  new NLPCallException("服务状态异常");
        } else {
            throw new NLPCallException(e.getMessage(),e);
        }
    }

}
