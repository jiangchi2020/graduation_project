package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.common.grpc.Keyword;
import com.scarike.gp.web.common.grpc.RpcNlpRequest;
import com.scarike.gp.web.common.grpc.RpcNlpServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NLPService {

    @Autowired
    private RpcNlpServiceGrpc.RpcNlpServiceBlockingStub stub;

    public Keyword getBufferAnalysisKeyWord(String query){
        return Keyword.from(stub.analysis(RpcNlpRequest.newBuilder().setQuery(query).build()));
    }
}
