package com.scarike.gp.web.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Poi {
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long pid;
    private String name;
    private String address;
    private String tel;
    private String description;
    private String type;
    private String typeCode;
    private List<String> photo;
    private Double lon;
    private Double lat;
}
