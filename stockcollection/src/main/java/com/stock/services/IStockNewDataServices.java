package com.stock.services;

import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;

import java.util.List;

public interface IStockNewDataServices {
      void getNewDataToTable(String stockCode);

    List<StockNewData>  getNewData(StockNewDataVo stockNewDataVo);
}
