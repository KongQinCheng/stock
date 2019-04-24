package com.stock.controller.collection;

import com.stock.bean.po.StockIncreaseAnalyze;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockIncreaseAnalyzeServices;
import com.stock.services.IStockListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class IndexCollection {

    //判断每只股票， 前一天处于哪个涨幅的时候，后一天会涨的比例
    //截止到2019 -04-23
    //统计数据为  stockCode= 000000  的数据。
//    StockIncreaseAnalyzeCollection.getStockIncreaseEffectInit

    //获取新上市的新股票
//  stockListCollection.getStockNewList();


    //获取股票的最新信息
//    stockInfoCollection.getWycjSituation("");

    //计算MACD值
//     stockMacdCollection.stockMacdInit("",1);

    //保存最新的数据到表中。
//     stockNewDataCollection.getNewDataToTable("");

    //根据区间 获取涨跌幅的 排行榜
   // StockNoninateController.getNominateInfo




    //定时获取微博的信息
    //  weiboCollection.getWeiBoByUser();





}
