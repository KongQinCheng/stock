package com.stock.dao.impl;

import com.stock.bean.po.StockCross;
import com.stock.dao.IStockCrossDao;
import com.stock.mapper.StockCrossMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockCrossDaoImpl implements IStockCrossDao {


    @Autowired
    StockCrossMapper stockCrossMapper;

    @Override
    public List<StockCross> getStockCrossList() {
        return stockCrossMapper.getStockCrossList();
    }

    @Override
    public StockCross getStockCrosstByStockCode(String stockCode) {
          return stockCrossMapper.getStockCrosstByStockCode(stockCode);

    }

    @Override
    public void insert(StockCross stockCross) {
        stockCrossMapper.insert(stockCross);
    }

    @Override
    public boolean isExistByStockCodeAndDate(String stockCode, String stockDate) {
        if (stockCrossMapper.isExistByStockCodeAndDate(stockCode,stockDate)>0)
            return  true;
        return false;
    }

    @Override
    public void delete(String stockCode) {
        stockCrossMapper.delete(stockCode);
    }

    @Override
    public List<StockCross> getStockCrossMacdList() {
        return null;
    }

    @Override
    public StockCross getStockCrossMacdtByStockCode(String stockCode) {
        return null;
    }

    @Override
    public void insertMacd(StockCross stockCross) {

    }

    @Override
    public boolean isExistMacdByStockCodeAndDate(String stockCode, String stockDate) {
        return false;
    }

    @Override
    public void deleteMacd(String stockCode) {

    }
}
