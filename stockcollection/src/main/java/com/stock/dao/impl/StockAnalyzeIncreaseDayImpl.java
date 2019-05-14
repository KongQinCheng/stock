package com.stock.dao.impl;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.dao.IStockAnalyzeIncreaseDayDao;
import com.stock.mapper.StockAnalyzeIncreaseDayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAnalyzeIncreaseDayImpl implements IStockAnalyzeIncreaseDayDao {

@Autowired
StockAnalyzeIncreaseDayMapper stockAnalyzeIncreaseDayMapper;

    @Override
    public void insert(AnalyzeIncreaseDay analyzeIncreaseDay) {
        stockAnalyzeIncreaseDayMapper.insert(analyzeIncreaseDay);
    }


    @Override
    public void delByStockCode(String stockCode,String increaseType) {
        stockAnalyzeIncreaseDayMapper.delByStockCode(stockCode, increaseType);
    }


    @Override
    public List<AnalyzeIncreaseDay> getListAll(String effectType) {
        return stockAnalyzeIncreaseDayMapper.getListAll( effectType);
    }


    @Override
    public  List<AnalyzeIncreaseDay> getEntryByStockCode(String stockCode, String stockDate,String  effectType,String  increaseType) {
//        return stockAnalyzeIncreaseDayMapper.getEntryByStockCode(stockCode,stockDate,  effectType,increaseType);
        AnalyzeIncreaseDay analyzeIncreaseDay =new AnalyzeIncreaseDay();

        analyzeIncreaseDay.setIncreaseType(Integer.valueOf(increaseType));
        analyzeIncreaseDay.setStockCode(stockCode);
        analyzeIncreaseDay.setStockDate(stockDate);
        analyzeIncreaseDay.setEffectType(Integer.valueOf(effectType));
        return stockAnalyzeIncreaseDayMapper.getEntryByStockCode(analyzeIncreaseDay);
    }


    @Override
    public  List<AnalyzeIncreaseDay> getEntryByEntry(AnalyzeIncreaseDay analyzeIncreaseDay) {
        return stockAnalyzeIncreaseDayMapper.getEntryByStockCode(analyzeIncreaseDay);
    }



    @Override
    public boolean isNewCount(String stockCode , String stockDate) {
        if(stockAnalyzeIncreaseDayMapper.isNewCount(stockCode ,stockDate)>0)
            return true;
        return false;
    }

}
