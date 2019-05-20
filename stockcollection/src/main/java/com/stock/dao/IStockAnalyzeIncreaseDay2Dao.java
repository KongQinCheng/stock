package com.stock.dao;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.AnalyzeIncreaseDay2;

import java.util.List;

public interface IStockAnalyzeIncreaseDay2Dao {


    void insert(AnalyzeIncreaseDay2 analyzeIncreaseDay);

    List<AnalyzeIncreaseDay2> getListAll(String crossType);


    List<AnalyzeIncreaseDay2> getEntryByStockCode(String stockCode, String stockDate, String effectType, String increaseType);


    List<AnalyzeIncreaseDay2> getEntryByEntry(AnalyzeIncreaseDay2 analyzeIncreaseDay);


    void delByStockCode(String stockCode, String increaseType);

    boolean isNewCount(String stockCode, String stockDate);


}
