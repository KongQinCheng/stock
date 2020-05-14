package com.stock.dao;

import com.stock.bean.po.StockList;

import java.util.List;

public interface IStockListDao {


    /***
     * 获取所有的列表
     * @return
     */
    public  List<StockList> getStockList()  ;

    /***
     * 获取所有的列表
     * @return
     */
    public  List<StockList> getStockListDesc()  ;

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

    /***
     *
     * @param stockCode
     * @param StockName
     * @param StockDate  数据插入时间
     * @param status   1是正常状态   2 ST状态  3 退市状态
     */

    void addStockList(String stockCode, String StockName , String StockDate, String status);


    /***
     * 现在股票编号
     * @param stockCode
     * @param status
     * @return
     */

    void updateStockList(String stockCode, String stockName);

    void updateStockListStatus(String stockCode, String status);
}
