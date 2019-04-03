package com.stock.controller.init;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.stock.controller.collection.StockNewInfoCollection.getNewInfoToTable;

@Component
public class InitProject implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        //项目初始化执行

        try {

            //保存最小的信息到 stock_info_new30表中
            getNewInfoToTable("603383",30);




//            delStockList();

            //获取具体的股票情况
//            getWycjSituationAll();

            //查找空表
//            findTable();

            //删除数据为空的表
//            delNullTable();

            //计算 MACD 值
//            stockMacdInitALL();

            //查找MACD为空的表
//            findMACDNullTable();


            //获取股票列表
//            getWycjStockList();


            //获取KD值
//            getKDJValue("600036");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }


    }
}
