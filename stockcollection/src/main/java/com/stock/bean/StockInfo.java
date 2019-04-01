package com.stock.bean;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class StockInfo {
    private String shareId;

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

    //MACD
    private  double EMA12;
    private  double EMA26;
    private  double DIF;
    private  double EMAMACD;
    private  double BAR;



    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public double getKpj() {
        return kpj;
    }

    public void setKpj(double kpj) {
        this.kpj = kpj;
    }

    public double getZgj() {
        return zgj;
    }

    public void setZgj(double zgj) {
        this.zgj = zgj;
    }

    public double getZdj() {
        return zdj;
    }

    public void setZdj(double zdj) {
        this.zdj = zdj;
    }

    public double getSpj() {
        return spj;
    }

    public void setSpj(double spj) {
        this.spj = spj;
    }

    public double getZde() {
        return zde;
    }

    public void setZde(double zde) {
        this.zde = zde;
    }

    public double getZdf() {
        return zdf;
    }

    public void setZdf(double zdf) {
        this.zdf = zdf;
    }

    public double getCjl() {
        return cjl;
    }

    public void setCjl(double cjl) {
        this.cjl = cjl;
    }

    public double getCjje() {
        return cjje;
    }

    public void setCjje(double cjje) {
        this.cjje = cjje;
    }

    public double getZf() {
        return zf;
    }

    public void setZf(double zf) {
        this.zf = zf;
    }

    public double getHsl() {
        return hsl;
    }

    public void setHsl(double hsl) {
        this.hsl = hsl;
    }

    public double getkValue() {
        return kValue;
    }

    public void setkValue(double kValue) {
        this.kValue = kValue;
    }

    public double getdValue() {
        return dValue;
    }

    public void setdValue(double dValue) {
        this.dValue = dValue;
    }

    public double getjValue() {
        return jValue;
    }

    public void setjValue(double jValue) {
        this.jValue = jValue;
    }

    public double getEMA12() {
        return EMA12;
    }

    public void setEMA12(double EMA12) {
        this.EMA12 = EMA12;
    }

    public double getEMA26() {
        return EMA26;
    }

    public void setEMA26(double EMA26) {
        this.EMA26 = EMA26;
    }

    public double getDIF() {
        return DIF;
    }

    public void setDIF(double DIF) {
        this.DIF = DIF;
    }

    public double getEMAMACD() {
        return EMAMACD;
    }

    public void setEMAMACD(double EMAMACD) {
        this.EMAMACD = EMAMACD;
    }

    public double getBAR() {
        return BAR;
    }

    public void setBAR(double BAR) {
        this.BAR = BAR;
    }

    @Override
    public String toString() {
        return "StockInfo{" +
                "shareId='" + shareId + '\'' +
                ", stockCode='" + stockCode + '\'' +
                ", stockDate='" + stockDate + '\'' +
                ", kpj=" + kpj +
                ", zgj=" + zgj +
                ", zdj=" + zdj +
                ", spj=" + spj +
                ", zde=" + zde +
                ", zdf=" + zdf +
                ", cjl=" + cjl +
                ", cjje=" + cjje +
                ", zf=" + zf +
                ", hsl=" + hsl +
                ", kValue=" + kValue +
                ", dValue=" + dValue +
                ", jValue=" + jValue +
                ", EMA12=" + EMA12 +
                ", EMA26=" + EMA26 +
                ", DIF=" + DIF +
                ", EMAMACD=" + EMAMACD +
                ", BAR=" + BAR +
                '}';
    }
}
