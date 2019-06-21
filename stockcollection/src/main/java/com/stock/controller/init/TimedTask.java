package com.stock.controller.init;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class TimedTask {


    @Autowired
    IStockListServices iStockListServices;

    @Autowired
    IStockNewDataServices iStockNewDataServices;


    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;

    @Autowired
    IStockAnalyzeMacdServices iStockAnalyzeMacdServices;


    @Autowired
    IStockAnalyzeIncreaseServices iStockAnalyzeIncreaseServices;

    @Autowired
    IWebDiaryServices iWebDiaryServices;


    @Autowired
    IStockInfoServices iStockInfoServices;

    @Autowired
    IStockInfoKdjServices iStockInfoKdjServices;


    @Scheduled(cron = "0 0 16 * * ?")
    public void getStockInfo() throws Exception {
        //获取新上市的新股票
//        iStockListServices.getStockNewList();

        //获取每一只最新的股票信息
        getStockNewData();
    }


    @Scheduled(cron = "0 0 0/2 * * ?")   //1分钟获取一次微博的信息
    public void getWeiBo() throws Exception {
        iWebDiaryServices.getWeiBoByUser();
        System.out.println("微博采集完成");
    }


    @Scheduled(cron = "0 30-59/2 14 * * ?")
    public void getStockMACDActualTime() throws Exception {
        StockMACDActualTime();
    }


    private static final double THREAD_NUMBER = 30.0;

    public void getStockNewData() {

        List<StockList> stockList2 = iStockListServices.getStockList();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        int count =0;
        List<StockList> stockList =new ArrayList<>();
        for (int i = 0; i <stockList2.size() ; i++) {
            List<StockInfo> list = iStockInfoServices.getStockListByStockCodeAndStockDateLimit(stockList2.get(i).getStockCode().replaceAll("\t", "") + "",sdf2.format(day));
            if (list.size()==0){
                stockList.add(stockList2.get(i));
                System.out.println(stockList2.get(i).getStockCode());
                count++;
                System.out.println(count);
            }
        }


//        List<StockList> stockList = iStockListServices.getStockList();
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

                    List<StockInfo> list = iStockInfoServices.getStockListByStockCode(listInput.get(i).getStockCode().replaceAll("\t", "") + "",10000000);

                    try {
                        //删除表中当天的数据（计算实时的MACD的时候，添加进去的，数据不准）
                        iStockInfoServices.delEmptyStockInfo(listInput.get(i).getStockCode().replaceAll("\t", ""));

                        //获取股票的最新信息
                        iStockInfoServices.getStockInfoHistory(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(" iStockInfoServices.getStockInfoHistory 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }
                    try {
                        //计算MACD值
                        iStockInfoMacdServices.getStockInfoMacd(listInput.get(i).getStockCode().replaceAll("\t", "") + "", 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("iStockInfoMacdServices.getStockInfoMacd 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }

                    try {
                        //KDJ初所化
                        iStockInfoKdjServices.getKDJValue(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("iStockInfoKdjServices.getKDJValue 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }

                    try {
                        //保存最新的数据到表中。
                        iStockNewDataServices.getNewDataToTable(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("iStockNewDataServices.getNewDataToTable 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
                    }
//                    try {
//                        //计算每只股票前一天涨幅对后一天的影响
////                        iStockAnalyzeIncreaseServices.getStockAnalyzeIncreaseAll(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("iStockAnalyzeIncreaseServices.getStockIncreaseEffectInit 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
//                    }
//
//                    try {
//                        //金叉出现后对后一天的影响
//                        iStockAnalyzeMacdServices.crossEffectInitNew(listInput.get(i).getStockCode().replaceAll("\t", "") + "", "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("iStockAnalyzeMacdServices.crossEffectInitNew 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
//                    }
//
//                    try {
//                        //金叉出现后有多少的前面几天内一定会涨
//                        iStockAnalyzeMacdServices.crossEffectInitNewFinal(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("iStockAnalyzeMacdServices.crossEffectInitNew 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
//                    }
//
//                    try {
//                        //金叉出现后后一天的涨幅的区间
//                        iStockAnalyzeMacdServices.crossEffectInitNewFinalMaxvalue(listInput.get(i).getStockCode().replaceAll("\t", "") + "");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("iStockAnalyzeMacdServices.crossEffectInitNewFinalMaxvalue 失败 stockCode=" + listInput.get(i).getStockCode().replaceAll("\t", ""));
//                    }



                }
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    @Autowired
    IStockListDao iStockListDao;

    public void StockMACDActualTime() {
        List<StockList> stockList = iStockListDao.getStockListDesc();
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
                StockMACDActualTimeRunnable threadRunnable = new StockMACDActualTimeRunnable( subList);
                fixedThreadPool.execute(threadRunnable);
            }
        }
    }


    public class StockMACDActualTimeRunnable implements Runnable {
        private List<StockList> listInput;
        public StockMACDActualTimeRunnable( List<StockList> temp) {
            this.listInput = temp;
        }

        public void run() {
            try {
                for (int i = 0; i < listInput.size(); i++) {
                    iStockInfoServices.getStockInfoActualTime(listInput.get(i).getStockCode().replaceAll("\t", ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
