package com.stock.dao.impl;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockCrossDistribution;
import com.stock.dao.IStockCrossDao;
import com.stock.dao.IStockCrossDistributionDao;
import com.stock.mapper.StockCrossDistributionMapper;
import com.stock.mapper.StockCrossMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockCrossDistributionDaoImpl implements IStockCrossDistributionDao {


    @Autowired
    StockCrossDistributionMapper stockCrossDistributionMapper;

    @Override
    public List<StockCrossDistribution> getStockCrossList() {
        return stockCrossDistributionMapper.getStockCrossDistributionList();
    }

    @Override
    public StockCrossDistribution getStockCrosstByStockCode(String stockCode, String stockDate, String crossType, String dayNum) {
        return stockCrossDistributionMapper.getStockCrosstDistribution(stockCode,stockDate, crossType, dayNum);
    }

    @Override
    public List<StockCrossDistribution> getStockCrosstDistributionList(String stockCode, String stockDate, String crossType, String dayNum) {
        return stockCrossDistributionMapper.getStockCrosstDistributionList(stockCode,stockDate, crossType, dayNum);
    }

    @Override
    public List<StockCrossDistribution> getStockCrosstByStockCodeAndStockDate(String stockCode, String stockDate) {
        return stockCrossDistributionMapper.getStockCrosstByStockCodeAndStockDate(stockCode,stockDate);
    }

    @Override
    public void insert(StockCrossDistribution stockCrossDistribution) {
        stockCrossDistributionMapper.insert(stockCrossDistribution);
    }

    @Override
    public void del(String stockCode) {
        stockCrossDistributionMapper.del(stockCode);
    }
}
