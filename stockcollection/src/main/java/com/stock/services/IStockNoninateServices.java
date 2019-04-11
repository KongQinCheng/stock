package com.stock.services;

import com.stock.bean.po.StockInfo;

import java.util.List;
import java.util.Map;

public interface IStockNoninateServices {

     /***
      * 获取 dayNum 天数内的 金叉死叉 情况
      * @param list
      * @param dayNum
      * @return
      */
     List<Map<String,String>>  getStockNoninateCross(List<StockInfo> list, int dayNum);


     /***
      * 判读多少天内 是否存在 金叉 或者死叉
      * @param list
      * @param dayNum
      * @param crossType
      * @return
      */
     Map<String,Object>  isExitCross(List<StockInfo> list, int dayNum,String crossType);

     /***
      * 判断是否 存在 金叉死叉 和 金叉死叉的出现时间
      * @param list
      * @param dayNum
      * @param crossType
      * @return
      */
     String  isExitCrossAndDate(List<StockInfo> list, int dayNum,String crossType);

}
