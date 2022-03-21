package com.scarike.gp.crawler.stations.entity.resp;

import com.scarike.gp.crawler.stations.entity.Train;
import lombok.Data;

import java.util.List;

@Data
public class TrainListResp {
    private List<Train> data;
    private boolean status;
    private String errorMsg;
}
