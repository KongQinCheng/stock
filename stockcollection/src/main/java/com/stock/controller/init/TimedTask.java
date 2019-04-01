package com.stock.controller.init;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.stock.controller.collection.StockInfoCollection.getWycjSituationAll;
import static com.stock.controller.collection.StockMacdCollection.stockMacdInitALL;


@Component
public class TimedTask {
    // 每天的17点、19点、21点都执行一次：0 0 17,19,21 * * ?
    @Scheduled(cron = "0 0 0,17,18,19 * * ?")
    public  static void getStockInfo() throws Exception {
        getWycjSituationAll();
    }

    // 每天的18点、20点、22点都执行一次：0 0 18,20,22 * * ?
    @Scheduled(cron = "0 0 18,19,20 * * ?")

    public  static void getStockMacd(){
        stockMacdInitALL();
    }

//    @Scheduled(cron = "* * * * * ?")
////    public  static void test(){
////        System.out.println("11111111111");
////    }
}
