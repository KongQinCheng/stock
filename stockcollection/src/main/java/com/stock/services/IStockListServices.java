package com.stock.services;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;

import java.util.List;
import java.util.Map;

public interface IStockListServices {


    /***
     * 循环获取 网易的股票列表 初始化
     */
      void getWycjStockList() throws Exception;


    /***
     * 获取最新上市的股票的信息
     */
      void getStockNewList() throws Exception;


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
     * 判断该股票是否存在
     * @param stockCode
     * @return
     */
     boolean isExitStockList(String stockCode);

    /***
     * 判断该股票是否存在
     * @param stockCode
     * @return
     */
    void addStockList(String stockCode ,String StockName);


}
