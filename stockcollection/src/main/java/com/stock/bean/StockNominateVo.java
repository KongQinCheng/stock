package com.stock.bean;

import lombok.Data;

@Data
public class StockNominateVo {
    private String  stockCode ;  //代码
    private String stockBeginTime;  //时间
    private String stockEndTime;  //时间

    private String spjmin;
    private String spjmax;




}
