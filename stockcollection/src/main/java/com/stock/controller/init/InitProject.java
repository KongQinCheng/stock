package com.stock.controller.init;


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

    @Override
    public void run(ApplicationArguments args) {
        //项目初始化执行

        try {
//            List<StockList> stockList = iStockListServices.getStockList();
//
//            for (int i = 0; i <stockList.size() ; i++) {
//                iStockInfoServices.delStockInfo(stockList.get(i).getStockCode().replaceAll("\t", "") + "","2019-05-27");
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
