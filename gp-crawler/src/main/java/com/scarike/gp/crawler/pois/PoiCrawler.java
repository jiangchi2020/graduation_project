package com.scarike.gp.crawler.pois;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.crawler.exception.PoiSearchException;
import com.scarike.gp.crawler.pois.entity.Poi;
import com.scarike.gp.crawler.pois.entity.resp.PoiResp;
import com.scarike.gp.crawler.util.TaskTimer;

import javax.validation.constraints.Null;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PoiCrawler {

    public static final int THREAD_COUNT = 8;
    public static final int COUNT_PRE_PAGE = 20;

    private static String URL = "https://restapi.amap.com/v3/place/text?key=caf711833b9439071d21c857a8161343&types=";

    public static void crawl(String code) {
        URL = URL + code + "&city=";

        List<String>[] task = getCities();
        try (
                PrintStream out = new PrintStream("gp-crawler/sql/" + code + ".sql");
                PrintStream ph_out = new PrintStream("gp-crawler/sql/" + code + "_photo.sql")
        ) {
            out.println("insert into `gp`.`t_poi_" + code + "`(`poi_id`,`lat`,`lon`,`address`,`name`,`tel`,`type`) values");
            ph_out.println("insert into `gp`.`t_poi_" + code + "_photo`(`poi_id`,`photo_url`) values");
            Thread[] threads = new Thread[THREAD_COUNT];
            for (int i = 0; i < threads.length; i++) {
                List<String> cities = task[i];
                threads[i] = new Thread(() -> {
                    boolean fail = false;
                    for (String city : cities) {
                        if (fail) {
                            System.out.println("[" + Thread.currentThread().getName() + "] 异常中断,剩余城市：" + city);
                        } else {
                            try {
                                getPoisByCity(city, out, ph_out);
                            } catch (PoiSearchException e) {
                                fail=true;
                                System.out.println("[" + Thread.currentThread().getName() + "] 异常中断,剩余城市：" + city);
                                e.printStackTrace();
                            }
                        }
                    }
                }, "Thread-" + i);
                threads[i].start();
            }

            Thread.sleep(5000);
            for (int i = 0; i < THREAD_COUNT; i++) {
                threads[i].join();
            }
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void getPoisByCity(String city, PrintStream out, PrintStream ph_out) {
        System.out.println("[" + Thread.currentThread().getName() + "] 查询城市：" + city);
        String url = URL + city + "&page=";

        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        int page = 1;

        PoiResp query = query(url, page, om);
        int count = query.getCount();
        writeSql(query.getPois(), out, ph_out);
        int pages = count / COUNT_PRE_PAGE + 1;
        for (page = 2; page <= pages; page++) {
            writeSql(query(url, page, om).getPois(), out, ph_out);
        }
    }

    public static PoiResp query(String url, int page, ObjectMapper om) {
        try {
            return om.readValue(new URL(url + page), PoiResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeSql(List<Poi> pois, PrintStream out, PrintStream ph_out) {
        for (Poi poi : pois) {
            out.println("(" +
                    poi.getPoi_id() + ',' +
                    poi.getLat() + ',' +
                    poi.getLon() + ",E'" +
                    (poi.getPname() + poi.getCityname() + poi.getAdname() + poi.getAddress()).replaceAll("'","\\'") + "',E'" +
                    poi.getName().replaceAll("'","\\'") + "','" +
                    poi.getTel() + "','" +
                    poi.getTypecode() + "'),");
            out.flush();
            for (Poi.Photo photo : poi.getPhotos()) {
                ph_out.println("(" +
                        poi.getPoi_id() + ",'" +
                        photo.getUrl() + "'),");
            }
            ph_out.flush();
        }
    }

    public static List<String>[] getCities() {
        List<String>[] task = new List[THREAD_COUNT];
        for (int i = 0; i < task.length; i++) {
            task[i] = new ArrayList<>(44);
        }
        try (BufferedReader br = new BufferedReader(new FileReader("gp-crawler/china.citys"))) {
            int count = Integer.parseInt(br.readLine());
            for (int i = 0; i < count; i++) {
                task[i % THREAD_COUNT].add(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

}
