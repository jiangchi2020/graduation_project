package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.common.grpc.Keyword;
import com.scarike.gp.web.common.grpc.RpcNlpResponse;
import org.springframework.stereotype.Component;

@Component
public class DevRpcDegradeHandler implements RpcDegradeHandler {
    private final static RpcNlpResponse[] RESPONSE_EXAMPLE_POOL = {
            RpcNlpResponse.newBuilder().setStatus(0).setMessage("OK")
                    .setKeyword(
                            RpcNlpResponse.Keyword.newBuilder()
                                    .setCenter("合安高铁")
                                    .setType(RpcNlpResponse.QueryType.LINE)
                                    .setDistance(3000)
                                    .setPoiCode(110000)
                                    .build()
                    ).build(),
            RpcNlpResponse.newBuilder().setStatus(0).setMessage("OK")
                    .setKeyword(
                            RpcNlpResponse.Keyword.newBuilder()
                                    .setCenter("成渝高速线")
                                    .setType(RpcNlpResponse.QueryType.LINE)
                                    .setDistance(1000)
                                    .setPoiCode(110000)
                                    .build()
                    ).build(),
            RpcNlpResponse.newBuilder().setStatus(0).setMessage("OK")
                    .setKeyword(
                            RpcNlpResponse.Keyword.newBuilder()
                                    .setCenter("京广高铁")
                                    .setType(RpcNlpResponse.QueryType.LINE)
                                    .setDistance(1000)
                                    .setPoiCode(110000)
                                    .build()
                    ).build(),
            RpcNlpResponse.newBuilder().setStatus(0).setMessage("OK")
                    .setKeyword(
                            RpcNlpResponse.Keyword.newBuilder()
                                    .setCenter("成都东")
                                    .setType(RpcNlpResponse.QueryType.POINT)
                                    .setDistance(1000)
                                    .setPoiCode(50000)
                                    .build()
                    ).build(),
            RpcNlpResponse.newBuilder().setStatus(0).setMessage("OK")
                    .setKeyword(
                            RpcNlpResponse.Keyword.newBuilder()
                                    .setCenter("合肥南")
                                    .setType(RpcNlpResponse.QueryType.POINT)
                                    .setDistance(1000)
                                    .setPoiCode(100000)
                                    .build()
                    ).build(),
            RpcNlpResponse.newBuilder().setStatus(0).setMessage("OK")
                    .setKeyword(
                            RpcNlpResponse.Keyword.newBuilder()
                                    .setCenter("天柱山")
                                    .setType(RpcNlpResponse.QueryType.POINT)
                                    .setDistance(10000)
                                    .setPoiCode(110000)
                                    .build()
                    ).build()};

    @Override
    public Keyword apply(Throwable e, Object... args) {
        e.printStackTrace();
        return Keyword.from(RESPONSE_EXAMPLE_POOL[(int) (Math.random()* RESPONSE_EXAMPLE_POOL.length)]);
    }
}
