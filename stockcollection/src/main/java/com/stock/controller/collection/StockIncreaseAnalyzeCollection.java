package com.stock.controller.collection;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class StockIncreaseAnalyzeCollection {

    @Autowired
    IStockListDao iStockListDao;

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockNewDataDao IStockNewDataDao;

    @Autowired
    IStockListServices iStockListServices;

    /***
     * 根据数据库中保存的 股票编号 获取股票的历史信息
     * @throws Exception
     */
    public  void getStockIncreaseAnalyzeToTableThread( ) throws Exception {

        //清空表的内容
        IStockNewDataDao.deleteAll();

        List<StockList> stockList = iStockListServices.getStockList();

        double threadCount =100.0 ; //使用 20个线程处理

        ExecutorService executor = Executors.newFixedThreadPool((int)threadCount);
        int listSize = stockList.size() ;
        //将总数分成 多个线程之后，每个线程需要处理的数据为： listSize/threadCount

        double divNumd  = Math.ceil(listSize/threadCount);
        int divNum  = (int)divNumd;

        if (listSize > 0) {
            int batch = listSize % divNum == 0 ? listSize / divNum : listSize / divNum + 1;
            for (int j=0; j<batch; j++) {
                int end = (j+1)*divNum;
                if (end > listSize) {
                    end = listSize;
                }
                List<StockList> subList = stockList.subList(j*divNum, end);
                ThreadRunnable threadRunnable = new ThreadRunnable(subList);
                executor.execute(threadRunnable);
            }
        }


    }

    /***
     *  开启多线程进行数据处理
     */
    class ThreadRunnable implements Runnable{
        private List<StockList> listInput;
        public ThreadRunnable(List<StockList> temp){
            this.listInput= temp;
        }

        @Override
        public void run() {
            for (int i = 0; i <listInput.size() ; i++) {
                try {
                    getStockIncreaseAnalyzeToTable(listInput.get(i).getStockCode().replaceAll("\t","")+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public  void getStockIncreaseAnalyzeToTable(String stockCode){
        List<StockInfo> newStockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), 30);
            for (int j = 0; j < newStockListByStockCode.size(); j++) {
                StockInfo stockInfo=newStockListByStockCode.get(j);
                stockInfo.setStockCode(stockCode);
                IStockNewDataDao.insert(stockInfo);
            }

    }
}
