package com.stock.services.impl;

import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockIncreaseAnalyzeDao;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockIncreaseAnalyzeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockIncreaseAnalyzeServicesImpl implements IStockIncreaseAnalyzeServices {



    @Autowired
    IStockIncreaseAnalyzeDao iStockIncreaseAnalyzeDao;


    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @Autowired
    IStockInfoDao iStockInfoDao;


    @Override
    public void getStockIncreaseAnalyzeToTable(String stockCode) {


        //根据股票代码查询历史数据
        List<StockInfo> stockListByStockCode = iStockInfoDao.getStockListByStockCode(stockCode,999999999);

        //根据历史数据查询每日的涨幅对后一天的影响；


        for (int i = 0; i <30; i++) {
            getProbabilityByBeforeDay(stockListByStockCode,i);
        }

        for (int i = 1; i <31; i++) {
            getAvgByBeforeDay(stockListByStockCode,i);
        }



//        StockIncreaseAnalyze stockIncreaseAnalyze=new StockIncreaseAnalyze();
//        iStockIncreaseAnalyzeDao.insert(stockIncreaseAnalyze);
    }


    /***
     * 前面后一天的上浮 对今天的影响
     * @param list
     * @param beginIndex
     * @return
     */
    public double getProbabilityByBeforeDay(List<StockInfo> list ,int beginIndex){

        double beforeDayIncrease=0; //初始化涨幅为0
        double increaseCount=0;

        for (int i = beginIndex; i <list.size(); i++) {
            if (beforeDayIncrease>=0 && list.get(i).getZdf()>=0){
                increaseCount++;
            }
            beforeDayIncrease=list.get(i-beginIndex).getZdf();
//            System.out.println("前 "+beginIndex+++" 天的涨幅对后一天的影响的概率为："+increaseCount/list.size());
        }
        beginIndex=beginIndex+1;
        System.out.println("前 "+beginIndex +"天的涨幅对后一天的影响 最终 的概率为："+increaseCount/list.size()); //0.2565789473684211
        return increaseCount/list.size();
    }








    public double getAvgByBeforeDay(List<StockInfo> list ,int avgDay){


        double increaseCount=0;
        double[] zdfArrzy =new  double[avgDay];

        for (int i = 0; i < list.size(); i++) {

            if ( getCount(zdfArrzy)>0 && list.get(i).getZdf()>=0){
                increaseCount++;
            }
            zdfArrzy[i%avgDay]=list.get(i).getZdf();
        }
        System.out.println("前 "+avgDay +"天的 平均 涨幅对后一天的影响 最终 的概率为："+increaseCount/list.size()); //0.2565789473684211
        return increaseCount/list.size();

    }

    public double getCount(double[] zdfArrzy){
        double increaseCount=0;
        for (int i = 0; i < zdfArrzy.length; i++) {
            increaseCount+=zdfArrzy[i];
        }
        return increaseCount;
    }




    public double getProbability(List<StockInfo> list ,int beginIndex, int endIndex){

        for (int i = beginIndex; i < endIndex; i++) {



        }

        return  0;
    }
}
