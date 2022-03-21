package com.scarike.gp.crawler.stations;

import com.scarike.gp.crawler.stations.entity.Station;
import com.scarike.gp.crawler.stations.entity.Train;
import com.scarike.gp.crawler.util.TaskTimer;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class I2306Crawler {
    /**
     * 从12306官网爬取列车车次信息，其大体流程为：
     * 1. 先获取train所有的车次信息，使用它的api分别使用关键字0-9查询再去重，即可查询到所有车次
     * 2. 根据车次编号，查询这个车次的经停站信息
     * 3. 遍历车次，对于每一车次遍历其中的经停站信息，给经停站生成自增的id
     * 4. 返回车次集合，
     * @param date 查询的日期
     * @param stationMap 保存车站的集合
     * @return 车次集合
     */
    public static Set<Train> crawl(String date, Map<String, Station> stationMap){
        TaskTimer task1=new TaskTimer("获取车次信息").begin();
        Set<Train> trainList = TrainListCrawler.getTrainList(date);
        task1.end();

        TaskTimer task2=new TaskTimer("获取经停站信息").begin();
        StationCrawler.getStation(date, trainList);
        task2.end();

        TaskTimer task3=new TaskTimer("生成唯一id").begin();
        var ref = new Object() {
            int id = 0;
        };
        BiFunction<String,Station,Station> id_gen=(name,station)->{
            if(station==null){
                return new Station(ref.id++, name);
            }
            return station;
        };

        trainList.forEach(train -> {
            train.setFrom_station_id(stationMap.compute(train.getFrom_station(),id_gen).getStation_id());
            train.setTo_station_id(stationMap.compute(train.getTo_station(),id_gen).getStation_id());
            train.getStop().forEach(stop -> {
                stop.setStation_id(stationMap.compute(stop.getStation_name(),id_gen).getStation_id());
            });
        });
        task3.end();

        return trainList;
    }
}
