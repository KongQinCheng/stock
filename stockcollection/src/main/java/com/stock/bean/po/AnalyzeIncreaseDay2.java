package com.stock.bean.po;


import lombok.Data;

@Data
public class AnalyzeIncreaseDay2 {

    private int id;
    private String stockCode;     //代码
    private String stockDate;    //时间

    private String  crossType;    //时间  10:出现 0下 金 叉 。  11:出现 0上 金 叉 。 01:出现 0上 死 叉 。 00： 出现 0下 死 叉
    private double  count ;      //总交易天数

    private double  day1 ; //第一天出现上涨的次数
    private double  day2 ; //第二天出现上涨的次数
    private double  day3 ; //第三天出现上涨的次数
    private double  day4 ; //第四天出现上涨的次数
    private double  day5 ; //第五天出现上涨的次数

    public  AnalyzeIncreaseDay2(){
    }

    public  AnalyzeIncreaseDay2(String stockCode,String stockDate,String crossType){
        this.stockCode =stockCode;
        this.stockDate=stockDate;
        this.crossType=crossType;
    }




}
