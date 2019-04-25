package com.stock.services.impl;

import com.alibaba.fastjson.JSON;
import com.stock.Enum.CrossType;
import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockCrossDao;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockMacdServicesImpl implements IStockMacdServices {

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockCrossDao iStockCrossDao;


    @Override
    public List<Map<String, String>> getStockCross(List<StockInfo> list, int dayNum) {


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
    public Map<String, Object> isExistCross(List<StockInfo> list, int dayNum, String crossType) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", false);
        if (list.size() < 1) {
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
                    resultMap.put("result", true);
                    resultMap.put("date", list.get(i).getStockDate());
                    return resultMap;
                }
            }
            if (CrossType.DEAD_CROSS.toString().equals(crossType)) {
                if ("00".equals(map.get("type")) || "01".equals(map.get("type"))) {
                    resultMap.put("result", true);
                    resultMap.put("date", list.get(i).getStockDate());
                    return resultMap;
                }
            }
        }
        return resultMap;
    }


    @Override
    public void crossEffectInit(String stockCode, String type) {

        //删除历史数据
        iStockCrossDao.delete(stockCode);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        List<Map<String, String>> resultList = new ArrayList<>();
        List<StockInfo> list = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);

        if (list != null && list.size() > 0) {

            double increase1 = 0.0;
            double increase2 = 0.0;
            double increase3 = 0.0;
            double increase4 = 0.0;
            double increase5 = 0.0;
            double crossCount = 0.0;

            double beforeDIF = list.get(0).getDIF();
            double beforeDEA = list.get(0).getEMAMACD();
            double beforeMACD = list.get(0).getBAR();
            for (int i = 1; i < list.size(); i++) {
                Map<String, String> map = haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
                beforeDIF = list.get(i).getDIF();
                beforeDEA = list.get(i).getEMAMACD();

                if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来

                    //统计一共出现多少次金叉
                    crossCount += 1.0;
                    //判断涨幅情况
                    int tempindex = i;
                    if (tempindex + 1 < list.size()) {
                        if (list.get(tempindex + 1).getZdf() >= 0) {
                            increase1 += 1.0;
                        }
                    }
                    if (tempindex + 2 < list.size()) {
                        if (list.get(tempindex + 2).getZdf() >= 0) {
                            increase2 += 1.0;
                        }
                    }
                    if (tempindex + 3 < list.size()) {
                        if (list.get(tempindex + 3).getZdf() >= 0) {
                            increase3 += 1.0;
                        }
                    }
                    if (tempindex + 4 < list.size()) {
                        if (list.get(tempindex + 4).getZdf() >= 0) {
                            increase4 += 1.0;
                        }
                    }
                    if (tempindex + 5 < list.size()) {
                        if (list.get(tempindex + 5).getZdf() >= 0) {
                            increase5 += 1.0;
                        }
                    }
                }
            }

            StockCross stockCross = new StockCross();
            stockCross.setStockCode(stockCode);
            stockCross.setStockDate(stockDate);
            stockCross.setIncrease1(increase1 / crossCount);
            stockCross.setIncrease2(increase2 / crossCount);
            stockCross.setIncrease3(increase3 / crossCount);
            stockCross.setIncrease4(increase4 / crossCount);
            stockCross.setIncrease5(increase5 / crossCount);
            stockCross.setCount(crossCount);
            iStockCrossDao.insert(stockCross);

        }
        System.out.println("金叉影响 更新完毕stockCode="+stockCode);
    }

    @Override
    public void crossEffectAllInit( String type){

        double increase1 = 0.0;
        double increase2 = 0.0;
        double increase3 = 0.0;
        double increase4 = 0.0;
        double increase5 = 0.0;
        double crossCount = 0.0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        iStockCrossDao.delete("000000");
        List<StockCross> stockCrossList = iStockCrossDao.getStockCrossList();
        for (int i = 0; i < stockCrossList.size(); i++) {
            increase1+=stockCrossList.get(i).getIncrease1()*stockCrossList.get(i).getCount();
            increase2+=stockCrossList.get(i).getIncrease2()*stockCrossList.get(i).getCount();
            increase3+=stockCrossList.get(i).getIncrease3()*stockCrossList.get(i).getCount();
            increase4+=stockCrossList.get(i).getIncrease4()*stockCrossList.get(i).getCount();
            increase5+=stockCrossList.get(i).getIncrease5()*stockCrossList.get(i).getCount();
            crossCount+=stockCrossList.get(i).getCount();
        }
        StockCross stockCross = new StockCross();
        stockCross.setStockCode("000000");
        stockCross.setStockDate(stockDate);
        stockCross.setIncrease1(increase1 / crossCount);
        stockCross.setIncrease2(increase2 / crossCount);
        stockCross.setIncrease3(increase3 / crossCount);
        stockCross.setIncrease4(increase4 / crossCount);
        stockCross.setIncrease5(increase5 / crossCount);
        stockCross.setCount(crossCount);
        iStockCrossDao.insert(stockCross);
    }
    @Override
    public String getCrossEffect(String stockCode) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        if (!iStockCrossDao.isExistByStockCodeAndDate(stockCode, stockDate)) {
            crossEffectInit(stockCode, stockDate);
        }
        //获取当前股票的影响
        StockCross stockCrosst = iStockCrossDao.getStockCrosstByStockCode(stockCode);

        if (!iStockCrossDao.isExistByStockCodeAndDate("000000", stockDate)) {
            crossEffectAllInit( stockDate);
        }
        StockCross stockCrosst_000000 = iStockCrossDao.getStockCrosstByStockCode("000000");

        Map<String,Object> resultMap =new HashMap<>();

        Map<String,Object> resultMapCross =new HashMap<>();
        Map<String,Object> resultMapCrossAll =new HashMap<>();
        resultMapCross.put("increase1",stockCrosst.getIncrease1());
        resultMapCross.put("increase2",stockCrosst.getIncrease2());
        resultMapCross.put("increase3",stockCrosst.getIncrease3());
        resultMapCross.put("increase4",stockCrosst.getIncrease4());
        resultMapCross.put("increase5",stockCrosst.getIncrease5());

        resultMapCrossAll.put("increase1",stockCrosst_000000.getIncrease1());
        resultMapCrossAll.put("increase2",stockCrosst_000000.getIncrease2());
        resultMapCrossAll.put("increase3",stockCrosst_000000.getIncrease3());
        resultMapCrossAll.put("increase4",stockCrosst_000000.getIncrease4());
        resultMapCrossAll.put("increase5",stockCrosst_000000.getIncrease5());
        resultMap.put("Cross",resultMapCross);
        resultMap.put("CrossAll",resultMapCrossAll);
        String jsonStr = JSON.toJSONString( resultMap );
        return jsonStr;
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
