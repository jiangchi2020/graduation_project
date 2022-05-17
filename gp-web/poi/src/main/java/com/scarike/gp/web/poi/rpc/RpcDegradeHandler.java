package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.common.grpc.Keyword;

public interface RpcDegradeHandler {
    Keyword apply(Throwable e, Object... args);
}
