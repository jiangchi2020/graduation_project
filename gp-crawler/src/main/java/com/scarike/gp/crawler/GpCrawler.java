package com.scarike.gp.crawler;

import com.scarike.gp.crawler.stations.I2306Crawler;
import com.scarike.gp.crawler.stations.LocationCrawler;
import com.scarike.gp.crawler.stations.entity.Station;
import com.scarike.gp.crawler.stations.entity.Train;
import lombok.AllArgsConstructor;

import java.util.*;

public class GpCrawler {
    public static Result crawl(String date) {
        Map<String, Station> stationMap = new HashMap<>(128);
        Set<Train> crawl = I2306Crawler.crawl(date, stationMap);
        final Station[] stationList = LocationCrawler.geoCode(stationMap);
        return new Result(crawl, stationList);
    }

    @AllArgsConstructor
    public static class Result {
        Set<Train> trainSet;
        Station[] stationList;
    }
}
