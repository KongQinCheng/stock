package com.stock.bean.dto;


import lombok.Data;

@Data
public class AnalyzeCorssEffectDto {

    private int id;
    private String stockCode;     //代码
    private String stockDate;    //时间


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
