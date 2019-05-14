package com.stock.dao.impl;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockCrossMacd;
import com.stock.dao.IStockCrossDao;
import com.stock.dao.IStockCrossMacdDao;
import com.stock.mapper.StockCrossMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockCrossMacdDaoImpl implements IStockCrossMacdDao {


    @Override
    public List<StockCrossMacd> getStockCrossMacdList() {
        return null;
    }

    @Override
    public StockCrossMacd getStockCrossMacdtByStockCode(String stockCode) {
        return null;
    }

    @Override
    public void insertMacd(StockCrossMacd stockCrossMacd) {

    }

    @Override
    public boolean isExistMacdByStockCodeAndDate(String stockCode, String stockDate) {
        return false;
    }

    @Override
    public void deleteMacd(String stockCode) {

    }
}
