package com.scarike.gp.web.poi.service;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.grpc.Keyword;
import com.scarike.gp.web.common.grpc.NLPCallException;
import com.scarike.gp.web.poi.dao.PoiDao;
import com.scarike.gp.web.poi.rpc.NLPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PoiServiceImpl implements PoiService {

    @Autowired
    private PoiDao dao;

    @Autowired
    private NLPService nlpService;

    @Override
    public Poi getPoiById(Long pid, String code) {
        if (code.startsWith("05")) {
            return dao.selectPoiById(pid, "050000");
        } else if (code.startsWith("10")) {
            return dao.selectPoiById(pid, "100000");
        } else if (code.startsWith("11")) {
            return dao.selectPoiById(pid, "110000");
        } else {
            return null;
        }
    }

    @Override
    public List<Poi> queryPois(String query, int p, int c) {
        Keyword keyword = nlpService.getBufferAnalysisKeyWord(query);
        log.info(keyword.toString());
        return searchPois(keyword, p, c);
    }

    @Override
    public List<Poi> searchPois(Keyword keyword, int p, int c) {
        return dao.bufferAnalysis(keyword, p, c);
    }

}
