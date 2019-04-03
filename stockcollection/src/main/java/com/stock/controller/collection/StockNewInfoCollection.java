package com.stock.controller.collection;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.stock.bean.StockInfo;
import com.stock.bean.StockInfoNew;
import com.stock.mapper.StockInfoMapper;
import com.stock.mapper.StockInfoNewMapper;
import com.stock.util.SpringUtil;
import org.springframework.beans.BeanUtils;

import java.util.List;

import static com.stock.controller.collection.StockKdjCollection.getStockListByShareCodeLimit;

public class StockNewInfoCollection {

    static StockInfoNewMapper stockInfoNewMapper = SpringUtil.getBean( StockInfoNewMapper.class);

    public static void main(String[] args)  throws Exception{

        String stockCode ="603383";

        getNewInfoToTable(stockCode,30);

    }


    //获取0上金叉
    public static void  getNewInfoToTable(String stockCode,int dataNum){

        List<StockInfo> stockListByShareCode = getStockListByShareCodeLimit(stockCode,dataNum);

        for (int i = 0; i <stockListByShareCode.size() ; i++) {

            StockInfoNew stockInfoNew =new StockInfoNew();
            BeanUtils.copyProperties(stockListByShareCode.get(i),stockInfoNew);

            stockInfoNewMapper.addStockInfoNew(stockInfoNew);
        }





    }
}
