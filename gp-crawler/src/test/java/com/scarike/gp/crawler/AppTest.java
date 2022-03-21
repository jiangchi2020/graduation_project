package com.scarike.gp.crawler;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.crawler.stations.entity.resp.GeoCodeResp;
import com.scarike.gp.crawler.util.db.SQLUtil;
import lombok.Data;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.*;
import java.util.*;

/**
 * Unit test for simple TrainListCrawler.
 */
public class AppTest {

    @Test
    public void t() throws SQLException {
        Connection conn = SQLUtil.getConn();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select sid,name,lon,lat from station where lon IS NOT NULL and lat IS NOT NULL;");
        StringBuilder sb=new StringBuilder(10000000);
        sb.append("[");
        while (rs.next()){
            sb.append("{\"id\":").append(rs.getInt(1)).append(',')
              .append("\"name\":\"").append(rs.getString(2)).append("\",")
              .append("\"lnglat\":[").append(rs.getDouble(3)).append(',')
                    .append(rs.getDouble(4)).append("]},");
        }
        System.out.println(sb.append(']').toString());
        rs.close();
        st.close();
        conn.close();
    }

    @Test
    public void test(){
        String json="{\"info\":\"infoinfo\",\"status\":\"1\"}";
        ObjectMapper om=new ObjectMapper();
        try {
            final Bean2 b = om.readValue(json, Bean2.class);
            System.out.println(b);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void TrainSetTest() {
        ObjectMapper om = new ObjectMapper();
        String content = "{\"a\":2,\"s\":\"scarike\"}";
        try {
            final Bean bean = om.readValue(content, Bean.class);
            System.out.println(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void hdanf() {
        String content = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"10000\",\"count\":\"2\",\"geocodes\":[{\"formatted_address\":\"安徽省合肥市瑶海区合肥站\",\"country\":\"中国\",\"province\":\"安徽省\",\"citycode\":\"0551\",\"city\":\"合肥市\",\"district\":\"瑶海区\",\"township\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"adcode\":\"340102\",\"street\":[],\"number\":[],\"location\":\"117.301563,31.864906\",\"level\":\"兴趣点\"},{\"formatted_address\":\"安徽省合肥市包河区合肥南站\",\"country\":\"中国\",\"province\":\"安徽省\",\"citycode\":\"0551\",\"city\":\"合肥市\",\"district\":\"包河区\",\"township\":[],\"neighborhood\":{\"name\":[],\"type\":[]},\"building\":{\"name\":[],\"type\":[]},\"adcode\":\"340111\",\"street\":[],\"number\":[],\"location\":\"117.290288,31.798983\",\"level\":\"兴趣点\"}]}";
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GeoCodeResp resp;
        try {
            resp = om.readValue(content, GeoCodeResp.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ps(){
        try (PrintStream ps = new PrintStream("test")) {
            ps.println("lalalal");
            ps.println("bababa");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static class Bean {
        public int a;
        public String s;
        private List<String> b;

        public Bean() {
            System.out.println("con");
        }

        @Override
        public String toString() {
            return "Bean{" +
                    "a=" + a +
                    ", s='" + s + '\'' +
                    ", b=" + b +
                    '}';
        }
    }

    @Data
    private static class Bean2{
        private int status;
        private String info;

        public void setStatus(int status) {
            this.status = status;
            System.out.println("do status");
        }

        public void setInfo(String info) {
            this.info = info;
            System.out.println("do info");
        }
    }

}
