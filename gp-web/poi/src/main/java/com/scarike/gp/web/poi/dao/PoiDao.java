package com.scarike.gp.web.poi.dao;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.grpc.Keyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PoiDao {
    Poi selectPoiById(@Param("pid") Long pid,@Param("poiCode") String poiCode);
    List<Poi> bufferAnalysis(@Param("key") Keyword key,@Param("p") int p,@Param("c") int c);
}
