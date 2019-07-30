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

import java.util.ArrayList;
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

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockInfoCciServices iStockInfoCciServices;

    @Autowired
    IStockAnalyzeRSIservices iStockAnalyzeRSIservices;

    @Autowired
    IStockAllTargetUpdateServices  iStockAllTargetUpdateServices;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //项目初始化执行


//            List<StockList> stockList = iStockListServices.getStockList();
//            List<StockList> returnlist =new ArrayList<>();
//            for (int i = 0; i <stockList.size() ; i++) {
//                try {
//                iStockInfoDao.alterTable(stockList.get(i).getStockCode().replaceAll("\t",""));
//                    System.out.println(stockList.get(i).getStockCode());
//                    System.out.println(i);
//                } catch (Exception e) {
//                    System.out.println(stockList.get(i).getStockCode());
//                    System.out.println(i);
//                  continue;
//                } finally {
//                }
//
//                iStockInfoCciServices.getCciValue("603383",14);

//            }

//            System.out.println("全部处理完成");



//            iStockAnalyzeRSIservices.getRSI("600206");

//            iStockInfoCciServices.getCciValue("603383",14);


//        iStockAllTargetUpdateServices.allTargetUpdate("603383");


    }
}
