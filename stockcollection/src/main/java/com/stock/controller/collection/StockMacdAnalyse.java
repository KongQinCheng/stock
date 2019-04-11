package com.stock.controller.collection;

import com.stock.bean.po.StockInfo;
import com.stock.mapper.StockInfoMapper;
import com.stock.util.SpringUtil;

import java.util.ArrayList;
import java.util.List;

import static com.stock.controller.collection.StockKdjCollection.getStockListByStockCode;
import static com.stock.controller.collection.StockKdjCollection.getStockListByStockCodeLimit10;

public class StockMacdAnalyse {

    static StockInfoMapper stockInfoMapper = SpringUtil.getBean( StockInfoMapper.class);

    public static void main(String[] args)  throws Exception{

        String stockCode ="603383";

        getGoldenCrossOverZero(stockCode,0);

    }


    //获取0上金叉
    public static List<StockInfo> getGoldenCrossOverZero(String stockCode,int type){

        //

        List<StockInfo> stockListByStockCode = new ArrayList<>();
        if (type==0){
            stockListByStockCode = getStockListByStockCode(stockCode,999999999);
        }
        if (type==1){
            stockListByStockCode = getStockListByStockCodeLimit10(stockCode);
        }



        double DIFvalue =0;
        double DEAvalue =0;
        double MACDvalue =0;

        double DIFtype =0;  //小于0 还是大于0
        double DEAtype =0;
        double MACDtype =0;

        //获取第一天的数据 判断 DIF DEA MACD的值

        if (stockListByStockCode.size()>0){
            DIFvalue= stockListByStockCode.get(0).getDIF();
            DEAvalue=stockListByStockCode.get(0).getEMAMACD();
            MACDvalue=stockListByStockCode.get(0).getBAR();

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

        for (int i = 1; i < stockListByStockCode.size(); i++) {
            if (stockListByStockCode.get(i).getBAR()>0){  //MACD 柱>0 的情况

                //统计股价 对比今日收盘价 -昨日的收盘价>0 的数据占所有数据的 百分比




            }
//            if (stockListByStockCode.get(i).getDIF()<0) {  //DIF 柱>0 的情况
//                continue;
//            }
//            if (stockListByStockCode.get(i).getEMAMACD()<0) {  //DEA 柱>0 的情况
//                continue;
//            }
//

            //获取 0上 金叉的开始点   保存到数据库







        }





        return  null;
    }



    //死亡交叉Death cross



}
