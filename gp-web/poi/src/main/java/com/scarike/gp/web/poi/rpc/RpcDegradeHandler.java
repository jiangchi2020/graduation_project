package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.grpc.NlpResponse;

public interface RpcDegradeHandler {
    NlpResponse apply(Throwable e,Object... args);
}
