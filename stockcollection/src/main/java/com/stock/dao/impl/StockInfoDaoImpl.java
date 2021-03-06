package com.stock.dao.impl;

import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockInfoDao;
import com.stock.mapper.StockInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockInfoDaoImpl implements IStockInfoDao {


    @Autowired
    StockInfoMapper stockInfoMapper;

    @Override
    public List<StockInfo> getStockListByStockCode(String stockCode ,int limitNum) {
        List<StockInfo> stockListByStockCode = stockInfoMapper.getStockListByStockCode(stockCode,limitNum);
        return stockListByStockCode;
    }

    @Override
    public List<StockInfo> getStockListByStockCodeLimit(String stockCode ,int limitNum) {
        List<StockInfo> stockListByStockCode = stockInfoMapper.getStockListByStockCodeLimit(stockCode,limitNum);
        return stockListByStockCode;
    }

    @Override
    public List<StockInfo> getStockListByStockCodeAndStockDateLimit(String stockCode, String stockDate) {
        List<StockInfo> stockListByStockCode = stockInfoMapper.getStockListByStockCodeAndStockDateLimit(stockCode,stockDate);
        return stockListByStockCode;
    }


    @Override
    public List<StockInfo> getNewStockListByStockCode(String stockCode, String sortType ,int limitNum) {
        List<StockInfo> stockListByStockCode = stockInfoMapper.getNewStockListByStockCode(stockCode,sortType,limitNum);
        return stockListByStockCode;
    }


    @Override
    public void updateStockInfoAll(StockInfo stockInfo) {
        stockInfoMapper.updateStockInfoALL(stockInfo);
    }

    @Override
    public void createTableByTableName(String tableName) {
        stockInfoMapper.createTableByTableName(tableName);
    }

    @Override
    public void updateStockInfoMacd(StockInfo stockInfo) {
        stockInfoMapper.updateStockInfoMacd(stockInfo);
    }

    @Override
    public void updateStockInfoKDJ(StockInfo stockInfo) {
        stockInfoMapper.updateStockInfoKDJ(stockInfo);
    }
    @Override
    public void updateStockInfoCCI(StockInfo stockInfo) {
        stockInfoMapper.updateStockInfoCCI(stockInfo);
    }



    @Override
    public void updateStockInfoRSI(StockInfo stockInfo) {
        stockInfoMapper.updateStockInfoRSI(stockInfo);
    }



    @Override
    public void updateStockInfoMacdNoDate(StockInfo stockInfo) {
        stockInfoMapper.updateStockInfoMacdNoDate(stockInfo);
    }

    @Override
    public void updateStockMacdCciKdj(StockInfo stockInfo) {
        stockInfoMapper.updateStockMacdCciKdj(stockInfo);
    }




    @Override
    public  boolean isTableExist(String tableName)   {
        boolean isExist=false;
        double tableExist = stockInfoMapper.isTableExist(tableName);
        if (tableExist==1){
            isExist= true;
        }
        return isExist;
    }

    @Override
    public void addStockInfo(StockInfo stockInfo){
        stockInfoMapper.addStockInfo(stockInfo);
    }

    @Override
    public void delStockInfo(String stockCode, String stockDate) {
        stockInfoMapper.delStockInfo(stockCode,stockDate);
    }

    @Override
    public void delEmptyStockInfo(String stockCode) {
        stockInfoMapper.delEmptyStockInfo(stockCode);
    }


    @Override
    public boolean isRoweExist(StockInfo stockInfo){
        if(stockInfoMapper.isRoweExist(stockInfo)>=1)
            return true;
        return  false;
    }

    @Override
    public void alterTable(String stockCode) {
        stockInfoMapper.alterTable(stockCode);
    }

    @Override
    public void delTableByStockCode(String stockCode) {
        stockInfoMapper.delTableByStockCode(stockCode);
    }

}
