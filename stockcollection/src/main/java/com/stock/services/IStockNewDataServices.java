package com.stock.services;

import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;

import java.util.List;

public interface IStockNewDataServices {
    /***
     *
     * @param stockCode
     * @param limitNum
     * @param insetType  0 每日总统计入库  ，1 实时获取的数据入库
     */
      void getNewDataToTable(String stockCode,int limitNum,String insetType);

    List<StockNewData>  getNewData(StockNewDataVo stockNewDataVo);
}
