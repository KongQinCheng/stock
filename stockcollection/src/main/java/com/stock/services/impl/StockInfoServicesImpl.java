package com.stock.services.impl;

import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockInfoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoServicesImpl implements IStockInfoServices {

    @Autowired
    IStockInfoDao iStockInfoDao;


    @Override
    public List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType, int limitNum) {
        return  iStockInfoDao.getNewStockListByStockCode(stockCode,sortType,limitNum);
    }
}
