package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockAllTargetUpdateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stock.services.impl.StockInfoCciServicesImpl.*;
import static com.stock.services.impl.StockInfoKdjServicesImpl.*;
import static com.stock.services.impl.StockInfoMacdServicesImpl.*;



@Service
public class StockAllTargetUpdateServicesImpl implements IStockAllTargetUpdateServices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Override
    public void allTargetUpdate(String stockCode) throws Exception {

        //根据股票代码查询历史数据
        List<StockInfo> stockInfoListAll =  iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(),50);

        //MACD的 初始化
        double lastDayEma12 = 0;
        double lastDayEma26 = 0;
        double lastDEAMACD = 0;
        double todayDif = 0;
        double todayBar = 0;


        //KDJ  初始化
        double[] lszgjArray = new double[9];
        double[] lszdjArray = new double[9];
        double maxValue=0.0;
        double minVaule=0.0;

        double kValue = 50.0;
        double dValue = 50.0;
        double jValue=0.0;


        //CCI 初始化
        int dayNum=14;
        double[] spjArray = new double[dayNum];

        //将前面n天的数据保存的列表中
        if (stockInfoListAll.size()>=dayNum+1){
            for (int i = 0; i <dayNum ; i++) {
                spjArray[i]  =  Double.valueOf(stockInfoListAll.get(i).getSpj());
            }
        }



        if (stockInfoListAll.size()>8){
            //将前面9天的数据保存的列表中
            for (int i = 0; i <9 ; i++) {
                StockInfo stockInfo = stockInfoListAll.get(i);
                lszgjArray[i] = Double.valueOf(stockInfo.getZgj());
                lszdjArray[i] = Double.valueOf(stockInfo.getZdj());
            }
        }



        for (int i = 0; i <stockInfoListAll.size() ; i++) {
            StockInfo stockInfo =stockInfoListAll.get(i);
            stockInfo.setStockCode(stockCode);

            //判断MACD值是否存在，如果不存在则进行更新--开始
            lastDayEma12 = stockInfo.getEMA12();
            lastDayEma26 = stockInfo.getEMA26();
            lastDEAMACD = stockInfo.getEMAMACD();

            if (stockInfo.getEMA12() != 0){
                lastDayEma12 = getEMA12(lastDayEma12, stockInfo.getSpj());
                lastDayEma26 = getEMA26(lastDayEma26, stockInfo.getSpj());
                todayDif = getDIFF(lastDayEma12, lastDayEma26);
                lastDEAMACD = getDEAMACD(lastDEAMACD, todayDif);
                todayBar = getBAR(lastDEAMACD, todayDif);

                stockInfo.setEMA12(lastDayEma12);
                stockInfo.setEMA26(lastDayEma26);
                stockInfo.setEMAMACD(lastDEAMACD);
                stockInfo.setDIF(todayDif);
                stockInfo.setBAR(todayBar);
            }
            System.out.println("MACD 更新完毕stockCode=" + stockCode +" stockDate="+stockInfo.getStockDate());
            //判断MACD值是否存在，如果不存在则进行更新--结束


            //判断KDJ值是否存在，如果不存在则进行更新--开始
            int insertArrayIndex = i % 9;
            lszgjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZdj());

            if (!(stockInfo.getKValue()==0.0&& stockInfo.getDValue()==0.0&& stockInfo.getJValue()==0.0)){
                kValue =stockInfo.getKValue();
                dValue=stockInfo.getDValue();
            }else {
                maxValue = getValueByType(lszgjArray, 1);
                minVaule = getValueByType(lszdjArray, 0);
                if (maxValue !=minVaule){
                    double rsv = getRsv((double) (stockInfo.getSpj()), (double)maxValue, minVaule);
                    kValue = getK(kValue, rsv);
                    dValue = getD(dValue, kValue);
                    jValue = getJ(kValue, dValue);
                }
                stockInfo.setKValue(kValue);
                stockInfo.setDValue(dValue);
                stockInfo.setJValue(jValue);
            }
            System.out.println("KDJ  更新完毕stockCode=" + stockCode+" stockDate="+stockInfo.getStockDate());
            //判断KDJ值是否存在，如果不存在则进行更新--结束



            //判断CCI值是否存在，如果不存在则进行更新--开始
            if (stockInfoListAll.size()>=dayNum+1){
                spjArray[insertArrayIndex]   = Double.valueOf(stockInfo.getSpj());
                double tpy=getTPY(Double.valueOf(stockInfo.getZgj()),Double.valueOf(stockInfo.getZdj()),Double.valueOf(stockInfo.getSpj()));
                double ma =getMA(spjArray);
                double md =getMD(ma ,spjArray);
                double cci =getCci(tpy,ma,md);
                stockInfo.setCci(cci);
            }
            System.out.println("CCI  更新完毕stockCode=" + stockCode+" stockDate="+stockInfo.getStockDate());
            //判断CCI值是否存在，如果不存在则进行更新--结束


            iStockInfoDao.updateStockInfoAll(stockInfo);

        }
    }
}
