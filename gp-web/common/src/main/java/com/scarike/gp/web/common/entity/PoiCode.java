package com.scarike.gp.web.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PoiCode {
    @JsonProperty("")
    PC_050000,
    @JsonProperty("")
    PC_050100,
    @JsonProperty("")
    PC_050101,
    @JsonProperty("")
    PC_050102,
    @JsonProperty("")
    PC_050103,
    @JsonProperty("")
    PC_050104,
    @JsonProperty("")
    PC_050105,
    @JsonProperty("")
    PC_050106,
    @JsonProperty("")
    PC_050107,
    @JsonProperty("")
    PC_050108,
    @JsonProperty("")
    PC_050109,
    @JsonProperty("")
    PC_050110,
    @JsonProperty("")
    PC_050111,
    @JsonProperty("")
    PC_050112,
    @JsonProperty("")
    PC_050113,
    @JsonProperty("")
    PC_050114,
    @JsonProperty("")
    PC_050115,
    @JsonProperty("")
    PC_050116,
    @JsonProperty("")
    PC_050117,
    @JsonProperty("")
    PC_050118,
    @JsonProperty("")
    PC_050119,
    @JsonProperty("")
    PC_050120,
    @JsonProperty("")
    PC_050121,
    @JsonProperty("")
    PC_050122,
    @JsonProperty("")
    PC_050123,
    @JsonProperty("")
    PC_050200,
    @JsonProperty("")
    PC_050201,
    @JsonProperty("")
    PC_050202,
    @JsonProperty("")
    PC_050203,
    @JsonProperty("")
    PC_050204,
    @JsonProperty("")
    PC_050205,
    @JsonProperty("")
    PC_050206,
    @JsonProperty("")
    PC_050207,
    @JsonProperty("")
    PC_050208,
    @JsonProperty("")
    PC_050209,
    @JsonProperty("")
    PC_050210,
    @JsonProperty("")
    PC_050211,
    @JsonProperty("")
    PC_050212,
    @JsonProperty("")
    PC_050213,
    @JsonProperty("")
    PC_050214,
    @JsonProperty("")
    PC_050215,
    @JsonProperty("")
    PC_050216,
    @JsonProperty("")
    PC_050217,
    @JsonProperty("")
    PC_050300,
    @JsonProperty("")
    PC_050301,
    @JsonProperty("")
    PC_050302,
    @JsonProperty("")
    PC_050303,
    @JsonProperty("")
    PC_050304,
    @JsonProperty("")
    PC_050305,
    @JsonProperty("")
    PC_050306,
    @JsonProperty("")
    PC_050307,
    @JsonProperty("")
    PC_050308,
    @JsonProperty("")
    PC_050309,
    @JsonProperty("")
    PC_050310,
    @JsonProperty("")
    PC_050311,
    @JsonProperty("")
    PC_050400,
    @JsonProperty("")
    PC_050500,
    @JsonProperty("")
    PC_050501,
    @JsonProperty("")
    PC_050502,
    @JsonProperty("")
    PC_050503,
    @JsonProperty("")
    PC_050504,
    @JsonProperty("")
    PC_050600,
    @JsonProperty("")
    PC_050700,
    @JsonProperty("")
    PC_050800,
    @JsonProperty("")
    PC_050900,
    @JsonProperty("")
    PC_100000,
    @JsonProperty("")
    PC_100100,
    @JsonProperty("")
    PC_100101,
    @JsonProperty("")
    PC_100102,
    @JsonProperty("")
    PC_100103,
    @JsonProperty("")
    PC_100104,
    @JsonProperty("")
    PC_100105,
    @JsonProperty("")
    PC_100200,
    @JsonProperty("")
    PC_100201,
    @JsonProperty("")
    PC_110000,
    @JsonProperty("")
    PC_110100,
    @JsonProperty("")
    PC_110101,
    @JsonProperty("")
    PC_110102,
    @JsonProperty("")
    PC_110103,
    @JsonProperty("")
    PC_110104,
    @JsonProperty("")
    PC_110105,
    @JsonProperty("")
    PC_110106,
    @JsonProperty("")
    PC_110200,
    @JsonProperty("")
    PC_110201,
    @JsonProperty("")
    PC_110202,
    @JsonProperty("")
    PC_110203,
    @JsonProperty("")
    PC_110204,
    @JsonProperty("")
    PC_110205,
    @JsonProperty("")
    PC_110206,
    @JsonProperty("")
    PC_110207,
    @JsonProperty("")
    PC_110208,
    @JsonProperty("")
    PC_110209,
    @JsonProperty("")
    PC_110210;

    private static final PoiCode[][][] MAP = {
            {
                    {PC_050000},
                    {PC_050100, PC_050101, PC_050102, PC_050103, PC_050104, PC_050105, PC_050106, PC_050107, PC_050108, PC_050109, PC_050110, PC_050111, PC_050112, PC_050113, PC_050114, PC_050115, PC_050116, PC_050117, PC_050118, PC_050119, PC_050120, PC_050121, PC_050122, PC_050123},
                    {PC_050200, PC_050201, PC_050202, PC_050203, PC_050204, PC_050205, PC_050206, PC_050207, PC_050208, PC_050209, PC_050210, PC_050211, PC_050212, PC_050213, PC_050214, PC_050215, PC_050216, PC_050217},
                    {PC_050300, PC_050301, PC_050302, PC_050303, PC_050304, PC_050305, PC_050306, PC_050307, PC_050308, PC_050309, PC_050310, PC_050311},
                    {PC_050400, PC_050500, PC_050501, PC_050502, PC_050503, PC_050504, PC_050600, PC_050700, PC_050800, PC_050900},
            },
            {
                    {PC_100000},
                    {PC_100100, PC_100101, PC_100102, PC_100103, PC_100104, PC_100105},
                    {PC_100200, PC_100201}
            },
            {
                    {PC_110000},
                    {PC_110100, PC_110101, PC_110102, PC_110103, PC_110104, PC_110105, PC_110106},
                    {PC_110200, PC_110201, PC_110202, PC_110203, PC_110204, PC_110205, PC_110206, PC_110207, PC_110208, PC_110209, PC_110210}
            }
    };

    public static PoiCode valueOf(int code){
        PoiCode[][] pos=null;
        if(code/10000==5){
            pos=MAP[0];
        }else if(code/10000==10){
            pos=MAP[1];
        }else if(code/10000==11){
            pos=MAP[2];
        }else {
            return null;
        }
        return pos[code%10000/100][code%100];
    }

}
