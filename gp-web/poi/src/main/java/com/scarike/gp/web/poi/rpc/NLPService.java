package com.scarike.gp.web.poi.rpc;

import com.scarike.gp.web.grpc.NlpResponse;
import com.scarike.gp.web.grpc.RpcNlpRequest;
import com.scarike.gp.web.grpc.RpcNlpServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NLPService {

    @Autowired
    private RpcNlpServiceGrpc.RpcNlpServiceBlockingStub stub;

    public NlpResponse getBufferAnalysisKeyWord(String query){
        return NlpResponse.from(stub.analysis(RpcNlpRequest.newBuilder().setQuery(query).build()));
    }
}
