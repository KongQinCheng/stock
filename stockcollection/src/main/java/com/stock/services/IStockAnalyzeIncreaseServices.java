package com.stock.services;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.StockAnalyzeIncrease;

import java.util.List;
import java.util.Map;

/***
 * 股票当日上涨和后面的关系
 */

public interface IStockAnalyzeIncreaseServices {


    /***
     * 进行所有 涨幅相关的 分析
     * @param stockCode
     */
    void getStockAnalyzeIncreaseAll(String stockCode);


    void insert(AnalyzeIncreaseDay analyzeIncreaseDay);

    List<AnalyzeIncreaseDay> getListAll(String effectType);

    List<AnalyzeIncreaseDay> getEntryByStockCode(String stockCode, String stockDate,String effectType,String  increaseType);


    List<AnalyzeIncreaseDay> getEntryByEntry(AnalyzeIncreaseDay analyzeIncreaseDay);



    void delByStockCode(String stockCode,String increaseType);

    boolean isNewCount(String stockCode, String stockDate);


    /***
     * 获取 影响的壁纸
     * @param stockCode
     * @return
     */

    /***
     * 获取 影响的壁纸
     * @param stockCode
     * @return
     */
    public Map<String, Object> getStockIncreaseEffect(String stockCode);

    public Map<String, Object> getStockIncreaseEffect(String stockCode,String  effectType);


}
