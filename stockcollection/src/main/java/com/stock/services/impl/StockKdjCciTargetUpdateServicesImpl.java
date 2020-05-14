package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockAllTargetUpdateServices;
import com.stock.services.IStockKdjCciTargetUpdateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

import static com.stock.services.impl.StockInfoCciServicesImpl.*;
import static com.stock.services.impl.StockInfoKdjServicesImpl.*;


@Service
public class StockKdjCciTargetUpdateServicesImpl implements IStockKdjCciTargetUpdateServices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Override
    public void allTargetUpdate(String stockCode) throws Exception {

        //重置数据 --删除最近半年的数据

        //根据股票代码查询历史数据
        List<StockInfo> stockInfoListAll = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), 80);


        //KDJ  初始化
        double[] lszgjArray = new double[9];
        double[] lszdjArray = new double[9];
        double maxValue = 0.0;
        double minVaule = 0.0;

        double kValue = 50.0;
        double dValue = 50.0;
        double jValue = 0.0;


        //CCI 初始化
        int dayNum = 14;
        double[] spjArray = new double[dayNum];


        //将前面n天的数据保存的列表中
        if (stockInfoListAll.size() >= dayNum + 1) {
            for (int i = 0; i < dayNum; i++) {
                spjArray[i] = Double.valueOf(stockInfoListAll.get(i).getSpj());
            }
        }


        if (stockInfoListAll.size() > 8) {
            //将前面9天的数据保存的列表中
            for (int i = 0; i < 9; i++) {
                StockInfo stockInfo = stockInfoListAll.get(i);
                lszgjArray[i] = Double.valueOf(stockInfo.getZgj());
                lszdjArray[i] = Double.valueOf(stockInfo.getZdj());
            }
        }


        for (int i = 0; i < stockInfoListAll.size(); i++) {

            boolean kdjBoolean = false;
            boolean cciBoolean = false;

            StockInfo stockInfo = stockInfoListAll.get(i);
            stockInfo.setStockCode(stockCode);

            //判断KDJ值是否存在，如果不存在则进行更新--开始
            int insertArrayIndex = i % 9;
            lszgjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZdj());

            if (stockInfo.getKValue() == 0.0 && stockInfo.getDValue() == 0.0 && stockInfo.getJValue() == 0.0) {//当天的数据未进行计算
                maxValue = getValueByType(lszgjArray, 1);
                minVaule = getValueByType(lszdjArray, 0);
                if (maxValue != minVaule) {
                    double rsv = getRsv((double) (stockInfo.getSpj()), (double) maxValue, minVaule);
                    kValue = getK(kValue, rsv);
                    dValue = getD(dValue, kValue);
                    jValue = getJ(kValue, dValue);
                }
                stockInfo.setKValue(kValue);
                stockInfo.setDValue(dValue);
                stockInfo.setJValue(jValue);
                kdjBoolean = true;
            } else {
                kValue = stockInfo.getKValue();
                dValue = stockInfo.getDValue();
            }
//            System.out.println("KDJ  更新完毕stockCode=" + stockCode + " stockDate=" + stockInfo.getStockDate());
            //判断KDJ值是否存在，如果不存在则进行更新--结束
//
//
//
//            //判断CCI值是否存在，如果不存在则进行更新--开始
            spjArray[insertArrayIndex] = Double.valueOf(stockInfo.getSpj());
            if (stockInfoListAll.size() >= dayNum + 1) {
                if (stockInfo.getCci() == 0.0) {    //当天的数据未进行计算
                    double tpy = getTPY(Double.valueOf(stockInfo.getZgj()), Double.valueOf(stockInfo.getZdj()), Double.valueOf(stockInfo.getSpj()));
                    double ma = getMA(spjArray);
                    double md = getMD(ma, spjArray);
                    double cci = getCci(tpy, ma, md);
                    stockInfo.setCci(cci);
                    cciBoolean = true;
                }
            }
//            System.out.println("CCI  更新完毕stockCode=" + stockCode + " stockDate=" + stockInfo.getStockDate());


            try {
                if (kdjBoolean || cciBoolean) {
                    iStockInfoDao.updateStockInfoAll(stockInfo);
                }
            }catch (Exception e){
                System.out.println("插入数据异常，新增字段");
                iStockInfoDao.delTableByStockCode(stockCode);
                iStockInfoDao.createTableByTableName("stock_info_"+stockCode);


            }
        }


        System.out.println("所有指标  更新完毕stockCode=" + stockCode);
    }
}
