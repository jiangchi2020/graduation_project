package com.scarike.gp.web.grpc;


import lombok.Data;

@Data
public class Keyword {
    private QueryMethod type;
    private String center;
    private Integer distance;
    private Integer poiCode;
    private String key;

    public enum QueryMethod {
        POINT, LINE
    }
}
