package com.scarike.gp.web.poi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.web.poi.dao.PoiDao;
import com.scarike.gp.web.poi.rpc.NLPService;
import com.scarike.gp.web.poi.service.PoiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PoiApplicationTests {

    @Autowired
    private PoiDao dao;

    @Autowired
    private PoiService service;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NLPService nlService;

    @Test
    void contextLoad() throws JsonProcessingException {
    }
}
