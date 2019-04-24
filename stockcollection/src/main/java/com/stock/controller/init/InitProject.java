package com.stock.controller.init;


import com.stock.controller.collection.*;
import com.stock.services.IStockIncreaseAnalyzeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitProject implements ApplicationRunner {

    @Autowired
    StockNewDataCollection stockNewDataCollection;

    @Autowired
    StockInfoCollection stockInfoCollection;

    @Autowired
    IStockIncreaseAnalyzeServices iStockIncreaseAnalyzeServices;

    @Autowired
    StockNominateCollection stockNoninateCollection;

    @Autowired
    StockListCollection stockListCollection;

    @Autowired
    WeiboCollection weiboCollection;

    @Autowired
    StockMacdCollection stockMacdCollection;


    @Autowired
    StockIncreaseEffectCollection stockIncreaseEffectCollection;

    @Override
    public void run(ApplicationArguments args) {
        //项目初始化执行

        try {


            stockIncreaseEffectCollection.getStockIncreaseEffectThread();

            //计算MACD值 保存到表中
//            stockMacdCollection.stockMacdInitALL();

            //将最新的30天的的数据保存到独立的表中
//            stockNewDataCollection.getNewDataToTableThread();

//            weiboCollection.getWeiBoByUser();
//            stockMacdCollection.stockMacdInitALL();
//            weiboCollection.getDiaryinit();
//          weiboCollection.getWeiBoByUser();


//            stockListCollection.addStockNewList();

            //CountDownLatchTest 测试
//            CountDownLatchTest.getWycjSituationAll();

            //查找金叉死叉
//            stockNoninateCollection.getStockNoninate("603383",10);

//            iStockIncreaseAnalyzeServices.getStockIncreaseAnalyzeToTable("000001");


            //查询各个表中最新的30天的数据保存到 stock_new_data表中
//            stockNewDataCollection.getNewDataToTableThread();

            //保存最小的信息到 stock_info_new30表中
//            getNewInfoToTable("603383",30);


//            delStockList();

            //获取具体的股票情况
//            stockInfoCollection.getWycjSituationAll();

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

            //更新stockCode 一次性使用
//            stockInfoCollection.updateStockCode();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }


    }
}
