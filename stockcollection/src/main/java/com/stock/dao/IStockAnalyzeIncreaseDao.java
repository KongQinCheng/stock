package com.stock.dao;

import com.stock.bean.po.StockAnalyzeIncrease;

import java.util.List;

public interface IStockAnalyzeIncreaseDao {


     void insert(StockAnalyzeIncrease stockAnalyzeIncrease);

    List<StockAnalyzeIncrease> getListAll();

     StockAnalyzeIncrease getEntryByStockCode(String stockCode, String stockDate) ;

    void delByStockCode(String stockCode);

   boolean isNewCount(String stockCode , String stockDate);



}
