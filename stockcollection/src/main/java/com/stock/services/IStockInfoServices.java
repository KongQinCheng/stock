package com.stock.services;

import com.stock.bean.po.StockInfo;

import java.util.List;

public interface IStockInfoServices {

    /***
     * 获取最新的的股票信息，按照
     * @param stockCode
     * @param sortType  最新的信息的排序方式。 ASC 最新的数据在 list底部。DESC最新数据在 list顶部
     * @param limitNum
     * @return
     */
      List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType, int limitNum);

      List<StockInfo> getStockListByStockCode(String stockCode, int limitNum);

//    public void createTableByTableName(String tableName);

    void updateStockInfoMacd(StockInfo stockInfo);

    void updateStockInfoMacdNoDate(StockInfo stockInfo);



    /***
     * 获取股票的历史消息
     * @param stockCode
     * @throws Exception
     */
      void getStockInfoHistory(String stockCode) throws Exception ;


    void delStockInfo(String stockCode, String stockDate)  ;


    /***
     * 删除 空表
     * @throws Exception
     */
      void delNullTable() throws Exception;

    /***
     * 查找数据为空的表
     * @throws Exception
     */
      void findTable( ) throws Exception;




    /***
     * 获取股票的实时的价格信息
     * @param stockCode
     * @throws Exception
     */
    void getStockInfoActualTime(String stockCode) throws Exception ;


}
