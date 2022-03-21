package com.scarike.gp.crawler.stations.entity.resp;

import com.scarike.gp.crawler.stations.entity.Stop;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class StopInfoResp {
    private String validateMessagesShowId;
    private boolean status;
    private boolean httpstatus;
    private StopInfo data;
    private List<String> messages;

    @Data
    public static class StopInfo{
        private LinkedList<Stop> data;
    }
}
