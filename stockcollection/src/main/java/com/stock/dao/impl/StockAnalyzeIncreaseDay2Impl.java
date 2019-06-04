package com.stock.dao.impl;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.AnalyzeIncreaseDay2;
import com.stock.dao.IStockAnalyzeIncreaseDay2Dao;
import com.stock.dao.IStockAnalyzeIncreaseDayDao;
import com.stock.mapper.StockAnalyzeIncreaseDay2Mapper;
import com.stock.mapper.StockAnalyzeIncreaseDayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAnalyzeIncreaseDay2Impl implements IStockAnalyzeIncreaseDay2Dao {

@Autowired
StockAnalyzeIncreaseDay2Mapper stockAnalyzeIncreaseDay2Mapper;

    @Override
    public void insert(AnalyzeIncreaseDay2 analyzeIncreaseDay) {
        stockAnalyzeIncreaseDay2Mapper.insert(analyzeIncreaseDay);
    }

    @Override
    public void insert(List<AnalyzeIncreaseDay2> list) {
        for (int i = 0; i < list.size(); i++) {
            stockAnalyzeIncreaseDay2Mapper.insert(list.get(i));
        }
    }


    @Override
    public void delByStockCode(String stockCode,String increaseType) {
        stockAnalyzeIncreaseDay2Mapper.delByStockCode(stockCode, increaseType);
    }


    @Override
    public List<AnalyzeIncreaseDay2> getListAll(String crossType) {
        return stockAnalyzeIncreaseDay2Mapper.getListAll( crossType);
    }


    @Override
    public  List<AnalyzeIncreaseDay2> getEntryByStockCode(String stockCode, String stockDate,String  effectType,String  increaseType) {
//        return stockAnalyzeIncreaseDayMapper.getEntryByStockCode(stockCode,stockDate,  effectType,increaseType);
        AnalyzeIncreaseDay2 analyzeIncreaseDay =new AnalyzeIncreaseDay2();
        return stockAnalyzeIncreaseDay2Mapper.getEntryByStockCode(analyzeIncreaseDay);
    }


    @Override
    public  List<AnalyzeIncreaseDay2> getEntryByEntry(AnalyzeIncreaseDay2 analyzeIncreaseDay) {
        return stockAnalyzeIncreaseDay2Mapper.getEntryByStockCode(analyzeIncreaseDay);
    }



    @Override
    public boolean isNewCount(String stockCode , String stockDate) {
        if(stockAnalyzeIncreaseDay2Mapper.isNewCount(stockCode ,stockDate)>0)
            return true;
        return false;
    }

}
