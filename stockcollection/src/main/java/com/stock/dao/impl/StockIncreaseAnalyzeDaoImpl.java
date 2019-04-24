package com.stock.dao.impl;

import com.stock.bean.po.StockIncreaseAnalyze;
import com.stock.dao.IStockIncreaseAnalyzeDao;
import com.stock.mapper.StockIncreaseAnalyzeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockIncreaseAnalyzeDaoImpl implements IStockIncreaseAnalyzeDao {


    @Autowired
    StockIncreaseAnalyzeMapper stockIncreaseAnalyzeMapper;

    @Override
    public void insert(StockIncreaseAnalyze stockIncreaseAnalyze) {
        stockIncreaseAnalyzeMapper.insert(stockIncreaseAnalyze);
    }

    @Override
    public List<StockIncreaseAnalyze> getListAll() {
        return stockIncreaseAnalyzeMapper.getListAll();
    }

    @Override
    public StockIncreaseAnalyze getEntryByStockCode(String stockCode,String stockDate) {
        return stockIncreaseAnalyzeMapper.getEntryByStockCode(stockCode,stockDate);
    }

    @Override
    public void delByStockCode(String stockCode) {
        stockIncreaseAnalyzeMapper.delByStockCode(stockCode);
    }

    @Override
    public boolean isNewCount(String stockCode , String stockDate) {
        if(stockIncreaseAnalyzeMapper.isNewCount(stockCode ,stockDate)>0)
            return true;
        return false;
    }


}
