package com.scarike.gp.crawler.stations.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Train {
    private String station_train_code;
    private String from_station;
    private int from_station_id;
    private String to_station;
    private int to_station_id;
    private int total_num;
    private String date;
    private String train_no;
    private List<Stop> stop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(station_train_code, train.station_train_code);
    }

    @Override
    public int hashCode() {
        return station_train_code.hashCode();
    }
}
