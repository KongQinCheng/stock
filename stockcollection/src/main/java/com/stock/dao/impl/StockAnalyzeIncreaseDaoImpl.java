package com.stock.dao.impl;

import com.stock.bean.po.StockAnalyzeIncrease;
import com.stock.dao.IStockAnalyzeIncreaseDao;
import com.stock.mapper.StockAnalyzeIncreaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAnalyzeIncreaseDaoImpl implements IStockAnalyzeIncreaseDao {


    @Autowired
    StockAnalyzeIncreaseMapper stockAnalyzeIncreaseMapper;

    @Override
    public void insert(StockAnalyzeIncrease stockAnalyzeIncrease) {
        stockAnalyzeIncreaseMapper.insert(stockAnalyzeIncrease);
    }

    @Override
    public List<StockAnalyzeIncrease> getListAll() {
        return stockAnalyzeIncreaseMapper.getListAll();
    }

    @Override
    public StockAnalyzeIncrease getEntryByStockCode(String stockCode, String stockDate) {
        return stockAnalyzeIncreaseMapper.getEntryByStockCode(stockCode,stockDate);
    }

    @Override
    public void delByStockCode(String stockCode) {
        stockAnalyzeIncreaseMapper.delByStockCode(stockCode);
    }

    @Override
    public boolean isNewCount(String stockCode , String stockDate) {
        if(stockAnalyzeIncreaseMapper.isNewCount(stockCode ,stockDate)>0)
            return true;
        return false;
    }


}
