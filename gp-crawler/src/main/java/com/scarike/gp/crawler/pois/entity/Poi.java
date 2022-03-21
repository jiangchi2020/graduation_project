package com.scarike.gp.crawler.pois.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Poi {

    private static final AtomicInteger ID = new AtomicInteger(0);

    private String pname;
    private String cityname;
    private String adname;
    private String address;

    private String name;
    private List<Photo> photos;

    private Double lon;
    private Double lat;

    private String typecode;

    private String tel;

    private int poi_id;

    public Poi() {
        poi_id = ID.getAndIncrement();
    }

    public void setAddress(Object address) {
        if (address instanceof String) {
            this.address = (String) address;
        } else {
            this.address = "";
        }
    }

    public void setPname(Object pname) {
        if (pname instanceof String) {
            this.pname = (String) pname;
        } else {
            this.pname = "";
        }
    }

    public void setCityname(Object cityname) {
        if (cityname instanceof String) {
            this.cityname = (String) cityname;
        } else {
            this.cityname = "";
        }
    }

    public void setAdname(Object adname) {
        if (adname instanceof String) {
            this.adname = (String) adname;
        } else {
            this.adname = "";
        }
    }

    public void setName(Object name) {
        if (name instanceof String) {
            this.name = (String) name;
        } else {
            this.name = "";
        }
    }

    public void setTypecode(Object typecode) {
        if (typecode instanceof String) {
            this.typecode = (String) typecode;
        } else {
            this.typecode = "";
        }
    }

    public void setTel(Object tel) {
        if (tel instanceof String) {
            this.tel = (String) tel;
        } else {
            this.tel = "";
        }
    }

    public void setLocation(Object loc) {
        if (!(loc instanceof String))
            return;
        String location = (String) loc;
        final String[] split = location.split(",");
        try {
            this.lon = Double.parseDouble(split[0]);
        } catch (NumberFormatException e) {
            this.lon = null;
        }
        try {
            this.lat = Double.parseDouble(split[1]);
        } catch (NumberFormatException e) {
            this.lat = null;
        }
    }

    @Data
    public static class Photo {
        private String url;
    }
}
