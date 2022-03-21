package com.scarike.gp.crawler.stations.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Station implements Serializable {
    private Integer station_id;
    private String station_name;
    private String address;
    /**
     * 经度
     */
    private Double lon;
    /**
     * 纬度
     */
    private Double lat;

    public Station(Integer station_id, String station_name) {
        this.station_id = station_id;
        this.station_name = station_name;
    }
}
