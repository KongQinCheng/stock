package com.stock.dao;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockCrossDistribution;

import java.util.List;

public interface IStockCrossDistributionDao {

    List<StockCrossDistribution> getStockCrossList();

    StockCrossDistribution getStockCrosstByStockCode(String stockCode,String stockDate,String crossType,String dayNum);

    List<StockCrossDistribution> getStockCrosstDistributionList(String stockCode,String stockDate,String crossType,String dayNum);

    List<StockCrossDistribution> getStockCrosstByStockCodeAndStockDate(String stockCode,String stockDate);


    void insert(StockCrossDistribution stockCrossDistribution);

    void del(String stockCode);


}
