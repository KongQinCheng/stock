package com.stock.dao.impl;

import com.stock.bean.po.StockAnalyzeIncrease;
import com.stock.bean.po.StockInfoActualtime;
import com.stock.dao.IStockAnalyzeIncreaseDao;
import com.stock.dao.IStockInfoActualtimeDao;
import com.stock.mapper.StockAnalyzeIncreaseMapper;
import com.stock.mapper.StockInfoActualtimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoActualtimeDaoImpl implements IStockInfoActualtimeDao {

    @Autowired
    StockInfoActualtimeMapper stockInfoActualtimeMapper;

    @Override
    public List<StockInfoActualtime> getByStockDate(String stockDate) {
        return stockInfoActualtimeMapper.getByStockDate(stockDate);
    }

    @Override
    public List<StockInfoActualtime> getByNullData() {
        return stockInfoActualtimeMapper.getByNullData();
    }

    @Override
    public void insert(StockInfoActualtime stockInfoActualtime) {
        stockInfoActualtimeMapper.insert(stockInfoActualtime);
    }

    @Override
    public void delete(String stockCode) {
        stockInfoActualtimeMapper.delByStockCode(stockCode);
    }

    @Override
    public void deleteByStockCodeAndStockDate(String stockCode,String stockDate) {
        stockInfoActualtimeMapper.delByStockCodeAndStockDate(stockCode,stockDate);
    }

    @Override
    public void update(StockInfoActualtime stockInfoActualtime) {
        stockInfoActualtimeMapper.update( stockInfoActualtime);
    }

}
