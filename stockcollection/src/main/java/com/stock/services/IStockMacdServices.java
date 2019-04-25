package com.stock.services;

import com.stock.bean.po.StockInfo;

import java.util.List;
import java.util.Map;

public interface IStockMacdServices {

     /***
      * 获取 dayNum 天数内的 金叉死叉 情况
      * @param list
      * @param dayNum
      * @return
      */
     List<Map<String,String>> getStockCross(List<StockInfo> list, int dayNum);


     /***
      * 判读多少天内 是否存在 金叉 或者死叉
      * @param list
      * @param dayNum
      * @param crossType
      * @return
      */
     Map<String,Object> isExistCross(List<StockInfo> list, int dayNum, String crossType);


     /***
      * 初始化各个股票出现金叉之后对后续的影响
      * @param stockCode
      * @param type  预留给之后的 涨幅和跌幅
      */
     void crossEffectInit(String stockCode, String type);


     /***
      * 统计所有数据的 出现金叉之后对后续的影响
      * @param type 预留给之后的 涨幅和跌幅
      */
     void crossEffectAllInit(String type);




     /***
      * 判断是否为最新数据 进行更新
      * @param stockCode
      * @return
      */
     String  getCrossEffect(String stockCode);
}
