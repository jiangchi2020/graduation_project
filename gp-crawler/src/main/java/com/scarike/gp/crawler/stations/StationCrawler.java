package com.scarike.gp.crawler.stations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.crawler.stations.entity.Train;
import com.scarike.gp.crawler.stations.entity.resp.StopInfoResp;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StationCrawler {

    /**
     * 获取所有的车站信息，32线程，返回所有列车时刻信息
     */
    public static Set<Train> getStation(String date, Set<Train> trainSet) {
        //转换日期格式
        String date2 = date.substring(0, 4) + '-' +
                date.substring(4, 6) + '-' +
                date.substring(6);
        List<Train>[] taskTable=new List[32];
        Thread[] threads=new Thread[32];
        int i;
        for(i=0;i<32;i++){
            taskTable[i]=new LinkedList<>();
        }
        i=0;
        for (Train train : trainSet) {
            taskTable[i%32].add(train);
            i++;
        }
        for(i=0;i<32;i++){
            final List<Train> tasks = taskTable[i];
            threads[i]=new Thread(()->{
                for (Train train : tasks) {
                    StopInfoResp query = query(train.getTrain_no(), date2);
                    train.setStop(query.getData().getData());
                }
            });
            threads[i].start();
        }
        for(i=0;i<32;i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return trainSet;
    }

    /**
     * 根据列车编号与日期查询经停站信息
     */
    public static StopInfoResp query(String trainNo, String date) {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        StopInfoResp resp = null;
        try {
            resp = om.readValue(new URL("https://kyfw.12306.cn/otn/queryTrainInfo/query?leftTicketDTO.train_no=" + trainNo + "&leftTicketDTO.train_date=" + date + "&rand_code="), StopInfoResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
}
