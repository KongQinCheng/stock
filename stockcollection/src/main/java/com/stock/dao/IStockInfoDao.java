package com.stock.dao;

import com.stock.bean.po.StockInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStockInfoDao {


    /***
     * 获取 前 limit 条数的数据
     * @param stockCode
     * @param sortType  最新的信息的排序方式。 ASC 最新的数据在 list底部。DESC最新数据在 list顶部
     * @param limitNum
     * @return
     */
      List<StockInfo> getStockListByStockCode(String stockCode, int limitNum);

    /***
     * 获取 最新的  limit 条数的数据
     * @param stockCode
     * @param sortType  最新的信息的排序方式。 ASC 最新的数据在 list底部。DESC最新数据在 list顶部
     * @param limitNum
     * @return
     */
    List<StockInfo>   getStockListByStockCodeLimit(String stockCode, int limitNum);


    List<StockInfo>   getStockListByStockCodeAndStockDateLimit(String stockCode, String stockDate);

    /***
     * 获取最新的的股票信息，按照
     * @param stockCode
     * @param sortType  最新的信息的排序方式。 ASC 最新的数据在 list底部。DESC最新数据在 list顶部
     * @param limitNum
     * @return
     */
      List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType, int limitNum);

    /***
     * 一次性使用，将stockCode更新
     * @param stockCode
     */
     void updateStockCode(String stockCode);

     void createTableByTableName(String tableName);

     void updateStockInfoMacd(StockInfo stockInfo);

    void updateStockInfoKDJ(StockInfo stockInfo);

    void updateStockInfoMacdNoDate(StockInfo stockInfo);



    /**
     *  判断数据库表 是否存在
     * @param tableName
     * @return
     */
      boolean isTableExist(String tableName);


    /***
     * 添加基本信息到数据库表中
     * @param stockCode
     */
     void addStockInfo(StockInfo stockCode);


    /***
     * 删除 股票的信息
     * @param stockCode
     */
    void delStockInfo(String stockCode,String stockDate);


    /***
     * 删除 股票的信息
     * @param stockCode
     */
    void delEmptyStockInfo(String stockCode);


    /***
     * 判断数据是否已经存在
     * @param stockInfo
     * @return
     */
     boolean isRoweExist(StockInfo stockInfo);


}
