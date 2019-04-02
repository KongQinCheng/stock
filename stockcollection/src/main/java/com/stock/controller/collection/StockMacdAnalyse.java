package com.stock.controller.collection;

import com.stock.bean.StockInfo;
import com.stock.mapper.StockInfoMapper;
import com.stock.util.SpringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.stock.controller.collection.StockKdjCollection.getStockListByShareCode;
import static com.stock.controller.collection.StockKdjCollection.getStockListByShareCodeLimit10;

public class StockMacdAnalyse {

    static StockInfoMapper stockInfoMapper = SpringUtil.getBean( StockInfoMapper.class);

    public static void main(String[] args)  throws Exception{

    }


    //获取0上金叉
    public List<StockInfo> getGoldenCrossOverZero(String stockCode,int type){

        List<StockInfo> stockListByShareCode = new ArrayList<>();
        if (type==0){
            stockListByShareCode = getStockListByShareCode(stockCode);
        }
        if (type==1){
            stockListByShareCode = getStockListByShareCodeLimit10(stockCode);
        }



        double DIFvalue =0;
        double DEAvalue =0;
        double MACDvalue =0;

        double DIFtype =0;  //小于0 还是大于0
        double DEAtype =0;
        double MACDtype =0;

        //获取第一天的数据 判断 DIF DEA MACD的值

        if (stockListByShareCode.size()>0){
            DIFvalue= stockListByShareCode.get(0).getDIF();
            DEAvalue=stockListByShareCode.get(0).getEMAMACD();
            MACDvalue=stockListByShareCode.get(0).getBAR();

            if (DIFvalue>0){
                DIFtype=1;
            }else {
                DIFtype=-1;
            }

            if (DEAvalue>0){
                DEAtype=1;
            }else {
                DEAtype=-1;
            }

            if (MACDvalue>0){
                MACDtype=1;
            }else {
                MACDtype=-1;
            }
        }

        for (int i = 1; i < stockListByShareCode.size(); i++) {




        }





        return  null;
    }



    //死亡交叉Death cross



}
