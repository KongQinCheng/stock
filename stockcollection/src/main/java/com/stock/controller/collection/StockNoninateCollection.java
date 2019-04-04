package com.stock.controller.collection;

import com.stock.Enum.SortType;
import com.stock.bean.StockInfo;
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

        List<StockInfo> stockListByShareCode = iStockInfoDao.getNewStockListByShareCode(stockCode, SortType.ASC.toString(),limitNum);
        for (int i = 0; i <stockListByShareCode.size() ; i++) {
//            StockInfoNew stockInfoNew =new StockInfoNew();
//            BeanUtils.copyProperties(stockListByShareCode.get(i),stockInfoNew);
//            stockInfoNewMapper.addStockInfoNew(stockInfoNew);
        }
    }


    public static List<StockInfo> getStockListByStockNominateVo(StockNominateVo stockNominateVo) {
        return null;
    }
}
