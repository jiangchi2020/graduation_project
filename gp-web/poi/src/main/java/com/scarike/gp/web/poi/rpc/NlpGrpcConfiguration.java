package com.scarike.gp.web.poi.rpc;

import com.alibaba.csp.sentinel.adapter.grpc.SentinelGrpcClientInterceptor;
import com.scarike.gp.web.grpc.RpcNlpServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NlpGrpcConfiguration {
    @Bean
    public ManagedChannel managedChannel(){
        return ManagedChannelBuilder.forAddress("127.0.0.1", 20880)
                .usePlaintext()
                .intercept(new SentinelGrpcClientInterceptor())
                .build();
    }

    @Bean
    public RpcNlpServiceGrpc.RpcNlpServiceBlockingStub nlpServiceBlockingStub(ManagedChannel channel){
        return RpcNlpServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public RpcNlpServiceGrpc.RpcNlpServiceFutureStub nlpServiceFutureStub(ManagedChannel channel){
        return RpcNlpServiceGrpc.newFutureStub(channel);
    }

    @Bean
    public RpcNlpServiceGrpc.RpcNlpServiceStub nlpServiceStub(ManagedChannel channel){
        return RpcNlpServiceGrpc.newStub(channel);
    }
}
