package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockNewDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockNewDataServicesImpl implements IStockNewDataServices  {

    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Override
    public void getNewDataToTable(String stockCode,int limitNum,String insertType) {
        //清空表的内容
        iStockNewDataDao.deleteByStockCode(stockCode);
        List<StockInfo> newStockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), limitNum);

        double hsl =0;

        for (int j = 0; j < newStockListByStockCode.size(); j++) {
            StockInfo stockInfo=newStockListByStockCode.get(j);
            stockInfo.setStockCode(stockCode);
            hsl+=stockInfo.getHsl();
            if ("1".equals(insertType) && j==newStockListByStockCode.size()-1) {
                double d =hsl/newStockListByStockCode.size();
                d = (double) Math.round(d * 100) / 100;
                stockInfo.setHsl(d);
                iStockNewDataDao.insert(stockInfo);
            }
            if ("0".equals(insertType) ){
                iStockNewDataDao.insert(stockInfo);
            }
        }

        System.out.println("拷贝最新的数据保存的数据库中成功stockCode= "+stockCode);

    }

    @Override
    public List<StockNewData>   getNewData(StockNewDataVo stockNewDataVo) {
       return  iStockNewDataDao.getStockNewDataListByVo(stockNewDataVo);
    }
}
