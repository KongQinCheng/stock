package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockInfoActualtime;
import com.stock.dao.IStockInfoActualtimeDao;
import com.stock.dao.IStockInfoDao;
import com.stock.mapper.StockInfoMapper;
import com.stock.services.IStockInfoActualtimeServices;
import com.stock.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoActualtimeServicesImpl implements IStockInfoActualtimeServices {

    @Autowired
    IStockInfoActualtimeDao iStockInfoActualtimeDao;

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Override
    public List<StockInfoActualtime> getByStockDate(String stockDate) {
        return iStockInfoActualtimeDao.getByStockDate( stockDate) ;
    }

    @Override
    public void insert(StockInfoActualtime stockInfoActualtime) {
        iStockInfoActualtimeDao.insert(stockInfoActualtime);
    }

    @Override
    public void delete(String stockCode) {
        iStockInfoActualtimeDao.delete(stockCode);
    }

    @Override
    public void deleteByStockCodeAndStockDate(String stockCode, String stockDate) {
        iStockInfoActualtimeDao.deleteByStockCodeAndStockDate(stockCode,stockDate);
    }

    @Override
    public void updateEffect(double persent) {

        List<StockInfoActualtime> data = iStockInfoActualtimeDao.getByNullData();

        for (int i = 0; i <data.size() ; i++) {
            StockInfoActualtime stockInfoActualtime = data.get(i);
            List<StockInfo> newStockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockInfoActualtime.getStockCode(), SortType.ASC.toString(), 5);

            for (int j = 0; j <newStockListByStockCode.size() ; j++) {
                if ( stockInfoActualtime.getStockDate().equals(newStockListByStockCode.get(j).getStockDate().replace(" 00:00:00.0",""))){
                    if (j+1<newStockListByStockCode.size()){
                        if (newStockListByStockCode.get(j+1).getZgj()-newStockListByStockCode.get(j).getSpj()> newStockListByStockCode.get(j).getSpj()*persent){
                            StockInfoActualtime stockInfoActualtime1 =new StockInfoActualtime();
                            stockInfoActualtime.setEffectDay1("1");
                             iStockInfoActualtimeDao.update(stockInfoActualtime);
                        }
                    }
                }
            }

        }
    }
}
