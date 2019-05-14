package com.stock.dao;

import com.stock.bean.po.StockCrossMacd;

import java.util.List;

public interface IStockCrossMacdDao {

    List<StockCrossMacd> getStockCrossMacdList();

    StockCrossMacd getStockCrossMacdtByStockCode(String stockCode);

    void insertMacd(StockCrossMacd stockCrossMacd);

    boolean isExistMacdByStockCodeAndDate(String stockCode, String stockDate) ;

    void deleteMacd(String stockCode);

}
