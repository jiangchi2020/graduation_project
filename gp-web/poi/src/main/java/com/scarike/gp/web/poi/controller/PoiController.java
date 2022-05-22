package com.scarike.gp.web.poi.controller;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.grpc.Keyword;
import com.scarike.gp.web.common.http.SimpleResponse;
import com.scarike.gp.web.poi.rpc.NLPService;
import com.scarike.gp.web.poi.service.PoiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoiService service;

    @Autowired
    private NLPService nlpService;

    @GetMapping("/search")
    public SimpleResponse<List<Poi>> search(@RequestParam("query") String query,
                                            @RequestParam(value = "p", defaultValue = "0") long page,
                                            @RequestParam(value = "c", defaultValue = "10") int count,
                                            HttpServletResponse response) {
        Keyword keyword = nlpService.getBufferAnalysisKeyWord(query);
        log.info(keyword.toString());
        List<Poi> pois = service.searchPois(keyword, page, count);
        if (pois == null) {
            return SimpleResponse.fail();
        }
        response.setHeader("X-Keyword",keyword.toQuery());
        response.setHeader("Access-Control-Request-Headers","X-Keyword");
        return SimpleResponse.ok(pois);
    }

    @GetMapping("/more")
    public SimpleResponse<List<Poi>> more(@Valid Keyword keyword,
                                          @RequestParam(value = "p", defaultValue = "0") long page,
                                          @RequestParam(value = "c", defaultValue = "10") int count) {
        return SimpleResponse.ok(service.searchPois(keyword, page, count));
    }

    @GetMapping("/{code}/{pid}")
    public SimpleResponse<Poi> detail(@PathVariable("pid") Long pid, @PathVariable("code") String code) {
        return SimpleResponse.ok(service.getPoiById(pid, code));
    }

}
