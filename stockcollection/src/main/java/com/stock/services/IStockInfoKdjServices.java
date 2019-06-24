package com.stock.services;

import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;

import java.util.List;
import java.util.Map;

public interface IStockInfoKdjServices {


    void getKDJValue(String stockCode) throws Exception;

    Map<String, Object> getKdjCross(String stockCode, int dayNum, String crossType);

    List<Map<String, Object>> getKdjCrossAll(StockSearchVo stockSearchVo);

    List<StockNewData> getStockKdjValueRegion(StockNewDataVo stockNewDataVo);

}
