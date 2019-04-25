package com.stock.dao;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockInfo;

import java.util.List;

public interface IStockCrossDao {


    /***
     * 获取最新交叉对之后的影响
     * @param stockCode
     * @param sortType  最新的信息的排序方式。 ASC 最新的数据在 list底部。DESC最新数据在 list顶部
     * @param limitNum
     * @return
     */
    List<StockCross> getStockCrossList();

    /***
     *根据 stockCode 获取 StockCross统计信息
     * @param stockCode
     * @return
     */
    StockCross getStockCrosstByStockCode(String stockCode);

    void insert(StockCross stockCross);

    boolean isExistByStockCodeAndDate(String stockCode, String stockDate) ;

    void delete(String stockCode);


}
