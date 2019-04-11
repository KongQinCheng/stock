package com.stock.dao.impl;

import com.stock.bean.po.StockIncreaseAnalyze;
import com.stock.dao.IStockIncreaseAnalyzeDao;
import com.stock.mapper.StockIncreaseAnalyzeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockIncreaseAnalyzeDaoImpl implements IStockIncreaseAnalyzeDao {


    @Autowired
    StockIncreaseAnalyzeMapper stockIncreaseAnalyzeMapper;

    @Override
    public void insert(StockIncreaseAnalyze stockIncreaseAnalyze) {
        stockIncreaseAnalyzeMapper.insert(stockIncreaseAnalyze);
    }


}
