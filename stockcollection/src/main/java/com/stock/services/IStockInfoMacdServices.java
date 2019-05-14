package com.stock.services;

import com.stock.bean.po.StockInfo;

import java.util.List;
import java.util.Map;

public interface IStockInfoMacdServices {



    /***
     * 计算 MACD值
     * @param stockCode
     * @param type  0: 初始化， 1： 有新数据进行更新
     */
    void getStockInfoMacd(String stockCode, int type);


    /***
     * 获取 dayNum 天数内的 金叉死叉 情况
     * @param list
     * @param dayNum
     * @return
     */
    List<Map<String, String>> getStockCross(List<StockInfo> list, int dayNum);


    /***
     * 判读多少天内 是否存在 金叉 或者死叉
     * @param list
     * @param dayNum
     * @param crossType
     * @return
     */
    Map<String, Object> isExistCross(List<StockInfo> list, int dayNum, String crossType);


    /***
     * 根据传入的值 ，判断是否为交叉
     * @param day
     * @param beforeDIF
     * @param beforeDEA
     * @param todayDIF
     * @param todayDEA
     * @return
     */
     Map<String, String> haveCross(String day, double beforeDIF, double beforeDEA, double todayDIF, double todayDEA);






}
