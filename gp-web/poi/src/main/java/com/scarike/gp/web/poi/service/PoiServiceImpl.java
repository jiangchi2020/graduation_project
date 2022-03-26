package com.scarike.gp.web.poi.service;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.grpc.Keyword;
import com.scarike.gp.web.grpc.NlpResponse;
import com.scarike.gp.web.poi.dao.PoiDao;
import com.scarike.gp.web.poi.exception.IncomprehensibleQueryException;
import com.scarike.gp.web.poi.rpc.NLPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    public List<Poi> queryPois(String query) {
        NlpResponse result = nlpService.getBufferAnalysisKeyWord(query);
        log.info(result.toString());
        if (result.getStatus() != 0) {
            throw new IncomprehensibleQueryException(result.getMessage());
        }
        List<Keyword> keywords = result.getKeywords();
        List<Poi> poiList = new LinkedList<>();
        for (Keyword keyword : keywords) {
            queryPoisByKeyword(keyword, poiList);
        }
        return poiList;
    }

    private void queryPoisByKeyword(Keyword keyword, List<Poi> result) {
        int poiCode = keyword.getPoiCode();
        int maskCode;
        if (poiCode % 10000 == 0) {
            maskCode = 10000;
        } else if (poiCode % 100 == 0) {
            maskCode = 100;
        } else {
            maskCode = 1;
        }

        switch (keyword.getType()) {
            case POINT: {
                if (poiCode >= 120000) {
                    return;
                } else if (poiCode >= 110000) {
                    result.addAll(dao.pointBufferAnalysisFor11(keyword.getCenter(), keyword.getDistance(), poiCode, maskCode));
                } else if (poiCode >= 100000) {
                    result.addAll(dao.pointBufferAnalysisFor10(keyword.getCenter(), keyword.getDistance(), poiCode, maskCode));
                } else if (poiCode >= 50000 && poiCode < 60000) {
                    result.addAll(dao.pointBufferAnalysisFor05(keyword.getCenter(), keyword.getDistance(), poiCode, maskCode));
                } else {
                    return;
                }
                break;
            }
            case LINE: {
                if (poiCode >= 120000) {
                    return;
                } else if (poiCode >= 110000) {
                    result.addAll(dao.lineBufferAnalysisFor11(keyword.getCenter(), keyword.getDistance(), poiCode, maskCode));
                } else if (poiCode >= 100000) {
                    result.addAll(dao.lineBufferAnalysisFor10(keyword.getCenter(), keyword.getDistance(), poiCode, maskCode));
                } else if (poiCode >= 50000 && poiCode < 60000) {
                    result.addAll(dao.lineBufferAnalysisFor05(keyword.getCenter(), keyword.getDistance(), poiCode, maskCode));
                } else {
                    return;
                }
                break;
            }
            default: {
                throw new IncomprehensibleQueryException("KEYWORD_FORMAT_ERROR");
            }
        }

    }

}
