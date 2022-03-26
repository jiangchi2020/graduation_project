package com.scarike.gp.web.poi.controller;

import com.scarike.gp.web.common.entity.Poi;
import com.scarike.gp.web.common.http.SimpleResponse;
import com.scarike.gp.web.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoiService service;

    @GetMapping("/search")
    public SimpleResponse<List<Poi>> search(@RequestParam("query") String query) {
        List<Poi> pois = service.queryPois(query);
        if(pois==null){
            return SimpleResponse.fail();
        }
        return SimpleResponse.ok(pois);
    }

    @GetMapping("/{code}/{pid}")
    public SimpleResponse<Poi> detail(@PathVariable("pid") Long pid,@PathVariable("code") String code) {
        return SimpleResponse.ok(service.getPoiById(pid,code));
    }

}
