package com.stock.controller.init;


import com.stock.Enum.SortType;
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
    IStockAllTargetUpdateServices iStockAllTargetUpdateServices;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //项目初始化执行


//        List<StockList> stockList = iStockListServices.getStockList();
//        List<StockList> returnlist = new ArrayList<>();
//        int count = 1;
//        for (int i = 0; i < stockList.size(); i++) {
//            try {
//
//                List<StockInfo> stockInfoListAll = iStockInfoDao.getNewStockListByStockCode(stockList.get(i).getStockCode().replaceAll("\t", "") + "", SortType.ASC.toString(), 1);
//
//                if (stockInfoListAll.get(0).getCci() == 0) {
//                    System.out.println(stockList.get(i).getStockCode());
//                    System.out.println("count=" + count++);
//                }
//
//            } catch (Exception e) {
//                System.out.println(stockList.get(i).getStockCode());
//                System.out.println("i=" + i);
//                continue;
//            } finally {
//            }
////                iStockInfoCciServices.getCciValue("603383",14);
//        }
//
//        System.out.println("全部处理完成");
//
//        iStockAnalyzeRSIservices.getRSI("600206");
//
//        iStockInfoCciServices.getCciValue("603383", 14);
//
//
//        iStockInfoServices.getStockInfoHistory("603838");
//        iStockAllTargetUpdateServices.allTargetUpdate("603838");
//
//
//        iStockInfoServices.getStockInfoHistory("603383");  //顶点软件
//        iStockAllTargetUpdateServices.allTargetUpdate("603383");
//
//        iStockInfoServices.getStockInfoHistory("000001");
//        iStockAllTargetUpdateServices.allTargetUpdate("000001");

    }
}
