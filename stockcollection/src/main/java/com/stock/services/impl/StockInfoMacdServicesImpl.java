package com.stock.services.impl;

import com.alibaba.fastjson.JSON;
import com.stock.Enum.CrossType;
import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockCrossDao;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockInfoMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class StockInfoMacdServicesImpl implements IStockInfoMacdServices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockCrossDao iStockCrossDao;


    @Override
    public void getStockInfoMacd(String stockCode, int type) {

        List<StockInfo> stockListByStockCode = new ArrayList<>();
        if (type == 0) {
            stockListByStockCode = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);
        }
        if (type == 1) {
            stockListByStockCode = iStockInfoDao.getStockListByStockCodeLimit(stockCode,10);
        }

        double lastDayEma12 = 0;
        double lastDayEma26 = 0;
        double lastDEAMACD = 0;
        double todayDif = 0;
        double todayBar = 0;

        for (int i = 0; i < stockListByStockCode.size(); i++) {
            StockInfo stockInfo = stockListByStockCode.get(i);

            if (type == 1) {
                if (stockInfo.getEMA12() != 0) {
                    lastDayEma12 = stockInfo.getEMA12();
                    lastDayEma26 = stockInfo.getEMA26();
                    lastDEAMACD = stockInfo.getEMAMACD();
                    continue;
                }
            }


            lastDayEma12 = getEMA12(lastDayEma12, stockInfo.getSpj());
            lastDayEma26 = getEMA26(lastDayEma26, stockInfo.getSpj());
            todayDif = getDIFF(lastDayEma12, lastDayEma26);
            lastDEAMACD = getDEAMACD(lastDEAMACD, todayDif);
            todayBar = getBAR(lastDEAMACD, todayDif);

            stockInfo.setStockCode(stockCode);
            stockInfo.setEMA12(lastDayEma12);
            stockInfo.setEMA26(lastDayEma26);
            stockInfo.setEMAMACD(lastDEAMACD);
            stockInfo.setDIF(todayDif);
            stockInfo.setBAR(todayBar);
            iStockInfoDao.updateStockInfoMacd(stockInfo);
        }
        System.out.println("MACD值更新成功stockCode= " + stockCode);
    }

    @Override
    public List<Map<String, String>> getStockCross(List<StockInfo> list, int dayNum) {


        List<Map<String, String>> resultList = new ArrayList<>();
        //金交死交个编号说明：
        //第一位：0：死交，1；金交  ；
        //第二位：0：0下；1：0上
        //第三位：0：不存在震荡；1-n:存在N次震荡
        //第四位：0震荡过后处于死交；1震荡过后处于金交


        double beforeDIF = list.get(0).getDIF();
        double beforeDEA = list.get(0).getEMAMACD();
        double beforeMACD = list.get(0).getBAR();
        for (int i = 1; i < list.size(); i++) {
            Map<String, String> map = haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
            beforeDIF = list.get(i).getDIF();
            beforeDEA = list.get(i).getEMAMACD();

            if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来
                resultList.add(map);
            }
        }
        System.out.println("查找金叉 死叉完毕+" + list.get(0).getStockCode());
        ;
        return resultList;
    }

    @Override
    public Map<String, Object> isExistCross(List<StockInfo> list, int dayNum, String crossType) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", false);
        if (list.size() < 1) {
            return resultMap;
        }

        double beforeDIF = list.get(0).getDIF();
        double beforeDEA = list.get(0).getEMAMACD();
        double beforeMACD = list.get(0).getBAR();
        for (int i = 1; i < list.size(); i++) {
            Map<String, String> map = haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
            beforeDIF = list.get(i).getDIF();
            beforeDEA = list.get(i).getEMAMACD();

            if (CrossType.GOLD_CROSS.toString().equals(crossType)) {
                if ("10".equals(map.get("type")) || "11".equals(map.get("type"))) {
                    resultMap.put("result", true);
                    resultMap.put("date", list.get(i).getStockDate());
                    return resultMap;
                }
            }
            if (CrossType.DEAD_CROSS.toString().equals(crossType)) {
                if ("00".equals(map.get("type")) || "01".equals(map.get("type"))) {
                    resultMap.put("result", true);
                    resultMap.put("date", list.get(i).getStockDate());
                    return resultMap;
                }
            }
        }
        return resultMap;
    }



    @Override
    public Map<String, String> haveCross(String day, double beforeDIF, double beforeDEA, double todayDIF, double todayDEA) {

        //beforeDIF-beforeDEA>0 即 macd>0上涨

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("type", "-1");
        if (beforeDIF - beforeDEA < 0 && todayDIF - todayDEA > 0) {

            if (todayDIF > 0 && todayDEA > 0) {
                resultMap.put("type", "11");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0上 金 叉");

            }
            if (todayDIF < 0 && todayDEA < 0) {
                resultMap.put("type", "10");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0下 金 叉");
            }
        }
        if (beforeDIF - beforeDEA > 0 && todayDIF - todayDEA < 0) {
            if (todayDIF > 0 && todayDEA > 0) {
                resultMap.put("type", "01");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0上 死 叉");
            }
            if (todayDIF < 0 && todayDEA < 0) {
                resultMap.put("type", "00");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0下 死 叉");
            }
        }

        return resultMap;
    }


    //    EMA（12）= 前一日EMA（12）×11/13＋今日收盘价×2/13
    public static double getEMA12(double lastDayEMA12, double todaySpj) {
        double result = 0;
        result = lastDayEMA12 * 11 / 13.0 + todaySpj * 2 / 13.0;
        return result;
    }

    //    EMA（26）= 前一日EMA（26）×25/27＋今日收盘价×2/27
    public static double getEMA26(double lastDayEMA26, double todaySpj) {
        double result = 0;
        result = lastDayEMA26 * 25 / 27.0 + todaySpj * 2 / 27.0;
        return result;
    }

    //    DIFF=今日EMA（12）- 今日EMA（26）
    public static double getDIFF(double lastDayEMA12, double lastDayEMA26) {
        double result = 0;
        result = lastDayEMA12 - lastDayEMA26;
        return result;
    }

    //    DEA（MACD）= 前一日DEA×8/10＋今日DIF×2/10
    public static double getDEAMACD(double lastDEA, double todayDIFF) {
        double result = 0;
        result = lastDEA * 8 / 10.0 + todayDIFF * 2 / 10.0;
        return result;
    }

    //    BAR=2×(DIFF－DEA)
    public static double getBAR(double DEA, double todayDIFF) {
        double result = 0;
        result = 2 * (todayDIFF - DEA);
        return result;
    }


    public static void main(String[] args) throws Exception {

        DecimalFormat df = new DecimalFormat("#.0000");
        System.out.println(getEMA12(55.01, 53.7));
        System.out.println(getEMA12(55.01, 53.7));
        System.out.println(getEMA26(55.01, 53.7));
        System.out.println(getDIFF(getEMA12(55.01, 53.7), getEMA26(55.01, 53.7)));
        System.out.println(getDEAMACD(0, getDIFF(getEMA12(55.01, 53.7), getEMA26(55.01, 53.7))));
        System.out.println(getBAR(getDEAMACD(0, getDIFF(getEMA12(55.01, 53.7), getEMA26(55.01, 53.7))), getDIFF(getEMA12(55.01, 53.7), getEMA26(55.01, 53.7))));
    }

}
