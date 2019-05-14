package com.stock.bean.po;


import lombok.Data;

@Data
public class AnalyzeIncreaseDay {

    private int id;
    private String stockCode;     //代码
    private String stockDate;    //时间

    private int increaseType;    //分析的类型。0：普通的上涨情况  1：金叉出现之后的情况
    private int indexDay ;      //当前的数据是第几天的数据0：截至到目前为止所有的数据，1：后一天的影响情况；2：后第二天的影响情况
    private int effectType ;    //影响类型：0：当天数据，1：前一天的涨幅区间对 后一天上涨的影响，2：前一天上涨对后一天涨幅的影响

    private int level;  // 0:表示 父级别。1：表示 子级别

    private String crossType;    //时间  10:出现 0下 金 叉 。  11:出现 0上 金 叉 。 01:出现 0上 死 叉 。 00： 出现 0下 死 叉
    private double  count ;      //总交易天数

    private double  increase10 ;
    private double  increase9 ;
    private double  increase8 ;
    private double  increase7 ;
    private double  increase6 ;
    private double  increase5 ;
    private double  increase4 ;
    private double  increase3 ;
    private double  increase2 ;
    private double  increase1 ;
    private double  increase0 ;  //上涨的区间为 0-1 之间 的天数

    private double  descend1 ;
    private double  descend2 ;
    private double  descend3 ;
    private double  descend4 ;
    private double  descend5 ;
    private double  descend6 ;
    private double  descend7 ;
    private double  descend8 ;
    private double  descend9 ;
    private double  descend10 ;
    private double  descend20 ;

}
