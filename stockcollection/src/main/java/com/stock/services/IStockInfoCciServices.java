package com.stock.services;

import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;

import java.util.List;
import java.util.Map;

public interface IStockInfoCciServices {


    void getCciValue(String stockCode,int dayNum) throws Exception;

}
