package com.scarike.gp.web.poi.service;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.grpc.Keyword;

import java.util.List;

public interface PoiService {
    Poi getPoiById(Long pid,String code);
    List<Poi> searchPois(Keyword keyword,long p,int c);
}
