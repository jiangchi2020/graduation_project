package com.scarike.gp.crawler;

import com.scarike.gp.crawler.pois.PoiCrawler;
import com.scarike.gp.crawler.stations.entity.Station;
import com.scarike.gp.crawler.stations.entity.Stop;
import com.scarike.gp.crawler.stations.entity.Train;
import com.scarike.gp.crawler.util.TaskTimer;
import com.scarike.gp.crawler.util.db.SQLUtil;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args) {
        poi();
    }

    public static void poi() {
        PoiCrawler.crawl("110000");
    }

    public static void train()
    {
        TaskTimer timer=new TaskTimer("任务").begin();

        GpCrawler.Result crawl = GpCrawler.crawl("20220115");
        final Station[] stationList = crawl.stationList;
        final Set<Train> trainSet = crawl.trainSet;

        StringBuilder sql1=new StringBuilder(10000);
        sql1.append("insert into `gp`.`t_train`(train_no,station_train_code,from_station_id,to_station_id,total_num,date) values");

        StringBuilder sql2=new StringBuilder(100000);
        sql2.append("insert into `gp`.`t_stop`(station_id,station_train_code,arrive_time,start_time) values");

        StringBuilder sql3=new StringBuilder(40000);
        sql3.append("insert into `gp`.`t_station`(station_id,station_name,station_address,lon,lat) values");

        TaskTimer task1=new TaskTimer("生成SQL语句").begin();
        for (Train t : trainSet) {
            sql1.append("('").append(t.getTrain_no()).append("','")
                    .append(t.getStation_train_code()).append("',")
                    .append(t.getFrom_station_id()).append(',')
                    .append(t.getTo_station_id()).append(',')
                    .append(t.getTotal_num()).append(",'")
                    .append(t.getDate()).append("'),");
            List<Stop> stop = t.getStop();
            for (Stop s : stop) {
                sql2.append('(').append(s.getStation_id()).append(",'")
                        .append(t.getStation_train_code()).append("','")
                        .append(s.getArrive_time()).append("','")
                        .append(s.getStart_time()).append("'),");
            }
        }

        for (Station st : stationList) {
            sql3.append('(').append(st.getStation_id()).append(",'")
                    .append(st.getStation_name()).append("','")
                    .append(st.getAddress()).append("',")
                    .append(st.getLon()).append(',')
                    .append(st.getLat()).append("),");
        }
        task1.end();

        TaskTimer task2=new TaskTimer("执行SQL").begin();
        SQLUtil.execute(sql1.substring(0,sql1.length()-1));
        SQLUtil.execute(sql3.substring(0,sql3.length()-1));
        SQLUtil.execute(sql2.substring(0,sql2.length()-1));
        task2.end();


        timer.end();
        System.out.println("end");
    }
}
