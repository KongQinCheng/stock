package com.stock.services.impl;

import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockNewDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockNewDataServicesImpl implements IStockNewDataServices  {

    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @Override
    public void getNewDataToTable(String stockCode) {

    }

    @Override
    public List<StockNewData>   getNewData(StockNewDataVo stockNewDataVo) {
       return  iStockNewDataDao.getStockNewDataListByVo(stockNewDataVo);
    }
}
