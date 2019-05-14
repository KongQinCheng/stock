package com.stock.bean.po;

import lombok.Data;

@Data
public class StockAnalyzeIncrease {

    private int  id ;
    private String  stockCode ;  //代码
    private String stockDate;  //时间
    private double  increase02 ;
    private double  increase24 ;
    private double  increase46 ;
    private double  increase68 ;
    private double  increase810 ;
    private double  increase10 ;

    private double  descend02 ;
    private double  descend24 ;
    private double  descend46 ;
    private double  descend68 ;
    private double  descend810 ;
    private double  descend10 ;
    private double  count ;   //上市总天数
    private double  szcount ;   //上涨总天数
}
