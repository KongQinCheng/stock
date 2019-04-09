package com.stock.services;

import com.stock.bean.StockInfo;

import java.util.List;

public interface IStockNoninateServices {

    public void getStockNoninate(List<StockInfo> list, int dayNum);

}
