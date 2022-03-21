package com.scarike.gp.crawler.stations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.crawler.stations.entity.Station;
import com.scarike.gp.crawler.stations.entity.resp.GeoCodeResp;
import com.scarike.gp.crawler.util.TaskTimer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LocationCrawler {
    public static final String URL = "https://restapi.amap.com/v3/geocode/geo?key=caf711833b9439071d21c857a8161343&batch=true&address=";

    public static Station[] geoCode(Map<String, Station> stationMap) {
        TaskTimer taskTimer =new TaskTimer("地理编码").begin();
        Station[] stations = new Station[stationMap.size()];
        Thread[] threads = new Thread[32];

        var ref = new Object() {
            int i = 0;
        };
        stationMap.forEach((k, v) -> {
            stations[ref.i++] = v;
        });

        int countPreThread = stations.length / 32 + 1;
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                int begin = finalI * countPreThread;
                int end = (finalI + 1) * countPreThread;
                if (end > stations.length)
                    end = stations.length;

                Station[] task=new Station[10];
                int k=0;
                for(int j=begin;j<end;j++){
                    task[k++]=stations[j];
                    if(k%10==0){
                        k=0;
                        geoCode(task,10);
                    }
                }
                if (k != 0) {
                    geoCode(task, k);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        taskTimer.end();
        return stations;
    }

    public static void geoCode(Station[] stations, int size) {
        StringBuilder sb = new StringBuilder(400);
        sb.append(URL);
        for (int i = 0; i < size - 1; i++) {
            sb.append(stations[i].getStation_name()).append("站").append('|');
        }
        sb.append(stations[size - 1].getStation_name()).append("站");

        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            GeoCodeResp resp = om.readValue(new URL(sb.toString()), GeoCodeResp.class);
            if (resp == null) {
                System.out.println("返回值为NULL");
                return;
            }
            if (resp.getStatus() != 1) {
                System.out.println("错误：" + resp.getInfo());
                return;
            }
            List<GeoCodeResp.GeoCode> geocodes = resp.getGeocodes();
            if (resp.getCount() != size || geocodes.size() != size) {
                System.out.println("错误：返回数目与传入数目不匹配");
                return;
            }
            int i = 0;
            for (GeoCodeResp.GeoCode geocode : geocodes) {
                Station station = stations[i];
                station.setAddress(geocode.getFormatted_address());
                station.setLon(geocode.getLon());
                station.setLat(geocode.getLat());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
