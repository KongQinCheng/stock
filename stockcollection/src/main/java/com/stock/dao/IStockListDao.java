package com.stock.dao;

import com.stock.bean.StockInfo;
import com.stock.bean.StockList;

import java.util.List;

public interface IStockListDao {


    /***
     * 获取所有的列表
     * @return
     */
    public  List<StockList> getStockList()  ;

    /***
     * 获取指定条数的数据
     * @param limit
     * @return
     */
    public  List<StockList> getStockListLimit(int limit);

    /***
     * 判断该股票编码是否存在于股票列表
     * @param stockCode
     * @return
     */
    public  boolean isExitStockList(String stockCode);
}
