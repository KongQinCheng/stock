package com.stock.bean.vo;

import lombok.Data;

@Data
public class StockNewDataVo {
    private String  stockCode ;  //代码
    private String stockBeginTime;  //时间
    private String stockEndTime;  //时间

    private double spjmin;
    private double spjmax;

    private double zdfmin;
    private double zdfmax;

    private double kvaluemax;
    private double kvaluemin;

    private double dvaluemax;
    private double dvaluemin;

    private double jvaluemax;
    private double jvaluemin;


    private double rsivaluemax1;
    private double rsivaluemax2;
    private double rsivaluemax3;

    private double rsivaluemin1;
    private double rsivaluemin2;
    private double rsivaluemin3;

    private String crossType;

    private double ccivaluemin;
    private double ccivaluemax;

    private double rsimax;

    private double rsimin;


    private int dayNum;
    private int cci;

    private double zdf_1 ;
    private double zdf_2 ;
    private double zdf_3 ;
    private double zdf_5 ;
    private double zdf_10 ;
    private double zdf_15 ;
    private double zdf_30 ;

}
