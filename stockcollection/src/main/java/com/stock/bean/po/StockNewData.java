package com.stock.bean.po;

import lombok.Data;

@Data
public class StockNewData {

    private int id;
    private String  stockCode ;  //代码
    private String stockDate;  //时间
    private double  kpj ;  //开盘价
    private double  zgj ;  //最高价
    private double  zdj ;  //最低价
    private double  spj ;  //收盘价
    private double  zde ;  //涨跌额
    private double  zdf ;  //涨跌幅(%)
    private double  cjl ;  //成交量(手)
    private double  cjje ;  //成交金额(万元)
    private double  zf ;  //振幅(%)
    private double  hsl ;  //换手率(%)

    //KDJ
    private double  kValue ;  //K
    private double  dValue ;  //D
    private double  jValue ;  //J

    private double  cci ;  //J

    private double  RSI06 ;  //J
    private double  RSI12 ;  //J
    private double  RSI24 ;  //J


    //MACD
    private  double EMA12;
    private  double EMA26;
    private  double DIF;
    private  double EMAMACD;
    private  double BAR;




}
