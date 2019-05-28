package com.stock.dao;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockInfoActualtime;

import java.util.List;

public interface IStockInfoActualtimeDao {

    List<StockInfoActualtime> getByStockDate(String stockDate);

    void insert(StockInfoActualtime stockInfoActualtime );

    void delete(String stockCode);

     void deleteByStockCodeAndStockDate(String stockCode,String stockDate) ;

}
