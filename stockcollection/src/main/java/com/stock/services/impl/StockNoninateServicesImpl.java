package com.stock.services.impl;

import com.stock.bean.StockInfo;
import com.stock.services.IStockNoninateServices;

import java.util.List;

public class StockNoninateServicesImpl implements IStockNoninateServices {
    @Override
    public void getStockNoninate(List<StockInfo> list, int dayNum) {


        //金交死交个编号说明：
        //第一位：0：死交，1；金交  ；
        //第二位：0：0下；1：0上
        //第三位：0：不存在震荡；1-n:存在N次震荡
        //第四位：0震荡过后处于死交；1震荡过后处于金交

        if (list.size() < 1) {
            return;
        }

        double beforeDIF = list.get(0).getDIF();
        double beforeDEA = list.get(0).getEMAMACD();
        double beforeMACD = list.get(0).getBAR();


        for (int i = 1; i < list.size(); i++) {

            list.get(i).getBAR();

            if (list.get(i).getBAR() > 0)

                if (beforeMACD > 0) {

                }

            if (beforeMACD > 0)

                //判断是否存在 金交的情况
                list.get(i).getDIF();


            //判断属于是 0 上金交还是 0下金交

            //判断是否存在 金交线条 震荡的情况


        }


    }
    public  boolean  haveCross(double beforeDIF,double beforeDEA,double todayDIF,double todayDEA){

        boolean result= false;




        return  result;
    }
}
