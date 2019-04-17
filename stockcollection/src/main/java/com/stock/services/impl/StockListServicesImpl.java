package com.stock.services.impl;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockListServicesImpl implements IStockListServices {

    @Autowired
    IStockListDao iStockListDao;

    @Override
    public List<StockList> getStockList() {
        return iStockListDao.getStockList();
    }

    @Override
    public List<StockList> getStockListLimit(int limit) {
        return iStockListDao.getStockListLimit(limit);
    }

    @Override
    public boolean isExitStockList(String stockCode) {
        return iStockListDao.isExitStockList(stockCode);
    }

    @Override
    public void addStockList(String stockCode, String stockName) {
         iStockListDao.addStockList(stockCode,stockName);
    }
}
