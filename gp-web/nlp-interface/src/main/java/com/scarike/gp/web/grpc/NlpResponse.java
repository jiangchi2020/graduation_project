package com.scarike.gp.web.grpc;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NlpResponse {
    private int status;
    private String message;
    private List<Keyword> keywords;

    public static NlpResponse from(RpcNlpResponse rpcNlpResponse) {
        NlpResponse response = new NlpResponse();
        int status = rpcNlpResponse.getStatus();
        response.setStatus(status);
        response.setMessage(rpcNlpResponse.getMessage());
        if (status == 0) {
            List<RpcNlpResponse.Keyword> keywordList = rpcNlpResponse.getKeywordList();
            List<Keyword> keywords = new ArrayList<>(keywordList.size());
            for (RpcNlpResponse.Keyword keyword : keywordList) {

                RpcNlpResponse.QueryType type = keyword.getType();
                if (type == RpcNlpResponse.QueryType.UNRECOGNIZED) {
                    response.setStatus(1);
                    response.setMessage("UNRECOGNIZED QUERY TYPE");
                    return response;
                }
                Keyword key = new Keyword();
                key.setCenter(keyword.getCenter());
                key.setDistance(keyword.getDistance());
                key.setPoiCode(keyword.getPoiCode());
                key.setType(type == RpcNlpResponse.QueryType.POINT ? Keyword.QueryMethod.POINT : Keyword.QueryMethod.LINE);
                key.setKey(keyword.getKey());
                keywords.add(key);
            }
            response.setKeywords(keywords);
        }
        return response;
    }
}
