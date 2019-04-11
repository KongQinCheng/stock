package com.stock.controller.collection;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.mapper.StockInfoMapper;
import com.stock.util.SpringUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.stock.controller.collection.StockKdjCollection.getStockListByStockCode;
import static com.stock.controller.collection.StockKdjCollection.getStockListByStockCodeLimit10;
import static com.stock.controller.collection.StockListCollection.getStockList;

public class StockMacdCollection {

    static StockInfoMapper stockInfoMapper = SpringUtil.getBean( StockInfoMapper.class);


    public static void main(String[] args)  throws Exception{

        DecimalFormat df = new DecimalFormat("#.0000");
        System.out.println(  getEMA12(55.01,53.7));
        System.out.println(  getEMA12(55.01,53.7));
        System.out.println(getEMA26(55.01,53.7));
        System.out.println(getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7)));
        System.out.println(getDEAMACD(0,getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7))));
        System.out.println(getBAR(getDEAMACD(0,getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7))),getDIFF(getEMA12(55.01,53.7),getEMA26(55.01,53.7))));
    }


    /***
     * 计算所有 股票的  MACD值
     */
    public static  void stockMacdInitALL( ){
        List<StockList> stockList = getStockList();

        double threadCount =200.0 ; //使用 20个线程处理

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


    /**
     * 查找数据库中 MACD值 为空的表
     */
    public static  void findMACDNullTable(){

        //获取列表
        List<StockList> stockList = getStockList();

        StockInfo stockInfo =new StockInfo();

        List<String> resultlist =new ArrayList<>();
        List<String> resultlist222 = new ArrayList<>();
        List<String> resultlist222333 = new ArrayList<>();

        for (int i = 0; i < stockList.size(); i++) {
            //循环列表
            StockList stockList1 =stockList.get(i);

            //判断表是否有数据
            stockInfo.setStockCode(stockList1.getStockCode().replaceAll("\t","")+"");

            try{
                List<StockInfo> stockInfoList = stockInfoMapper.getStockListByStockCode(stockInfo.getStockCode() ,999999999);

                    if(stockInfoList.get(0).getEMA12()==0){
                        resultlist222.add(stockInfo.getStockCode());
                    }
            }
            catch (Exception e){
                resultlist222333.add(stockInfo.getStockCode());
            }
        }
        System.out.println(resultlist222);
        System.out.println(resultlist222333);
    }


    /**
     * 开启多线程 计算MACD值
     */
    static class ThreadRunnable implements Runnable{
        private List<StockList> listInput;
        public ThreadRunnable(List<StockList> temp){
            this.listInput= temp;
        }

        @Override
        public void run() {
            for (int i = 0; i <listInput.size() ; i++) {
                try {

                    //初始化
//                    stockMacdInit(listInput.get(i).getStockCode().replaceAll("\t","")+"",0);

                    //每次更新新数据
                    stockMacdInit(listInput.get(i).getStockCode().replaceAll("\t","")+"",1);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    /***
     *
     * @param stockCode
     * @param type  0: 初始化， 1： 有新数据进行更新
     */

    public static  void stockMacdInit(String stockCode ,int type){

        List<StockInfo> stockListByStockCode = new ArrayList<>();
        if (type==0){
            stockListByStockCode = getStockListByStockCode(stockCode,999999999);
        }
        if (type==1){
            stockListByStockCode = getStockListByStockCodeLimit10(stockCode);
        }

        double lastDayEma12 =0;
        double lastDayEma26 =0;
        double lastDEAMACD =0;
        double todayDif=0;
        double todayBar=0;

        for (int i = 0; i <stockListByStockCode.size() ; i++) {
            StockInfo stockInfo =stockListByStockCode.get(i);

            if (type ==1){
                if (stockInfo.getEMA12()!=0) {
                    lastDayEma12 = stockInfo.getEMA12();
                    lastDayEma26 = stockInfo.getEMA26();
                    lastDEAMACD = stockInfo.getEMAMACD();
                    continue;
                }
            }


            lastDayEma12 =getEMA12(lastDayEma12,stockInfo.getSpj());
            lastDayEma26 =getEMA26(lastDayEma26,stockInfo.getSpj());
            todayDif= getDIFF(lastDayEma12,lastDayEma26);
            lastDEAMACD=getDEAMACD(lastDEAMACD,todayDif);
            todayBar=getBAR(lastDEAMACD,todayDif);

            stockInfo.setStockCode(stockCode);
            stockInfo.setEMA12(lastDayEma12);
            stockInfo.setEMA26(lastDayEma26);
            stockInfo.setEMAMACD(lastDEAMACD);
            stockInfo.setDIF(todayDif);
            stockInfo.setBAR(todayBar);
            stockInfoMapper.updateStockInfoMacd(stockInfo);
        }

    }






    //    EMA（12）= 前一日EMA（12）×11/13＋今日收盘价×2/13
    public static  double getEMA12(double lastDayEMA12,double todaySpj){
        double result=0;
        result=lastDayEMA12*11/13.0+todaySpj*2/13.0;
        return  result;
    }
    //    EMA（26）= 前一日EMA（26）×25/27＋今日收盘价×2/27
    public static  double getEMA26(double lastDayEMA26,double todaySpj){
        double result=0;
        result=lastDayEMA26*25/27.0+todaySpj*2/27.0;
        return  result;
    }
    //    DIFF=今日EMA（12）- 今日EMA（26）
    public static  double getDIFF(double lastDayEMA12,double lastDayEMA26){
        double result=0;
        result=lastDayEMA12 -lastDayEMA26;
        return  result;
    }
    //    DEA（MACD）= 前一日DEA×8/10＋今日DIF×2/10
    public static  double getDEAMACD(double lastDEA,double todayDIFF){
        double result=0;
        result=lastDEA*8/10.0 + todayDIFF*2/10.0;
        return  result;
    }
    //    BAR=2×(DIFF－DEA)
    public static  double getBAR(double DEA,double todayDIFF){
        double result=0;
        result=2*(todayDIFF-DEA);
        return  result;
    }

}
