package com.stock.dao.impl;

import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.mapper.StockInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoDaoImpl implements IStockInfoDao {


    @Autowired
    StockInfoMapper stockInfoMapper;

    @Override
    public List<StockInfo> getStockListByStockCode(String stockCode ,int limitNum) {
        List<StockInfo> stockListByStockCode = stockInfoMapper.getStockListByStockCode(stockCode,limitNum);
        return stockListByStockCode;
    }

    @Override
    public List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType ,int limitNum) {
        List<StockInfo> stockListByStockCode = stockInfoMapper.getNewStockListByStockCode(stockCode,sortType,limitNum);
        return stockListByStockCode;
    }


    @Override
    public void updateStockCode(String stockCode) {
        stockInfoMapper.updateStockCode(stockCode);
    }

}
