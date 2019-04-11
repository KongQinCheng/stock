package com.stock.controller.Thread;

import com.stock.bean.po.StockList;

import java.util.List;

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
//                    stockListByStockCode(listInput.get(i).getStockCode().replaceAll("\t","")+"",0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }