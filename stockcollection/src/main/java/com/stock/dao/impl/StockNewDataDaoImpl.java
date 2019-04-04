package com.stock.dao.impl;

import com.stock.bean.StockInfo;
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
}
