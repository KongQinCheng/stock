package com.stock.dao.impl;

import com.stock.bean.StockIncreaseAnalyze;
import com.stock.bean.StockInfo;
import com.stock.dao.IStockIncreaseAnalyzeDao;
import com.stock.dao.IStockInfoDao;
import com.stock.mapper.StockIncreaseAnalyzeMapper;
import com.stock.mapper.StockInfoMapper;
import com.stock.services.IStockIncreaseAnalyzeServices;
import org.mybatis.spring.annotation.MapperScan;
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


}
