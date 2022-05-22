package com.scarike.gp.web.common.grpc;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@EqualsAndHashCode
@ToString
@Getter
@Accessors
public class Keyword {
    private QueryMethod type;

    @NotBlank
    private String center;

    @Positive
    private int distance;

    private int poiCode;

    private int maskCode;

    private static final String POI_050000="poi_050000";
    private static final String POI_100000="poi_100000";
    private static final String POI_110000="poi_110000";
    private String poiTableName;

    public static final String STATION="station";
    public static final String RAILWAY="cn_railway_gcj02_3857";
    private String centerTableName;

    public Keyword setPoiCode(int poiCode) {
        this.poiCode = poiCode;
        switch (poiCode/10000){
            case 5:{this.poiTableName=POI_050000;break;}
            case 10:{this.poiTableName=POI_100000;break;}
            case 11:{this.poiTableName=POI_110000;break;}
            default:{
                throw new RuntimeException("未知的兴趣点编码");
            }
        }
        if (poiCode % 10000 == 0) {
            this.maskCode = 10000;
        } else if (poiCode % 100 == 0) {
            this.maskCode = 100;
        } else {
            this.maskCode = 1;
        }
        return this;
    }

    public Keyword setType(QueryMethod type) {
        this.type = type;
        switch (type){
            case LINE:{
                this.centerTableName=RAILWAY;break;
            }
            case POINT:{
                this.centerTableName=STATION;break;
            }
            default:{
                throw new RuntimeException("未知的查询类型");
            }
        }
        return this;
    }

    public Keyword setCenter(String center) {
        this.center = center;
        return this;
    }

    public Keyword setDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public enum QueryMethod {
        POINT, LINE
    }

    public static Keyword from(RpcNlpResponse rpcNlpResponse){
        if(rpcNlpResponse.getStatus()!=0){
            throw new NLPCallException(rpcNlpResponse.getMessage());
        }
        RpcNlpResponse.Keyword keyword = rpcNlpResponse.getKeyword();
        Keyword res=new Keyword();
        switch (keyword.getType()){
            case POINT:{
                res.setType(QueryMethod.POINT);break;
            }
            case LINE:{
                res.setType(QueryMethod.LINE);break;
            }
            default: throw new NLPCallException("UNKNOWN QUERY TYPE");
        }
        res.setCenter(keyword.getCenter());
        res.setDistance(keyword.getDistance());
        res.setPoiCode(keyword.getPoiCode());
        return res;
    }

    public String toQuery(){
        return "type="+type+"&center="+ URLEncoder.encode(center, StandardCharsets.UTF_8) +"&distance="+distance+"&poiCode="+poiCode;
    }
}
