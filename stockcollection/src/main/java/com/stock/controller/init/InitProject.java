package com.stock.controller.init;


import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitProject implements ApplicationRunner {


    @Autowired
    IStockAnalyzeMacdServices iStockAnalyzeMacdServices;

    @Autowired
    IStockInfoServices iStockInfoServices;

    @Autowired
    IStockListServices iStockListServices;

    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;

    @Override
    public void run(ApplicationArguments args) {
        //项目初始化执行

        try {

            //更新 002032 macd 值
//            StockInfo stockInfo =new StockInfo();
//            stockInfo.setStockCode("002032");
//            stockInfo.setDIF(0);
//            stockInfo.setEMA12(0);
//            stockInfo.setEMA26(0);
//            stockInfo.setEMAMACD(0);
//            stockInfo.setBAR(0);
//            iStockInfoServices.updateStockInfoMacdNoDate(stockInfo);
//            iStockInfoMacdServices.getStockInfoMacd("002032", 0);



//            List<StockList> stockList = iStockListServices.getStockList();

//            for (int i = 0; i <stockList.size() ; i++) {
//                iStockInfoServices.delStockInfo(stockList.get(i).getStockCode().replaceAll("\t", "") + "","2019-06-10");
//                System.out.println(stockList.get(i).getStockCode());
//                System.out.println(i);
//            }
//            System.out.println("全部处理完成");


//            iStockInfoServices.getStockInfoActualTime("603383");
//            iStockAnalyzeMacdServices.getStockCrossEffectNewFinal("603383","11");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }


    }
}
