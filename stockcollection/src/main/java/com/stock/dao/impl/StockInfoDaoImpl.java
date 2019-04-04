package com.stock.dao.impl;

import com.stock.bean.StockInfo;
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
    public List<StockInfo> getNewStockListByShareCode(String stockCode, String sortType ,int limitNum) {
        List<StockInfo> stockListByShareCode = stockInfoMapper.getNewStockListByShareCode(stockCode,sortType,limitNum);
        return stockListByShareCode;
    }


    @Override
    public void updateStockCode(String stockCode) {
        stockInfoMapper.updateStockCode(stockCode);
    }

}
