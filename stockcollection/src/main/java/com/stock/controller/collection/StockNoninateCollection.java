package com.stock.controller.collection;

import com.stock.Enum.SortType;
import com.stock.bean.StockInfo;
import com.stock.bean.StockNewDataVo;
import com.stock.bean.StockNominateVo;
import com.stock.dao.IStockInfoDao;
import com.stock.mapper.StockNewDataMapper;
import com.stock.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class StockNoninateCollection {

    @Autowired
    IStockInfoDao iStockInfoDao;

    public static void main(String[] args)  throws Exception{

        String stockCode ="603383";

//        getNewInfoToTable(stockCode,30);

    }


    //获取0上金叉
    public  void  getNewInfoToTable(String stockCode,int limitNum){

        List<StockInfo> stockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(),limitNum);
        for (int i = 0; i <stockListByStockCode.size() ; i++) {
//            StockNewData stockInfoNew =new StockNewData();
//            BeanUtils.copyProperties(stockListByStockCode.get(i),stockInfoNew);
//            stockInfoNewMapper.addStockInfoNew(stockInfoNew);
        }
    }


    public static List<StockInfo> getStockListByStockNominateVo(StockNewDataVo stockNewDataVo) {
        return null;
    }
}
