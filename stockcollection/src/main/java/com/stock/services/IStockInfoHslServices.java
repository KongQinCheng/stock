package com.stock.services;

import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;

import java.util.List;
import java.util.Map;

public interface IStockInfoHslServices {

    List<StockNewData> getStockHslValueRegion(StockNewDataVo stockNewDataVo);

}
