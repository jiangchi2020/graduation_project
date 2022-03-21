package com.scarike.gp.crawler.stations.entity.resp;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class GeoCodeResp {
    private int status;
    private String info;
    private int count;
    private List<GeoCode> geocodes;

    public static GeoCodeResp emptyResp(){
        GeoCodeResp resp = new GeoCodeResp();
        resp.geocodes= Collections.emptyList();
        return resp;
    }

    @Data
    public static class GeoCode {
        private String formatted_address;
        private Double lon;
        private Double lat;

        public void setFormatted_address(Object formatted_address) {
            if(formatted_address instanceof String)
                this.formatted_address = (String) formatted_address;
            else this.formatted_address=null;
        }

        public void setLocation(Object loc) {
            if(!(loc instanceof String))
                return;
            String location= (String) loc;
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
    }
}
