package com.scarike.gp.crawler.pois.entity.resp;

import com.scarike.gp.crawler.exception.PoiSearchException;
import com.scarike.gp.crawler.pois.entity.Poi;
import lombok.Data;

import java.util.List;

@Data
public class PoiResp {
    private List<Poi> pois;
    private int count;
    private int status;
    private String info;

    public void setInfo(String info) {
        this.info = info;
        if(status==0){
            throw new PoiSearchException(info);
        }
    }
}
