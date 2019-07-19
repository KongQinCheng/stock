package com.stock.services;

import com.stock.bean.po.StockInfo;

import java.util.List;

public interface IStockAnalyzeRSIservices {

    /**
     * 计算RSI指标数据
     * 
     * @param closePrice--收盘价要从第一天开始
     * @param rsi1_n--计算rsi1指标，标准为：6
     * @param rsi2_n--计算rsi2指标，标准为：12
     * @param rsi3_n--计算rsi3指标，标准为：24
     * @param rsi1--顺序记录rsi1指标
     *            new double[]
     * @param rsi2--顺序记录rsi2指标
     *            new double[]
     * @param rsi3--顺序记录rsi3指标
     *            new double[]
     */
    void getRSI(String stockCode);

    void alterTable(String stockCode);


}
