package com.stock.dao;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.StockAnalyzeIncrease;

import java.util.List;

public interface IStockAnalyzeIncreaseDayDao {


    void insert(AnalyzeIncreaseDay analyzeIncreaseDay);

    List<AnalyzeIncreaseDay> getListAll(String effectType);


    List<AnalyzeIncreaseDay> getEntryByStockCode(String stockCode, String stockDate, String effectType,String  increaseType);


    List<AnalyzeIncreaseDay> getEntryByEntry(AnalyzeIncreaseDay analyzeIncreaseDay);


    void delByStockCode(String stockCode, String increaseType);

    boolean isNewCount(String stockCode, String stockDate);


}
