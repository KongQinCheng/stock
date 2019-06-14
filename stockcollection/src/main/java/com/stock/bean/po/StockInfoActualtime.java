package com.stock.bean.po;


import lombok.Data;

@Data
public class StockInfoActualtime {

    private int id;
    private String stockCode;     //代码
    private String stockDate;    //时间
    private String spj;    //时间

    private String effectDay1;
    private String effectDay2;
    private String effectDay3;
    private String effectDay4;
    private String effectDay5;



}
