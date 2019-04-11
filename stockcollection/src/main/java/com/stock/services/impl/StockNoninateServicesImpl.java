package com.stock.services.impl;

import com.stock.Enum.CrossType;
import com.stock.bean.po.StockInfo;
import com.stock.services.IStockNoninateServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockNoninateServicesImpl implements IStockNoninateServices {
    @Override
    public List<Map<String, String>> getStockNoninateCross(List<StockInfo> list, int dayNum) {


        List<Map<String, String>> resultList = new ArrayList<>();
        //金交死交个编号说明：
        //第一位：0：死交，1；金交  ；
        //第二位：0：0下；1：0上
        //第三位：0：不存在震荡；1-n:存在N次震荡
        //第四位：0震荡过后处于死交；1震荡过后处于金交


        double beforeDIF = list.get(0).getDIF();
        double beforeDEA = list.get(0).getEMAMACD();
        double beforeMACD = list.get(0).getBAR();
        for (int i = 1; i < list.size(); i++) {
            Map<String, String> map = haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
            beforeDIF = list.get(i).getDIF();
            beforeDEA = list.get(i).getEMAMACD();

            if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来
                resultList.add(map);
            }
        }
        System.out.println("查找金叉 死叉完毕+" + list.get(0).getStockCode());
        return resultList;
    }

    @Override
    public Map<String,Object> isExitCross(List<StockInfo> list, int dayNum, String crossType) {

        Map<String,Object> resultMap= new HashMap<>();
        if (list.size()<1){
            resultMap.put("result",false);
            return resultMap;
        }

        double beforeDIF = list.get(0).getDIF();
        double beforeDEA = list.get(0).getEMAMACD();
        double beforeMACD = list.get(0).getBAR();
        for (int i = 1; i < list.size(); i++) {
            Map<String, String> map = haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
            beforeDIF = list.get(i).getDIF();
            beforeDEA = list.get(i).getEMAMACD();

            if (CrossType.GOLD_CROSS.toString().equals(crossType)) {
                if ("10".equals(map.get("type")) || "11".equals(map.get("type"))) {
                    resultMap.put("result",true);
                    resultMap.put("date",list.get(i).getStockDate());
                    return resultMap;
                }
            }
            if (CrossType.DEAD_CROSS.toString().equals(crossType)) {
                if ("00".equals(map.get("type")) || "01".equals(map.get("type"))) {
                    resultMap.put("result",true);
                    resultMap.put("date",list.get(i).getStockDate());
                    return resultMap;
                }
            }
        }
        return resultMap;
    }

    @Override
    public String isExitCrossAndDate(List<StockInfo> list, int dayNum, String crossType) {

         isExitCross( list,  dayNum,  crossType);

        return "";
    }


    public Map<String, String> haveCross(String day, double beforeDIF, double beforeDEA, double todayDIF, double todayDEA) {

        //beforeDIF-beforeDEA>0 即 macd>0上涨

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("type", "-1");
        if (beforeDIF - beforeDEA < 0 && todayDIF - todayDEA > 0) {

            if (todayDIF > 0 && todayDEA > 0) {
                resultMap.put("type", "11");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0上 金 叉");

            }
            if (todayDIF < 0 && todayDEA < 0) {
                resultMap.put("type", "10");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0下 金 叉");
            }
        }
        if (beforeDIF - beforeDEA > 0 && todayDIF - todayDEA < 0) {
            if (todayDIF > 0 && todayDEA > 0) {
                resultMap.put("type", "01");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0上 死 叉");
            }
            if (todayDIF < 0 && todayDEA < 0) {
                resultMap.put("type", "00");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 0下 死 叉");
            }
        }

        return resultMap;
    }
}
