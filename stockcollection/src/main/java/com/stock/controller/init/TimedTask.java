package com.stock.controller.init;

import com.stock.controller.collection.StockInfoCollection;
import com.stock.controller.collection.StockNewDataCollection;
import com.stock.dao.IStockInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.stock.controller.collection.StockMacdCollection.stockMacdInitALL;


@Component
public class TimedTask {

    @Autowired
    StockInfoCollection stockInfoCollection;

    @Autowired
    StockNewDataCollection stockNewDataCollection;

    // 每天的17点、19点、21点都执行一次：0 0 17,19,21 * * ?
    @Scheduled(cron = "0 0 0,17,18 * * ?")
    public   void getStockInfo() throws Exception {
        stockInfoCollection.getWycjSituationAll();
    }

    // 每天的18点、20点、22点都执行一次：0 0 18,20,22 * * ?
    @Scheduled(cron = "0 0 18,19 * * ?")
    public  static void getStockMacd(){
        stockMacdInitALL();
    }

//    @Scheduled(cron = "* * * * * ?")
////    public  static void test(){
////        System.out.println("11111111111");
////    }


    //更新最新的单价到数据库中
    //时间具体在决定
//    @Scheduled(cron = "0 0 18,19 * * ?")
    public   void getStockNewPrice() throws Exception {
        //查询各个表中最新的30天的数据保存到 stock_new_data表中
            stockNewDataCollection.getNewDataToTableThread();
    }

}
