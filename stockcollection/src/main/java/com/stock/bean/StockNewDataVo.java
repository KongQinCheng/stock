package com.stock.bean;

import lombok.Data;

@Data
public class StockNewDataVo {
    private String  stockCode ;  //代码
    private String stockBeginTime;  //时间
    private String stockEndTime;  //时间

    private double spjmin;
    private double spjmax;

    private double zdf_1 ;
    private double zdf_2 ;
    private double zdf_3 ;
    private double zdf_5 ;
    private double zdf_10 ;
    private double zdf_15 ;
    private double zdf_30 ;

}
