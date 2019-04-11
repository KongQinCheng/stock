package com.stock.controller.collection;

import com.stock.bean.po.StockList;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CommonThread {

    public  void getStockNoninateThread(List<StockList> stockList, double threadCount ,String ThreadObjName) throws Exception {

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
                Class<?> cls = Class.forName(ThreadObjName); // 取得Class对象
                Constructor<?> cons = cls.getConstructor(List.class);
                Object obj = cons.newInstance(stockList); // 为构造方法传递参数
                executor.execute((Runnable) obj);
            }
        }


    }
}
