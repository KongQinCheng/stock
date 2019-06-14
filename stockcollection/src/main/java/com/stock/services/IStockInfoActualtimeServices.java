package com.stock.services;


import com.stock.bean.po.StockInfoActualtime;

import java.util.List;
import java.util.Map;

public interface IStockInfoActualtimeServices {

    List<StockInfoActualtime> getByStockDate(String stockDate);

    void insert(StockInfoActualtime stockInfoActualtime );

    void delete(String stockCode);

    void deleteByStockCodeAndStockDate(String stockCode,String stockDate) ;

    void updateEffect(double persent);


}
