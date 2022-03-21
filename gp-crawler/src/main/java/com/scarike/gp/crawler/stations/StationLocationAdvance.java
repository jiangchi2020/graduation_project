package com.scarike.gp.crawler.stations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarike.gp.crawler.util.db.SQLUtil;

import javax.sql.rowset.spi.SyncResolver;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 由于高德地图提供的地理编码服务部分站点的查询结果为空，这里使用天地图的地理编码API进一步查询
 * 首先从数据表中搜索为空的站名字段，构造请求调用
 * http://api.tianditu.gov.cn/geocoder?ds={%22keyWord%22:%22%E6%95%A6%E7%85%8C%E7%AB%99%22}&tk=50bdb310b4b5c20364a1c4802161cd13
 * 查询
 */
public class StationLocationAdvance {

    private static final ObjectMapper om=new ObjectMapper();

    public static void main(String[] args) throws SQLException, IOException {

        FileWriter fw=new FileWriter("gp-crawler/advance.sql");

        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Connection conn = SQLUtil.getConn();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select station_id,station_name from t_station where lon is null or lat is null ");
        while (resultSet.next()) {
            int sid = resultSet.getInt(1);
            String name=resultSet.getString(2)+"站";
            System.out.println(name);
            name = URLEncoder.encode(name, StandardCharsets.UTF_8);
            TkResp.Location loc = query(name);
            if(loc==null)
                continue;
            fw.append("update t_station set lon=")
                    .append(String.valueOf(loc.lon))
                    .append(",lat=").append(String.valueOf(loc.lat))
                    .append(" where station_id=").append(String.valueOf(sid)).append(";\n");
        }
        resultSet.close();
        statement.close();
        conn.close();
        fw.flush();fw.close();
    }

    public static TkResp.Location query(String key) throws IOException {
        URL url = new URL("http://api.tianditu.gov.cn/geocoder?tk=50bdb310b4b5c20364a1c4802161cd13&ds={%22keyWord%22:%22" + key + "%22}");
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
        conn.setRequestProperty("Cookie","HWWAFSESID=0027aa0073f9534607; HWWAFSESTIME=1647061688351; TDTSESID=rBACAmIsKrq5RwNgBRAtAg==");
        conn.connect();
        InputStream inputStream = conn.getInputStream();
        TkResp tkResp = om.readValue(inputStream, TkResp.class);
        System.out.println(tkResp);
        inputStream.close();
        return tkResp.location;
//        return om.readValue(new URL("http://api.tianditu.gov.cn/geocoder?tk=50bdb310b4b5c20364a1c4802161cd13&ds={%22keyWord%22:%22"+key+"%22}"), TkResp.class).location;
    }

    public static class TkResp{
        public int status;
        public String msg;
        public Location location;

        public static class Location{
            public double lon;
            public double lat;

            @Override
            public String toString() {
                return "Location{" +
                        "lon=" + lon +
                        ", lat=" + lat +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "TkResp{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", location=" + location +
                    '}';
        }
    }
}
