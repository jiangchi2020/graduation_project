package com.scarike.gp.web.poi.service;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.grpc.Keyword;

import java.util.List;

public interface PoiService {
    Poi getPoiById(Long pid,String code);
    List<Poi> queryPois(String query,int p,int c);
    List<Poi> searchPois(Keyword keyword,int p,int c);
}
