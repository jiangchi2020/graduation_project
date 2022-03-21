package com.scarike.gp.crawler.stations.entity;

import lombok.Data;

import java.util.Objects;

//{"arrive_day_str":"当日到达"
// ,"station_name":"乌鲁木齐","train_class_name":"动车","is_start":"Y","service_type":"2"
// ,"end_station_name":"兰州西","arrive_time":"10:18","start_station_name":"乌鲁木齐"
// ,"station_train_code":"D56","arrive_day_diff":"0","start_time":"10:18"
// ,"station_no":"01","wz_num":"--","running_time":"00:00"}
//{"arrive_day_str":"当日到达","arrive_time":"11:11","station_train_code":"D56"
// ,"station_name":"吐鲁番北","arrive_day_diff":"0","OT":[],"start_time":"11:13"
// ,"wz_num":"--","station_no":"02","running_time":"00:53"}
@Data
public class Stop {
    private Integer station_id;
    private String station_name;
    private String station_train_code;
    private String arrive_time;
    private String start_time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return Objects.equals(station_name, stop.station_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station_name);
    }

}
