package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockInfoCciServices;
import com.stock.services.IStockInfoKdjServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stock.services.impl.StockInfoKdjServicesImpl.getValueByType;

@Service
public class StockInfoCciServicesImpl implements IStockInfoCciServices {

    static DecimalFormat df = new DecimalFormat("#.###");

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockListDao iStockListDao;

    @Autowired
    IStockNewDataDao iStockNewDataDao ;

    @Override
    public  void getCciValue(String stockCode,int dayNum) throws Exception {

//        CCI（N）＝（TYP－MA）÷MD  ÷ 0.015
//        TYP ＝（最高价＋最低价＋收盘价）÷3
//        MA = 近N日收盘价的累计之和÷N
//        MD ＝最近N日 （MA-收盘价）的累计之和÷N
//        0.015为计算系数，N为计算周期


//        CCI（N）＝（TYP－MA）÷AVEDEV  ÷ 0.015
//        TYP＝（最高价＋最低价＋收盘价）÷3
//        MA＝最近N日TYP的移动平均值
//        AVEDEV＝最近N日TYP的平均绝对偏差
//        0.015为计算系数，N为计算周期


        List<StockInfo> stockinfoList = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), 30);

        double[] lszgjArray = new double[dayNum];
        double[] lszdjArray = new double[dayNum];
        double[] spjArray = new double[dayNum];

        if (stockinfoList.size()<dayNum+1){
            return;
        }
        double maxValue=0.0;
        double minVaule=0.0;
        double spjVaule=0.0;

        //将前面9天的数据保存的列表中
        for (int i = 0; i <dayNum-1 ; i++) {
            StockInfo stockInfo = stockinfoList.get(i);
            lszgjArray[i] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[i] = Double.valueOf(stockInfo.getZdj());
            spjArray[i]  =  Double.valueOf(stockInfo.getSpj());
            spjVaule=Double.valueOf(stockInfo.getSpj());
        }

        //从第dayNum天开始计算 CCI 值
        for (int i = dayNum-1; i <stockinfoList.size() ; i++) {

            int insertArrayIndex = i % dayNum;
            StockInfo stockInfo = stockinfoList.get(i);
            spjVaule=Double.valueOf(stockInfo.getSpj());
            lszgjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZdj());
            spjArray[insertArrayIndex]   = Double.valueOf(stockInfo.getSpj());

            maxValue = getValueByType(lszgjArray, 1);
            minVaule = getValueByType(lszdjArray, 0);

            double tpy=getTPY(maxValue,minVaule,spjVaule);
            double ma =getMA(spjArray);
            double md =getMD(ma ,spjArray);
            double cci =getCci(tpy,ma,md);

            System.out.println(stockinfoList.get(i).getStockDate() +"   =====   "+ cci);
        }
    }

    public  static  double getCci( double tpy,double ma, double md ){
        //      （TYP－MA）÷MD  ÷ 0.015
        return (tpy-ma) / md/0.015;
    }

    public  static  double getTPY( double maxValue,double minVaule, double spjVaule ){
        //   TYP ＝（最高价＋最低价＋收盘价）÷3
        return (maxValue +minVaule +spjVaule)/3;
    }

    public  static  double getMA( double[] spjArray ){
        //   MA = 近N日收盘价的累计之和÷N
        double allCount=0;
        for (int i = 0; i < spjArray.length; i++) {
            allCount+=spjArray[i];
        }
        return allCount/spjArray.length;
    }

    public  static  double getMD( double ma ,double[] spjVaule ){
        //   MD ＝最近N日 （MA-收盘价）的绝对值 的 累计之和÷N
        double allCount=0;
        for (int i = 0; i <spjVaule.length ; i++) {
            allCount+=abs(ma-spjVaule[i]);
        }
        return allCount/spjVaule.length;
    }

    public static double abs(double a){
//        return a;
        return a>0?a:-a;
    }


}
