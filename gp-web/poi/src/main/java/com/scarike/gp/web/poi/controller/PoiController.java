package com.scarike.gp.web.poi.controller;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.grpc.Keyword;
import com.scarike.gp.web.common.http.SimpleResponse;
import com.scarike.gp.web.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoiService service;

    @GetMapping("/search")
    public SimpleResponse<List<Poi>> search(@RequestParam("query") String query,
                                            @RequestParam(value = "p", defaultValue = "0") int page,
                                            @RequestParam(value = "c", defaultValue = "10") int count) {
        List<Poi> pois = service.queryPois(query, page, count);
        if (pois == null) {
            return SimpleResponse.fail();
        }
        return SimpleResponse.ok(pois);
    }

    @GetMapping("/more")
    public SimpleResponse<List<Poi>> more(@Valid Keyword keyword,
                                          @RequestParam(value = "p", defaultValue = "0") int page,
                                          @RequestParam(value = "c", defaultValue = "10") int count) {
        return SimpleResponse.ok(service.searchPois(keyword, page, count));
    }

    @GetMapping("/{code}/{pid}")
    public SimpleResponse<Poi> detail(@PathVariable("pid") Long pid, @PathVariable("code") String code) {
        return SimpleResponse.ok(service.getPoiById(pid, code));
    }

}
