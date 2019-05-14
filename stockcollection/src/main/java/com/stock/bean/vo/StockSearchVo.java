package com.stock.bean.vo;

import lombok.Data;

@Data
public class StockSearchVo {
    private String  stockCode ;  //代码
    private String stockBeginTime;  //时间
    private String stockEndTime;  //时间

    private double spjmin;
    private double spjmax;

    private double zdfmin;
    private double zdfmax;


    private int dayNum;

    private String  crossType ; //金叉 死叉类型。
    private String  effectType ; //金叉 死叉类型。

}
