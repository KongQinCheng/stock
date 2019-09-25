package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockInfoHslServices;
import com.stock.services.IStockInfoKdjServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockInfoHslServicesImpl implements IStockInfoHslServices{

    static DecimalFormat df = new DecimalFormat("#.###");

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockListDao iStockListDao;

    @Autowired
    IStockNewDataDao iStockNewDataDao ;


    @Override
    public List<StockNewData> getStockHslValueRegion(StockNewDataVo stockNewDataVo) {
        List<StockNewData>  list= iStockNewDataDao.getStockHslValueRegion(stockNewDataVo);

        return list;
    }





}
