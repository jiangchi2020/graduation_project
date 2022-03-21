package com.scarike.gp.web.poi.dao;

import com.scarike.gp.web.common.entity.Poi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PoiDao {
    Poi selectPoiById(@Param("pid") Long pid);
    List<Poi> pointBufferAnalysisFor05(@Param("center") String center,@Param("distance") Integer distance,@Param("typeCode") Integer typeCode,@Param("maskCode") Integer maskCode);
    List<Poi> pointBufferAnalysisFor10(@Param("center") String center,@Param("distance") Integer distance,@Param("typeCode") Integer typeCode,@Param("maskCode") Integer maskCode);
    List<Poi> pointBufferAnalysisFor11(@Param("center") String center,@Param("distance") Integer distance,@Param("typeCode") Integer typeCode,@Param("maskCode") Integer maskCode);
    List<Poi> lineBufferAnalysisFor05(@Param("center") String center,@Param("distance") Integer distance,@Param("typeCode") Integer typeCode,@Param("maskCode") Integer maskCode);
    List<Poi> lineBufferAnalysisFor10(@Param("center") String center,@Param("distance") Integer distance,@Param("typeCode") Integer typeCode,@Param("maskCode") Integer maskCode);
    List<Poi> lineBufferAnalysisFor11(@Param("center") String center,@Param("distance") Integer distance,@Param("typeCode") Integer typeCode,@Param("maskCode") Integer maskCode);
}
