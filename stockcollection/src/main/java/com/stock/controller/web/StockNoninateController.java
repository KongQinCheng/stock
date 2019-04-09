package com.stock.controller.web;

import com.stock.Enum.SortType;
import com.stock.bean.StockInfo;
import com.stock.bean.StockNewDataVo;
import com.stock.dao.IStockInfoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class StockNoninateController {

    @Autowired
    IStockInfoDao iStockInfoDao;

    public static void main(String[] args)  throws Exception{

        String stockCode ="603383";

//        getNewInfoToTable(stockCode,30);

    }


    //获取 指定天数的0上金叉
    public  void  getNewInfoToTable(String stockCode,int limitNum ,int dayNum){

        List<StockInfo> stockListByShareCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(),limitNum);
        for (int i = 0; i <stockListByShareCode.size() ; i++) {
//            StockNewData stockInfoNew =new StockNewData();
//            BeanUtils.copyProperties(stockListByShareCode.get(i),stockInfoNew);

//            stockInfoNewMapper.addStockInfoNew(stockInfoNew);
        }
    }


    public static List<StockInfo> getStockListByStockNominateVo(StockNewDataVo stockNewDataVo) {
        return null;
    }
}
