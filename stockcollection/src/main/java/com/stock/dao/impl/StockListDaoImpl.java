package com.stock.dao.impl;

import com.stock.bean.po.StockList;
import com.stock.dao.IStockListDao;
import com.stock.mapper.StockListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StockListDaoImpl implements IStockListDao {

    @Autowired
    StockListMapper stockListMapper;

    @Override
    public List<StockList> getStockList() {
        return  stockListMapper.getStockList();
    }

    @Override
    public List<StockList> getStockListLimit(int limit) {
        return  stockListMapper.getStockListLimit(limit);
    }

    @Override
    public boolean isExitStockList(String stockCode) {
        return false;
    }
}
