package com.stock.dao;

import com.stock.bean.StockInfo;
import com.stock.bean.StockNewData;

import java.util.List;

public interface IStockNewDataDao {

    /***
     * 新增数据初入到数据库
     * @param stockInfo
     */
    public void insert(StockInfo stockInfo);

    /***
     * 清空数据库表中的数据
     */
    public void deleteAll();


    /***
     * 新增数据初入到数据库
     * @param stockInfo
     */
    public List<StockNewData> getStockListByStockCode(String stockCode, int limitNum);

}
