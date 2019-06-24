package com.stock.bean.vo;

import lombok.Data;

@Data
public class StockNewDataVo {
    private String  stockCode ;  //代码
    private String stockBeginTime;  //时间
    private String stockEndTime;  //时间

    private double spjmin;
    private double spjmax;

    private double zdfmin;
    private double zdfmax;

    private double kValueMax;
    private double kValueMin;

    private double dValueMax;
    private double dValueMin;

    private double jValueMax;
    private double jValueMin;


    private String crossType;

    private int dayNum;

    private double zdf_1 ;
    private double zdf_2 ;
    private double zdf_3 ;
    private double zdf_5 ;
    private double zdf_10 ;
    private double zdf_15 ;
    private double zdf_30 ;

}
