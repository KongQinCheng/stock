package com.stock.controller.init;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockList;
import com.stock.controller.collection.*;
import com.stock.services.IStockListServices;
import com.stock.services.IStockMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class TimedTask {


    @Autowired
    StockListCollection stockListCollection;

    @Autowired
    IStockListServices iStockListServices;

    @Autowired
    StockInfoCollection stockInfoCollection;

    @Autowired
    StockNewDataCollection stockNewDataCollection;

    @Autowired
    StockMacdCollection stockMacdCollection;

    @Autowired
    WeiboCollection weiboCollection;

    @Autowired
    StockIncreaseEffectCollection stockIncreaseEffectCollection;


    @Autowired
    IStockMacdServices iStockMacdServices;


    @Scheduled(cron = "0 0 0,17 * * ?")
    public void getStockInfo() throws Exception {
        //获取新上市的新股票
        stockListCollection.getStockNewList();

        //获取每一只最新的股票信息
        getStockNewData();
    }


    @Scheduled(cron = "0 1 * * * ? ")   //5分钟获取一次微博的信息
    public void getWeiBo() throws Exception {
        weiboCollection.getWeiBoByUser();
    }


    private static final double THREAD_NUMBER = 100.0;

    public void getStockNewData() {
        List<StockList> stockList = iStockListServices.getStockList();
        CountDownLatch CountDownLatch_getStockNewData = new CountDownLatch((int) THREAD_NUMBER);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool((int) THREAD_NUMBER);
        int listSize = stockList.size();

        //将总数分成 多个线程之后，每个线程需要处理的数据为： listSize/threadCount
        double divNumd = Math.ceil(listSize / THREAD_NUMBER);
        int divNum = (int) divNumd;

        if (listSize > 0) {
            int batch = listSize % divNum == 0 ? listSize / divNum : listSize / divNum + 1;
            for (int j = 0; j < batch; j++) {
                int end = (j + 1) * divNum;
                if (end > listSize) {
                    end = listSize;
                }
                List<StockList> subList = stockList.subList(j * divNum, end);
                StockNewDataRunnable threadRunnable = new StockNewDataRunnable("getStockNewData", subList, CountDownLatch_getStockNewData);
                fixedThreadPool.execute(threadRunnable);
            }
        }
        try {
            CountDownLatch_getStockNewData.await();
            System.out.println("getStockNewData 执行结束。开始继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class StockNewDataRunnable implements Runnable {
        private String mName;
        private CountDownLatch countDownLatch;
        private List<StockList> listInput;

        public StockNewDataRunnable(String name, List<StockList> temp, CountDownLatch countDownLatch) {
            this.mName = name;
            this.listInput = temp;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            try {
                for (int i = 0; i < listInput.size(); i++) {
                    try {
                        //获取股票的最新信息
                        stockInfoCollection.getWycjSituation(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("stockInfoCollection.getWycjSituation 失败 stockCode="+listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }
                    try {
                        //计算MACD值
                        stockMacdCollection.stockMacdInit(listInput.get(i).getStockCode().replaceAll("\t", "") + "", 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("stockMacdCollection.stockMacdInit 失败 stockCode="+listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }
                    try {
                        //保存最新的数据到表中。
                        stockNewDataCollection.getNewDataToTable(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("stockNewDataCollection.getNewDataToTable 失败 stockCode="+listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }
                    try {
                        //计算每只股票前一天涨幅对后一天的影响
                        stockIncreaseEffectCollection.getStockIncreaseEffectInit(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("stockIncreaseEffectCollection.getStockIncreaseEffectInit 失败 stockCode="+listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }

                    try {
                        //金叉出现后对后一天的影响
                        iStockMacdServices.crossEffectInit(listInput.get(i).getStockCode().replaceAll("\t", "") + "","");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("iStockMacdServices.crossEffectInit 失败 stockCode="+listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }

                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
