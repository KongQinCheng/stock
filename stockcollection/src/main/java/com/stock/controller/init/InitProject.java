package com.stock.controller.init;


import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.services.*;
import com.stock.services.impl.StockInfoKdjServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @Autowired
    IStockInfoActualtimeServices iStockInfoActualtimeServices;

    @Autowired
    IStockInfoKdjServices iStockInfoKdjServices;

    @Override
    public void run(ApplicationArguments args) {
        //项目初始化执行

        try {


            List<StockList> stockList = iStockListServices.getStockList();

            for (int i = 0; i <stockList.size() ; i++) {

                List<StockInfo> list = iStockInfoServices.getStockListByStockCode(stockList.get(i).getStockCode().replaceAll("\t", "") + "",10000000);

                iStockInfoServices.delStockInfo(stockList.get(i).getStockCode().replaceAll("\t", "") + "","2019-06-18");
                iStockInfoServices.delStockInfo(stockList.get(i).getStockCode().replaceAll("\t", "") + "","2019-06-19");
//                iStockInfoServices.delStockInfo(stockList.get(i).getStockCode().replaceAll("\t", "") + "","2019-06-16");
//                iStockInfoServices.delStockInfo(stockList.get(i).getStockCode().replaceAll("\t", "") + "","2019-06-17");
                System.out.println(stockList.get(i).getStockCode());
                System.out.println(i);
            }


            System.out.println("全部处理完成");


//            iStockInfoServices.getStockInfoActualTime("603383");
//            iStockAnalyzeMacdServices.getStockCrossEffectNewFinal("603383","11");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }


    }
}
