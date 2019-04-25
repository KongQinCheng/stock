package com.stock.controller.collection;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockListServices;
import com.stock.services.IStockMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StockNominateCollection {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockMacdServices iStockMacdServices;


    @Autowired
    IStockListServices iStockListServices;

    public static void main(String[] args)  throws Exception{

        String stockCode ="603383";

//        getNewInfoToTable(stockCode,30);

    }


    public  void getStockNoninateThread( ) throws Exception {

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
                StockNominateCollection.ThreadRunnable threadRunnable = new StockNominateCollection.ThreadRunnable(subList);
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
                    getStockNoninate(listInput.get(i).getStockCode().replaceAll("\t","")+"",0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }




    //获取0上金叉
    public  void  getStockNoninate(String stockCode,int limitNum){
        List<StockInfo> stockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(),limitNum);
        iStockMacdServices.getStockCross(stockListByStockCode,300);
    }

}
