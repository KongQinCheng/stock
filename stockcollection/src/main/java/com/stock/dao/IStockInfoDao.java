package com.stock.dao;

import com.stock.bean.StockInfo;
import com.stock.bean.StockList;

import java.util.List;

public interface IStockInfoDao {

    /***
     * 获取最新的的股票信息，按照
     * @param stockCode
     * @param sortType  最新的信息的排序方式。 ASC 最新的数据在 list底部。DESC最新数据在 list顶部
     * @param limitNum
     * @return
     */
    public  List<StockInfo> getNewStockListByShareCode(String stockCode, String sortType , int limitNum);

    /***
     * 一次性使用，将stockCode更新
     * @param stockCode
     */
    public void updateStockCode(String stockCode);

}
