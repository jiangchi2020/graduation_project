package com.scarike.gp.crawler.stations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.crawler.stations.entity.Train;
import com.scarike.gp.crawler.stations.entity.resp.TrainListResp;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class TrainListCrawler {
    /**
     * 根据日期获取所有车次，封装成Set集合，根据车次名不重复
     */
    public static Set<Train> getTrainList(String date) {
        Set<Train> trainSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            TrainListResp resp = search(String.valueOf(i), date);
            trainSet.addAll(resp.getData());
        }
        return trainSet;
    }

    /**
     * 发送请求并封装结果，根据关键字和日期返回所有车次
     */
    public static TrainListResp search(String keyword, String date) {
        ObjectMapper om = new ObjectMapper();
        TrainListResp resp = null;
        try {
            resp = om.readValue(new URL("https://search.12306.cn/search/v1/train/search?keyword=" + keyword + "&date=" + date), TrainListResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

}
