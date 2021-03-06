package com.stock.dao.impl;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;

import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockNewDataDao;
import com.stock.mapper.StockNewDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockNewDataDaoImpl implements IStockNewDataDao {

    @Autowired
    StockNewDataMapper stockNewDataMapper;

    @Override
    public void insert(StockInfo stockInfo) {
        stockNewDataMapper.insert(stockInfo);
    }

    @Override
    public void deleteAll() {
        stockNewDataMapper.deleteAll();
    }

    @Override
    public void deleteByStockCode(String stockCode) {
        stockNewDataMapper.deleteByStockCode(stockCode);
    }

    @Override
    public void deleteByStockCodeAndStockDate(String stockCode,String stockDate){
        stockNewDataMapper.deleteByStockCodeAndStockDate(stockCode,stockDate);
    }


    @Override
    public List<StockNewData> getStockNewDataListByVo(StockNewDataVo stockNewDataVo) {
        return stockNewDataMapper.getStockNewDataListByVo(stockNewDataVo);
    }
    @Override
    public List<StockNewData> getStockListByStockCode(String stockCode, int limitNum) {
        return stockNewDataMapper.getStockListByStockCode(stockCode, limitNum);
    }

    @Override
    public List<StockNewData> getStockKdjValueRegion(StockNewDataVo stockNewDataVo) {
        return stockNewDataMapper.getStockKdjValueRegion(stockNewDataVo);
    }

    @Override
    public List<StockNewData> getStockHslValueRegion(StockNewDataVo stockNewDataVo) {
        return stockNewDataMapper.getStockHslValueRegion(stockNewDataVo);
    }

}
