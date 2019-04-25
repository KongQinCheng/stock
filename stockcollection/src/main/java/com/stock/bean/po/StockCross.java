package com.stock.bean.po;

import lombok.Data;

@Data
public class StockCross {

    private int  id ;
    private String  stockCode ;  //代码
    private String stockDate;  //时间
    private double  increase1 ;
    private double  increase2 ;
    private double  increase3 ;
    private double  increase4 ;
    private double  increase5 ;

    private double  count ;

}
