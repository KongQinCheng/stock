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
    public  List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType, int limitNum);

}
