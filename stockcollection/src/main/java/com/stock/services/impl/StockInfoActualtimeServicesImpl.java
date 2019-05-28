package com.stock.services.impl;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockInfoActualtime;
import com.stock.dao.IStockInfoActualtimeDao;
import com.stock.mapper.StockInfoMapper;
import com.stock.services.IStockInfoActualtimeServices;
import com.stock.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoActualtimeServicesImpl implements IStockInfoActualtimeServices {

    @Autowired
    IStockInfoActualtimeDao iStockInfoActualtimeDao;

    @Override
    public List<StockInfoActualtime> getByStockDate(String stockDate) {
        return iStockInfoActualtimeDao.getByStockDate( stockDate) ;
    }

    @Override
    public void insert(StockInfoActualtime stockInfoActualtime) {
        iStockInfoActualtimeDao.insert(stockInfoActualtime);
    }

    @Override
    public void delete(String stockCode) {
        iStockInfoActualtimeDao.delete(stockCode);
    }

    @Override
    public void deleteByStockCodeAndStockDate(String stockCode, String stockDate) {
        iStockInfoActualtimeDao.deleteByStockCodeAndStockDate(stockCode,stockDate);
    }
}
