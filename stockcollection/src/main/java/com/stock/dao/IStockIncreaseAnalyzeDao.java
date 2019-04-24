package com.stock.dao;

import com.stock.bean.po.StockIncreaseAnalyze;

import java.util.List;

public interface IStockIncreaseAnalyzeDao {


     void insert(StockIncreaseAnalyze stockIncreaseAnalyze);

    List<StockIncreaseAnalyze> getListAll();

     StockIncreaseAnalyze getEntryByStockCode(String stockCode,String stockDate) ;

    void delByStockCode(String stockCode);

   boolean isNewCount(String stockCode , String stockDate);



}
