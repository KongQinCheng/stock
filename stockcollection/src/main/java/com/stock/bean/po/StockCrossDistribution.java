package com.stock.bean.po;

import lombok.Data;

@Data
public class StockCrossDistribution {

    private int  id ;
    private String  stockCode ;  //代码
    private String stockDate;  //时间
    private String dayNum;  //时间
    private String crossType;  //时间
    private double  increase0 ;
    private double  increase1 ;
    private double  increase2 ;
    private double  increase3 ;
    private double  increase4 ;
    private double  increase5 ;
    private double  increase6 ;
    private double  increase7 ;
    private double  increase8 ;
    private double  increase9 ;
    private double  increase10 ;
    private double  count ;

    public  StockCrossDistribution(){
    }

    public  StockCrossDistribution(String stockCode,String stockDate,String crossType,String dayNum){
        this.stockCode =stockCode;
        this.stockDate=stockDate;
        this.crossType=crossType;
        this.dayNum=dayNum;
    }

}
