package com.scarike.gp.web.poi.service;

import com.scarike.gp.web.common.entity.Poi;

import java.util.List;

public interface PoiService {
    Poi getPoiById(Long pid,String code);
    List<Poi> queryPois(String query);
}
