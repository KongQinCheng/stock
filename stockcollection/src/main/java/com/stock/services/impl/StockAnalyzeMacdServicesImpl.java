package com.stock.services.impl;

import com.alibaba.fastjson.JSON;
import com.stock.bean.dto.AnalyzeCorssEffectDto;
import com.stock.bean.po.*;
import com.stock.dao.*;
import com.stock.services.IStockAnalyzeMacdServices;
import com.stock.services.IStockInfoMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.stock.services.impl.StockAnalyzeIncreaseServicesImpl.getStockIncreaseBase;

@Service
public class StockAnalyzeMacdServicesImpl implements IStockAnalyzeMacdServices {


    @Autowired
    IStockCrossDao iStockCrossDao;


    @Autowired
    IStockInfoDao iStockInfoDao;


    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;

    @Autowired
    IStockAnalyzeIncreaseDay2Dao iStockAnalyzeIncreaseDay2Dao;

    @Autowired
    IStockCrossDistributionDao iStockCrossDistributionDao;

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


            double count_increase02_1 = 0.0;
            double count_increase24_1 = 0.0;
            double count_increase46_1 = 0.0;
            double count_increase68_1 = 0.0;
            double count_increase810_1 = 0.0;
            double count_increase10_1 = 0.0;
            double count_descend02_1 = 0.0;
            double count_descend24_1 = 0.0;
            double count_descend46_1 = 0.0;
            double count_descend68_1 = 0.0;
            double count_descend810_1 = 0.0;
            double count_descend10_1 = 0.0;


            double count_increase02_2 = 0.0;
            double count_increase24_2 = 0.0;
            double count_increase46_2 = 0.0;
            double count_increase68_2 = 0.0;
            double count_increase810_2 = 0.0;
            double count_increase10_2 = 0.0;
            double count_descend02_2 = 0.0;
            double count_descend24_2 = 0.0;
            double count_descend46_2 = 0.0;
            double count_descend68_2 = 0.0;
            double count_descend810_2 = 0.0;
            double count_descend10_2 = 0.0;


            double count_increase02_3 = 0.0;
            double count_increase24_3 = 0.0;
            double count_increase46_3 = 0.0;
            double count_increase68_3 = 0.0;
            double count_increase810_3 = 0.0;
            double count_increase10_3 = 0.0;
            double count_descend02_3 = 0.0;
            double count_descend24_3 = 0.0;
            double count_descend46_3 = 0.0;
            double count_descend68_3 = 0.0;
            double count_descend810_3 = 0.0;
            double count_descend10_3 = 0.0;


            double count_increase02_4 = 0.0;
            double count_increase24_4 = 0.0;
            double count_increase46_4 = 0.0;
            double count_increase68_4 = 0.0;
            double count_increase810_4 = 0.0;
            double count_increase10_4 = 0.0;
            double count_descend02_4 = 0.0;
            double count_descend24_4 = 0.0;
            double count_descend46_4 = 0.0;
            double count_descend68_4 = 0.0;
            double count_descend810_4 = 0.0;
            double count_descend10_4 = 0.0;


            double count_increase02_5 = 0.0;
            double count_increase24_5 = 0.0;
            double count_increase46_5 = 0.0;
            double count_increase68_5 = 0.0;
            double count_increase810_5 = 0.0;
            double count_increase10_5 = 0.0;
            double count_descend02_5 = 0.0;
            double count_descend24_5 = 0.0;
            double count_descend46_5 = 0.0;
            double count_descend68_5 = 0.0;
            double count_descend810_5 = 0.0;
            double count_descend10_5 = 0.0;


            double beforeDIF = list.get(0).getDIF();
            double beforeDEA = list.get(0).getEMAMACD();
            double beforeMACD = list.get(0).getBAR();
            for (int i = 1; i < list.size(); i++) {
                Map<String, String> map = iStockInfoMacdServices.haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
                beforeDIF = list.get(i).getDIF();
                beforeDEA = list.get(i).getEMAMACD();

                if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来

                    double yestayZdf = list.get(i).getZdf();
                    //统计一共出现多少次金叉
                    crossCount += 1.0;
                    //判断涨幅情况
                    int tempindex = i;
                    if (tempindex + 1 < list.size()) {
                        if (list.get(tempindex + 1).getZdf() >= 0) {
                            increase1 += 1.0;

                            if (yestayZdf >= 10) {
                                count_increase10_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 8) {
                                count_increase810_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 6) {
                                count_increase68_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 4) {
                                count_increase46_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 2) {
                                count_increase24_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 0) {
                                count_increase02_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -2) {
                                count_descend02_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -4) {
                                count_descend24_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -6) {
                                count_descend46_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -8) {
                                count_descend68_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -10) {
                                count_descend810_1 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -20) {
                                count_descend10_1 += 1.0;
                                continue;
                            }
                        }
                    }
                    if (tempindex + 2 < list.size()) {
                        if (list.get(tempindex + 2).getZdf() >= 0) {
                            increase2 += 1.0;
                            if (yestayZdf >= 10) {
                                count_increase10_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 8) {
                                count_increase810_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 6) {
                                count_increase68_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 4) {
                                count_increase46_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 2) {
                                count_increase24_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 0) {
                                count_increase02_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -2) {
                                count_descend02_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -4) {
                                count_descend24_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -6) {
                                count_descend46_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -8) {
                                count_descend68_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -10) {
                                count_descend810_2 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -20) {
                                count_descend10_2 += 1.0;
                                continue;
                            }
                        }
                    }
                    if (tempindex + 3 < list.size()) {
                        if (list.get(tempindex + 3).getZdf() >= 0) {
                            increase3 += 1.0;
                            if (yestayZdf >= 10) {
                                count_increase10_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 8) {
                                count_increase810_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 6) {
                                count_increase68_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 4) {
                                count_increase46_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 2) {
                                count_increase24_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 0) {
                                count_increase02_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -2) {
                                count_descend02_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -4) {
                                count_descend24_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -6) {
                                count_descend46_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -8) {
                                count_descend68_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -10) {
                                count_descend810_3 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -20) {
                                count_descend10_3 += 1.0;
                                continue;
                            }
                        }
                    }
                    if (tempindex + 4 < list.size()) {
                        if (list.get(tempindex + 4).getZdf() >= 0) {
                            increase4 += 1.0;
                            if (yestayZdf >= 10) {
                                count_increase10_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 8) {
                                count_increase810_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 6) {
                                count_increase68_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 4) {
                                count_increase46_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 2) {
                                count_increase24_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 0) {
                                count_increase02_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -2) {
                                count_descend02_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -4) {
                                count_descend24_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -6) {
                                count_descend46_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -8) {
                                count_descend68_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -10) {
                                count_descend810_4 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -20) {
                                count_descend10_4 += 1.0;
                                continue;
                            }
                        }
                    }
                    if (tempindex + 5 < list.size()) {
                        if (list.get(tempindex + 5).getZdf() >= 0) {
                            increase5 += 1.0;
                            if (yestayZdf >= 10) {
                                count_increase10_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 8) {
                                count_increase810_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 6) {
                                count_increase68_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 4) {
                                count_increase46_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 2) {
                                count_increase24_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= 0) {
                                count_increase02_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -2) {
                                count_descend02_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -4) {
                                count_descend24_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -6) {
                                count_descend46_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -8) {
                                count_descend68_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -10) {
                                count_descend810_5 += 1.0;
                                continue;
                            }
                            if (yestayZdf >= -20) {
                                count_descend10_5 += 1.0;
                                continue;
                            }
                        }
                    }
                }
            }

            StockCross stockCross = new StockCross();
            stockCross.setStockCode(stockCode);
            stockCross.setStockDate(stockDate);
            if (crossCount != 0.0) {
                stockCross.setIncrease1(increase1 / crossCount);
                stockCross.setIncrease2(increase2 / crossCount);
                stockCross.setIncrease3(increase3 / crossCount);
                stockCross.setIncrease4(increase4 / crossCount);
                stockCross.setIncrease5(increase5 / crossCount);
            }
//-------1---------
            if (increase1 != 0.0) {
                stockCross.setIncrease102(count_increase02_1 / increase1);
                stockCross.setIncrease124(count_increase24_1 / increase1);
                stockCross.setIncrease146(count_increase46_1 / increase1);
                stockCross.setIncrease168(count_increase68_1 / increase1);
                stockCross.setIncrease1810(count_increase810_1 / increase1);
                stockCross.setIncrease110(count_increase10_1 / increase1);
                stockCross.setDescend102(count_descend02_1 / increase1);
                stockCross.setDescend124(count_descend24_1 / increase1);
                stockCross.setDescend146(count_descend46_1 / increase1);
                stockCross.setDescend168(count_descend68_1 / increase1);
                stockCross.setDescend1810(count_descend810_1 / increase1);
                stockCross.setDescend110(count_descend10_1 / increase1);
                stockCross.setCount1(increase1);
            }
//-------2---------
            if (increase2 != 0.0) {
                stockCross.setIncrease202(count_increase02_2 / increase2);
                stockCross.setIncrease224(count_increase24_2 / increase2);
                stockCross.setIncrease246(count_increase46_2 / increase2);
                stockCross.setIncrease268(count_increase68_2 / increase2);
                stockCross.setIncrease2810(count_increase810_2 / increase2);
                stockCross.setIncrease210(count_increase10_2 / increase2);
                stockCross.setDescend202(count_descend02_2 / increase2);
                stockCross.setDescend224(count_descend24_2 / increase2);
                stockCross.setDescend246(count_descend46_2 / increase2);
                stockCross.setDescend268(count_descend68_2 / increase2);
                stockCross.setDescend2810(count_descend810_2 / increase2);
                stockCross.setDescend210(count_descend10_2 / increase2);
                stockCross.setCount2(increase2);
            }
//--------3--------
            if (increase3 != 0.0) {
                stockCross.setIncrease302(count_increase02_3 / increase3);
                stockCross.setIncrease324(count_increase24_3 / increase3);
                stockCross.setIncrease346(count_increase46_3 / increase3);
                stockCross.setIncrease368(count_increase68_3 / increase3);
                stockCross.setIncrease3810(count_increase810_3 / increase3);
                stockCross.setIncrease310(count_increase10_3 / increase3);
                stockCross.setDescend302(count_descend02_3 / increase3);
                stockCross.setDescend324(count_descend24_3 / increase3);
                stockCross.setDescend346(count_descend46_3 / increase3);
                stockCross.setDescend368(count_descend68_3 / increase3);
                stockCross.setDescend3810(count_descend810_3 / increase3);
                stockCross.setDescend310(count_descend10_3 / increase3);
                stockCross.setCount3(increase3);
            }
//--------4--------
            if (increase4 != 0.0) {
                stockCross.setIncrease402(count_increase02_4 / increase4);
                stockCross.setIncrease424(count_increase24_4 / increase4);
                stockCross.setIncrease446(count_increase46_4 / increase4);
                stockCross.setIncrease468(count_increase68_4 / increase4);
                stockCross.setIncrease4810(count_increase810_4 / increase4);
                stockCross.setIncrease410(count_increase10_4 / increase4);
                stockCross.setDescend402(count_descend02_4 / increase4);
                stockCross.setDescend424(count_descend24_4 / increase4);
                stockCross.setDescend446(count_descend46_4 / increase4);
                stockCross.setDescend468(count_descend68_4 / increase4);
                stockCross.setDescend4810(count_descend810_4 / increase4);
                stockCross.setDescend410(count_descend10_4 / increase4);
                stockCross.setCount4(increase4);
            }
//--------5--------
            if (increase5 != 0.0) {
                stockCross.setIncrease502(count_increase02_5 / increase5);
                stockCross.setIncrease524(count_increase24_5 / increase5);
                stockCross.setIncrease546(count_increase46_5 / increase5);
                stockCross.setIncrease568(count_increase68_5 / increase5);
                stockCross.setIncrease5810(count_increase810_5 / increase5);
                stockCross.setIncrease510(count_increase10_5 / increase5);
                stockCross.setDescend502(count_descend02_5 / increase5);
                stockCross.setDescend524(count_descend24_5 / increase5);
                stockCross.setDescend546(count_descend46_5 / increase5);
                stockCross.setDescend568(count_descend68_5 / increase5);
                stockCross.setDescend5810(count_descend810_5 / increase5);
                stockCross.setDescend510(count_descend10_5 / increase5);
                stockCross.setCount5(increase5);
            }
            stockCross.setCount(crossCount);
            iStockCrossDao.insert(stockCross);

        }
        System.out.println("金叉影响 更新完毕stockCode=" + stockCode);
    }

    @Override
    public void crossEffectInitNew(String stockCode, String sss) {
        //删除历史数据
        iStockAnalyzeIncreaseDayDao.delByStockCode(stockCode, "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        List<StockInfo> list = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);

        if (list != null && list.size() > 0) {

            double beforeDIF = list.get(0).getDIF();
            double beforeDEA = list.get(0).getEMAMACD();
            double beforeMACD = list.get(0).getBAR();


            AnalyzeIncreaseDayMore increaseEffect0_1 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 1, 1);
            AnalyzeIncreaseDayMore increaseEffect0_2 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 2, 1);
            AnalyzeIncreaseDayMore increaseEffect0_3 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 3, 1);
            AnalyzeIncreaseDayMore increaseEffect0_4 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 4, 1);
            AnalyzeIncreaseDayMore increaseEffect0_5 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 5, 1);

            AnalyzeIncreaseDayMore increaseEffect1_1 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 1, 1);
            AnalyzeIncreaseDayMore increaseEffect1_2 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 2, 1);
            AnalyzeIncreaseDayMore increaseEffect1_3 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 3, 1);
            AnalyzeIncreaseDayMore increaseEffect1_4 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 4, 1);
            AnalyzeIncreaseDayMore increaseEffect1_5 = new AnalyzeIncreaseDayMore(stockCode, stockDate, 5, 1);


            for (int i = 1; i < list.size(); i++) {
                Map<String, String> map = iStockInfoMacdServices.haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
                beforeDIF = list.get(i).getDIF();
                beforeDEA = list.get(i).getEMAMACD();

                if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来

                    increaseEffect0_1 = getIncreaseEffect(1, 0, list, i, increaseEffect0_1);
                    increaseEffect0_2 = getIncreaseEffect(2, 0, list, i, increaseEffect0_2);
                    increaseEffect0_3 = getIncreaseEffect(3, 0, list, i, increaseEffect0_3);
                    increaseEffect0_4 = getIncreaseEffect(4, 0, list, i, increaseEffect0_4);
                    increaseEffect0_5 = getIncreaseEffect(5, 0, list, i, increaseEffect0_5);

                    increaseEffect1_1 = getIncreaseEffect(1, 1, list, i, increaseEffect1_1);
                    increaseEffect1_2 = getIncreaseEffect(2, 1, list, i, increaseEffect1_2);
                    increaseEffect1_3 = getIncreaseEffect(3, 1, list, i, increaseEffect1_3);
                    increaseEffect1_4 = getIncreaseEffect(4, 1, list, i, increaseEffect1_4);
                    increaseEffect1_5 = getIncreaseEffect(5, 1, list, i, increaseEffect1_5);

                }
            }
            insertAnalyzeIncreaseDayMore(increaseEffect0_1);
            insertAnalyzeIncreaseDayMore(increaseEffect0_2);
            insertAnalyzeIncreaseDayMore(increaseEffect0_3);
            insertAnalyzeIncreaseDayMore(increaseEffect0_4);
            insertAnalyzeIncreaseDayMore(increaseEffect0_5);
            insertAnalyzeIncreaseDayMore(increaseEffect1_1);
            insertAnalyzeIncreaseDayMore(increaseEffect1_2);
            insertAnalyzeIncreaseDayMore(increaseEffect1_3);
            insertAnalyzeIncreaseDayMore(increaseEffect1_4);
            insertAnalyzeIncreaseDayMore(increaseEffect1_5);
        }
        System.out.println("金叉影响 更新完毕stockCode=" + stockCode);
    }


    public void crossEffectInitAllNew(String stockCode, String stockDate, int effectType) {

        iStockAnalyzeIncreaseDayDao.delByStockCode(stockCode, "1");


        AnalyzeIncreaseDay analyzeIncreaseDay = new AnalyzeIncreaseDay();
        analyzeIncreaseDay.setStockCode("");
        analyzeIncreaseDay.setStockDate(stockDate);


        for (int i = 1; i < 6; i++) {
            analyzeIncreaseDay.setIndexDay(i);
            analyzeIncreaseDay.setEffectType(effectType);
            analyzeIncreaseDay.setLevel(0);
            analyzeIncreaseDay.setIncreaseType(1);
            List<AnalyzeIncreaseDay> entryByStockCodeAll = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

            analyzeIncreaseDay.setLevel(1);
            List<AnalyzeIncreaseDay> entryByStockCodePart = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

            AnalyzeIncreaseDayMore increaseEffect = new AnalyzeIncreaseDayMore(stockCode, stockDate, 1, 1);

            increaseEffect.getAnalyzeIncreaseDayAll().setIndexDay(i);
            increaseEffect.getAnalyzeIncreaseDayAll().setEffectType(effectType);

            increaseEffect.getAnalyzeIncreaseDayPart().setIndexDay(i);
            increaseEffect.getAnalyzeIncreaseDayPart().setEffectType(effectType);

            for (int j = 0; j < entryByStockCodeAll.size(); j++) {
                increaseEffect.setAnalyzeIncreaseDayAll(getStockIncreaseBase(entryByStockCodeAll.get(j), increaseEffect.getAnalyzeIncreaseDayAll()));
                increaseEffect.setAnalyzeIncreaseDayPart(getStockIncreaseBase(entryByStockCodePart.get(j), increaseEffect.getAnalyzeIncreaseDayPart()));
            }
            insertAnalyzeIncreaseDayMore(increaseEffect);

        }

    }

    @Autowired
    IStockAnalyzeIncreaseDayDao iStockAnalyzeIncreaseDayDao;

    public void insertAnalyzeIncreaseDayMore(AnalyzeIncreaseDayMore analyzeIncreaseDayMore) {
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDayMore.getAnalyzeIncreaseDayAll());
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart());
    }

    public void insertAnalyzeIncreaseDayMore(AnalyzeIncreaseDayMore analyzeIncreaseDayMore, String crossType) {
        analyzeIncreaseDayMore.getAnalyzeIncreaseDayAll().setCrossType(crossType);
        analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart().setCrossType(crossType);
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDayMore.getAnalyzeIncreaseDayAll());
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart());
    }

    /***
     * 上涨的概率
     * @param dayNum 天数  n：交叉后n天的数据
     * @param type  统计类型   0:当天的数据. 1:总n 天的涨幅情况。
     */
    public static AnalyzeIncreaseDayMore getIncreaseEffect(int dayNum, int type, List<StockInfo> list, int i, AnalyzeIncreaseDayMore analyzeIncreaseDayMore) {

        if (type == 0) {
            analyzeIncreaseDayMore.getAnalyzeIncreaseDayAll().setEffectType(0);
            analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart().setEffectType(0);
        }
        if (type == 1) {
            analyzeIncreaseDayMore.getAnalyzeIncreaseDayAll().setEffectType(1);
            analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart().setEffectType(1);
        }

        //统计出现 交叉 的时候 的 涨跌幅区间
        analyzeIncreaseDayMore.setAnalyzeIncreaseDayAll(getStockIncreaseBase(list.get(i), analyzeIncreaseDayMore.getAnalyzeIncreaseDayAll()));

        if (i + dayNum < list.size()) {
            if (type == 0) {
                // 1. 后一天上涨
                if ((list.get(i + dayNum).getZdf() > 0)) {
                    //如果下一天涨幅超过0 次数加一
                    analyzeIncreaseDayMore.setAnalyzeIncreaseDayPart(getStockIncreaseBase(list.get(i), analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart()));
                }
            }
            if (type == 1) {
                // 1. 后几天最终上涨的
                if ((list.get(i + dayNum).getSpj() - list.get(i).getSpj() > 0)) {
                    //如果下一天涨幅超过0 次数加一
                    analyzeIncreaseDayMore.setAnalyzeIncreaseDayPart(getStockIncreaseBase(list.get(i), analyzeIncreaseDayMore.getAnalyzeIncreaseDayPart()));
                }
            }
        }

        return analyzeIncreaseDayMore;
    }

    /***
     * 判断各种交叉 出现之后 后面是否一定会上涨
     *
     * @param list
     * @param i
     * @param analyzeIncreaseDay2
     * @return
     */
    public static AnalyzeIncreaseDay2 getIncreaseEffectFinal(List<StockInfo> list, int i, AnalyzeIncreaseDay2 analyzeIncreaseDay2) {

        analyzeIncreaseDay2.setCount(analyzeIncreaseDay2.getCount() + 1.0);

        if (i + 1 < list.size() && (list.get(i + 1).getSpj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay1(analyzeIncreaseDay2.getDay1() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 2 < list.size() && (list.get(i + 2).getSpj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay2(analyzeIncreaseDay2.getDay2() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 3 < list.size() && (list.get(i + 3).getSpj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay3(analyzeIncreaseDay2.getDay3() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 4 < list.size() && (list.get(i + 4).getSpj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay4(analyzeIncreaseDay2.getDay4() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 5 < list.size() && (list.get(i + 5).getSpj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay5(analyzeIncreaseDay2.getDay5() + 1.0);
            return analyzeIncreaseDay2;
        }
        return analyzeIncreaseDay2;
    }


    /***
     * 判断各种交叉 出现之后 后面是否一定会上涨
     *
     * @param list
     * @param i
     * @param analyzeIncreaseDay2
     * @return
     */
    public static AnalyzeIncreaseDay2 getIncreaseEffectMaxValue(List<StockInfo> list, int i, AnalyzeIncreaseDay2 analyzeIncreaseDay2) {

        analyzeIncreaseDay2.setCount(analyzeIncreaseDay2.getCount() + 1.0);

        if (i + 1 < list.size() && (list.get(i + 1).getZgj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay1(analyzeIncreaseDay2.getDay1() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 2 < list.size() && (list.get(i + 2).getZgj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay2(analyzeIncreaseDay2.getDay2() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 3 < list.size() && (list.get(i + 3).getZgj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay3(analyzeIncreaseDay2.getDay3() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 4 < list.size() && (list.get(i + 4).getZgj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay4(analyzeIncreaseDay2.getDay4() + 1.0);
            return analyzeIncreaseDay2;
        }
        if (i + 5 < list.size() && (list.get(i + 5).getZgj() - list.get(i).getSpj() > 0)) {
            analyzeIncreaseDay2.setDay5(analyzeIncreaseDay2.getDay5() + 1.0);
            return analyzeIncreaseDay2;
        }
        return analyzeIncreaseDay2;
    }

    /***
     *
     * @param list
     * @param i
     * @return
     */
    public static StockCrossDistribution getIncreaseEffectMaxValueDistribution(List<StockInfo> list, int i, StockCrossDistribution stockCrossDistribution) {


        stockCrossDistribution.setCount(stockCrossDistribution.getCount()+1.0);
        if (i + 1 < list.size() && (list.get(i + 1).getZgj() - list.get(i).getSpj() > 0)) {
            stockCrossDistribution = getDistribution(list.get(i + 1).getZgj(), list.get(i).getSpj(), stockCrossDistribution);
        }
//        if (i + 2 < list.size() && (list.get(i + 2).getZgj() - list.get(i).getSpj() > 0)) {
//            distribuitonList[1] = getDistribution(list.get(i + 2).getZgj(), list.get(i).getSpj(), distribuitonList[1]);
//        }
//        if (i + 3 < list.size() && (list.get(i + 3).getZgj() - list.get(i).getSpj() > 0)) {
//            distribuitonList[2] = getDistribution(list.get(i + 3).getZgj(), list.get(i).getSpj(), distribuitonList[2]);
//        }
//        if (i + 4 < list.size() && (list.get(i + 4).getZgj() - list.get(i).getSpj() > 0)) {
//            distribuitonList[3] = getDistribution(list.get(i + 4).getZgj(), list.get(i).getSpj(), distribuitonList[3]);
//        }
//        if (i + 5 < list.size() && (list.get(i + 5).getZgj() - list.get(i).getSpj() > 0)) {
//            distribuitonList[4] = getDistribution(list.get(i + 5).getZgj(), list.get(i).getSpj(), distribuitonList[4]);
//        }
        return stockCrossDistribution;
    }

    public static StockCrossDistribution getDistribution(double zgj, double spj, StockCrossDistribution stockCrossDistribution) {


        if ((zgj - spj) / spj >= 0.1) {
            stockCrossDistribution.setIncrease10(stockCrossDistribution.getIncrease10() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.09) {
            stockCrossDistribution.setIncrease9(stockCrossDistribution.getIncrease9() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.08) {
            stockCrossDistribution.setIncrease8(stockCrossDistribution.getIncrease8() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.07) {
            stockCrossDistribution.setIncrease7(stockCrossDistribution.getIncrease7() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.06) {
            stockCrossDistribution.setIncrease6(stockCrossDistribution.getIncrease6() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.05) {
            stockCrossDistribution.setIncrease5(stockCrossDistribution.getIncrease5() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.04) {
            stockCrossDistribution.setIncrease4(stockCrossDistribution.getIncrease4() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.03) {
            stockCrossDistribution.setIncrease3(stockCrossDistribution.getIncrease3() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.02) {
            stockCrossDistribution.setIncrease2(stockCrossDistribution.getIncrease2() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0.01) {
            stockCrossDistribution.setIncrease1(stockCrossDistribution.getIncrease1() + 1.0);
            return stockCrossDistribution;
        }
        if ((zgj - spj) / spj >= 0) {
            stockCrossDistribution.setIncrease0(stockCrossDistribution.getIncrease0() + 1.0);
            return stockCrossDistribution;
        }
        return stockCrossDistribution;
    }


    public static AnalyzeIncreaseDay2 getIncreaseEffectFinalDay(AnalyzeIncreaseDay2 analyzeIncreaseInsert, AnalyzeIncreaseDay2 analyzeIncreaseReturn) {
        analyzeIncreaseReturn.setDay1(analyzeIncreaseReturn.getDay1() + analyzeIncreaseInsert.getDay1());
        analyzeIncreaseReturn.setDay2(analyzeIncreaseReturn.getDay2() + analyzeIncreaseInsert.getDay2());
        analyzeIncreaseReturn.setDay3(analyzeIncreaseReturn.getDay3() + analyzeIncreaseInsert.getDay3());
        analyzeIncreaseReturn.setDay4(analyzeIncreaseReturn.getDay4() + analyzeIncreaseInsert.getDay4());
        analyzeIncreaseReturn.setDay5(analyzeIncreaseReturn.getDay5() + analyzeIncreaseInsert.getDay5());
        analyzeIncreaseReturn.setCount(analyzeIncreaseReturn.getCount() + analyzeIncreaseInsert.getCount());
        return analyzeIncreaseReturn;
    }

    public static StockCrossDistribution getIncreaseEffectFinalDayMaxvalue(StockCrossDistribution stockCrossDistribution, StockCrossDistribution stockCrossDistributionReturn) {
        stockCrossDistributionReturn.setCount(stockCrossDistributionReturn.getCount()+stockCrossDistribution.getCount());
        stockCrossDistributionReturn.setIncrease10(stockCrossDistributionReturn.getIncrease10()+stockCrossDistribution.getIncrease10());
        stockCrossDistributionReturn.setIncrease9(stockCrossDistributionReturn.getIncrease9()+stockCrossDistribution.getIncrease9());
        stockCrossDistributionReturn.setIncrease8(stockCrossDistributionReturn.getIncrease8()+stockCrossDistribution.getIncrease8());
        stockCrossDistributionReturn.setIncrease7(stockCrossDistributionReturn.getIncrease7()+stockCrossDistribution.getIncrease7());
        stockCrossDistributionReturn.setIncrease6(stockCrossDistributionReturn.getIncrease6()+stockCrossDistribution.getIncrease6());
        stockCrossDistributionReturn.setIncrease5(stockCrossDistributionReturn.getIncrease5()+stockCrossDistribution.getIncrease5());
        stockCrossDistributionReturn.setIncrease4(stockCrossDistributionReturn.getIncrease4()+stockCrossDistribution.getIncrease4());
        stockCrossDistributionReturn.setIncrease3(stockCrossDistributionReturn.getIncrease3()+stockCrossDistribution.getIncrease3());
        stockCrossDistributionReturn.setIncrease2(stockCrossDistributionReturn.getIncrease2()+stockCrossDistribution.getIncrease2());
        stockCrossDistributionReturn.setIncrease1(stockCrossDistributionReturn.getIncrease1()+stockCrossDistribution.getIncrease1());
        stockCrossDistributionReturn.setIncrease0(stockCrossDistributionReturn.getIncrease0()+stockCrossDistribution.getIncrease0());
        return stockCrossDistributionReturn;
    }

    @Override
    public void crossEffectAllInit(String type) {

        double increase1 = 0.0;
        double increase2 = 0.0;
        double increase3 = 0.0;
        double increase4 = 0.0;
        double increase5 = 0.0;
        double crossCount = 0.0;


        double count_increase02_1 = 0.0;
        double count_increase24_1 = 0.0;
        double count_increase46_1 = 0.0;
        double count_increase68_1 = 0.0;
        double count_increase810_1 = 0.0;
        double count_increase10_1 = 0.0;
        double count_descend02_1 = 0.0;
        double count_descend24_1 = 0.0;
        double count_descend46_1 = 0.0;
        double count_descend68_1 = 0.0;
        double count_descend810_1 = 0.0;
        double count_descend10_1 = 0.0;


        double count_increase02_2 = 0.0;
        double count_increase24_2 = 0.0;
        double count_increase46_2 = 0.0;
        double count_increase68_2 = 0.0;
        double count_increase810_2 = 0.0;
        double count_increase10_2 = 0.0;
        double count_descend02_2 = 0.0;
        double count_descend24_2 = 0.0;
        double count_descend46_2 = 0.0;
        double count_descend68_2 = 0.0;
        double count_descend810_2 = 0.0;
        double count_descend10_2 = 0.0;


        double count_increase02_3 = 0.0;
        double count_increase24_3 = 0.0;
        double count_increase46_3 = 0.0;
        double count_increase68_3 = 0.0;
        double count_increase810_3 = 0.0;
        double count_increase10_3 = 0.0;
        double count_descend02_3 = 0.0;
        double count_descend24_3 = 0.0;
        double count_descend46_3 = 0.0;
        double count_descend68_3 = 0.0;
        double count_descend810_3 = 0.0;
        double count_descend10_3 = 0.0;


        double count_increase02_4 = 0.0;
        double count_increase24_4 = 0.0;
        double count_increase46_4 = 0.0;
        double count_increase68_4 = 0.0;
        double count_increase810_4 = 0.0;
        double count_increase10_4 = 0.0;
        double count_descend02_4 = 0.0;
        double count_descend24_4 = 0.0;
        double count_descend46_4 = 0.0;
        double count_descend68_4 = 0.0;
        double count_descend810_4 = 0.0;
        double count_descend10_4 = 0.0;


        double count_increase02_5 = 0.0;
        double count_increase24_5 = 0.0;
        double count_increase46_5 = 0.0;
        double count_increase68_5 = 0.0;
        double count_increase810_5 = 0.0;
        double count_increase10_5 = 0.0;
        double count_descend02_5 = 0.0;
        double count_descend24_5 = 0.0;
        double count_descend46_5 = 0.0;
        double count_descend68_5 = 0.0;
        double count_descend810_5 = 0.0;
        double count_descend10_5 = 0.0;


        double count_1 = 0.0;
        double count_2 = 0.0;
        double count_3 = 0.0;
        double count_4 = 0.0;
        double count_5 = 0.0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        iStockCrossDao.delete("000000");
        List<StockCross> stockCrossList = iStockCrossDao.getStockCrossList();
        for (int i = 0; i < stockCrossList.size(); i++) {
            increase1 += stockCrossList.get(i).getIncrease1() * stockCrossList.get(i).getCount();
            increase2 += stockCrossList.get(i).getIncrease2() * stockCrossList.get(i).getCount();
            increase3 += stockCrossList.get(i).getIncrease3() * stockCrossList.get(i).getCount();
            increase4 += stockCrossList.get(i).getIncrease4() * stockCrossList.get(i).getCount();
            increase5 += stockCrossList.get(i).getIncrease5() * stockCrossList.get(i).getCount();
//--------1-----
            count_increase02_1 += stockCrossList.get(i).getIncrease102() * stockCrossList.get(i).getCount1();
            count_increase24_1 += stockCrossList.get(i).getIncrease124() * stockCrossList.get(i).getCount1();
            count_increase46_1 += stockCrossList.get(i).getIncrease146() * stockCrossList.get(i).getCount1();
            count_increase68_1 += stockCrossList.get(i).getIncrease168() * stockCrossList.get(i).getCount1();
            count_increase810_1 += stockCrossList.get(i).getIncrease1810() * stockCrossList.get(i).getCount1();
            count_increase10_1 += stockCrossList.get(i).getIncrease110() * stockCrossList.get(i).getCount1();
            count_descend02_1 += stockCrossList.get(i).getDescend102() * stockCrossList.get(i).getCount1();
            count_descend24_1 += stockCrossList.get(i).getDescend124() * stockCrossList.get(i).getCount1();
            count_descend46_1 += stockCrossList.get(i).getDescend146() * stockCrossList.get(i).getCount1();
            count_descend68_1 += stockCrossList.get(i).getDescend168() * stockCrossList.get(i).getCount1();
            count_descend810_1 += stockCrossList.get(i).getDescend1810() * stockCrossList.get(i).getCount1();
            count_descend10_1 += stockCrossList.get(i).getDescend110() * stockCrossList.get(i).getCount1();
            count_1 += stockCrossList.get(i).getCount1();


            count_increase02_2 += stockCrossList.get(i).getIncrease202() * stockCrossList.get(i).getCount2();
            count_increase24_2 += stockCrossList.get(i).getIncrease224() * stockCrossList.get(i).getCount2();
            count_increase46_2 += stockCrossList.get(i).getIncrease246() * stockCrossList.get(i).getCount2();
            count_increase68_2 += stockCrossList.get(i).getIncrease268() * stockCrossList.get(i).getCount2();
            count_increase810_2 += stockCrossList.get(i).getIncrease2810() * stockCrossList.get(i).getCount2();
            count_increase10_2 += stockCrossList.get(i).getIncrease210() * stockCrossList.get(i).getCount2();
            count_descend02_2 += stockCrossList.get(i).getDescend202() * stockCrossList.get(i).getCount2();
            count_descend24_2 += stockCrossList.get(i).getDescend224() * stockCrossList.get(i).getCount2();
            count_descend46_2 += stockCrossList.get(i).getDescend246() * stockCrossList.get(i).getCount2();
            count_descend68_2 += stockCrossList.get(i).getDescend268() * stockCrossList.get(i).getCount2();
            count_descend810_2 += stockCrossList.get(i).getDescend2810() * stockCrossList.get(i).getCount2();
            count_descend10_2 += stockCrossList.get(i).getDescend210() * stockCrossList.get(i).getCount2();
            count_2 += stockCrossList.get(i).getCount2();

            count_increase02_3 += stockCrossList.get(i).getIncrease302() * stockCrossList.get(i).getCount3();
            count_increase24_3 += stockCrossList.get(i).getIncrease324() * stockCrossList.get(i).getCount3();
            count_increase46_3 += stockCrossList.get(i).getIncrease346() * stockCrossList.get(i).getCount3();
            count_increase68_3 += stockCrossList.get(i).getIncrease368() * stockCrossList.get(i).getCount3();
            count_increase810_3 += stockCrossList.get(i).getIncrease3810() * stockCrossList.get(i).getCount3();
            count_increase10_3 += stockCrossList.get(i).getIncrease310() * stockCrossList.get(i).getCount3();
            count_descend02_3 += stockCrossList.get(i).getDescend302() * stockCrossList.get(i).getCount3();
            count_descend24_3 += stockCrossList.get(i).getDescend324() * stockCrossList.get(i).getCount3();
            count_descend46_3 += stockCrossList.get(i).getDescend346() * stockCrossList.get(i).getCount3();
            count_descend68_3 += stockCrossList.get(i).getDescend368() * stockCrossList.get(i).getCount3();
            count_descend810_3 += stockCrossList.get(i).getDescend3810() * stockCrossList.get(i).getCount3();
            count_descend10_3 += stockCrossList.get(i).getDescend310() * stockCrossList.get(i).getCount3();
            count_3 += stockCrossList.get(i).getCount3();


            count_increase02_4 += stockCrossList.get(i).getIncrease402() * stockCrossList.get(i).getCount4();
            count_increase24_4 += stockCrossList.get(i).getIncrease424() * stockCrossList.get(i).getCount4();
            count_increase46_4 += stockCrossList.get(i).getIncrease446() * stockCrossList.get(i).getCount4();
            count_increase68_4 += stockCrossList.get(i).getIncrease468() * stockCrossList.get(i).getCount4();
            count_increase810_4 += stockCrossList.get(i).getIncrease4810() * stockCrossList.get(i).getCount4();
            count_increase10_4 += stockCrossList.get(i).getIncrease410() * stockCrossList.get(i).getCount4();
            count_descend02_4 += stockCrossList.get(i).getDescend402() * stockCrossList.get(i).getCount4();
            count_descend24_4 += stockCrossList.get(i).getDescend424() * stockCrossList.get(i).getCount4();
            count_descend46_4 += stockCrossList.get(i).getDescend446() * stockCrossList.get(i).getCount4();
            count_descend68_4 += stockCrossList.get(i).getDescend468() * stockCrossList.get(i).getCount4();
            count_descend810_4 += stockCrossList.get(i).getDescend4810() * stockCrossList.get(i).getCount4();
            count_descend10_4 += stockCrossList.get(i).getDescend410() * stockCrossList.get(i).getCount4();
            count_4 += stockCrossList.get(i).getCount4();

            count_increase02_5 += stockCrossList.get(i).getIncrease502() * stockCrossList.get(i).getCount5();
            count_increase24_5 += stockCrossList.get(i).getIncrease524() * stockCrossList.get(i).getCount5();
            count_increase46_5 += stockCrossList.get(i).getIncrease546() * stockCrossList.get(i).getCount5();
            count_increase68_5 += stockCrossList.get(i).getIncrease568() * stockCrossList.get(i).getCount5();
            count_increase810_5 += stockCrossList.get(i).getIncrease5810() * stockCrossList.get(i).getCount5();
            count_increase10_5 += stockCrossList.get(i).getIncrease510() * stockCrossList.get(i).getCount5();
            count_descend02_5 += stockCrossList.get(i).getDescend502() * stockCrossList.get(i).getCount5();
            count_descend24_5 += stockCrossList.get(i).getDescend524() * stockCrossList.get(i).getCount5();
            count_descend46_5 += stockCrossList.get(i).getDescend546() * stockCrossList.get(i).getCount5();
            count_descend68_5 += stockCrossList.get(i).getDescend568() * stockCrossList.get(i).getCount5();
            count_descend810_5 += stockCrossList.get(i).getDescend5810() * stockCrossList.get(i).getCount5();
            count_descend10_5 += stockCrossList.get(i).getDescend510() * stockCrossList.get(i).getCount5();
            count_5 += stockCrossList.get(i).getCount5();

            crossCount += stockCrossList.get(i).getCount();
        }
        StockCross stockCross = new StockCross();
        stockCross.setStockCode("000000");
        stockCross.setStockDate(stockDate);
        stockCross.setIncrease1(increase1 / crossCount);
        stockCross.setIncrease2(increase2 / crossCount);
        stockCross.setIncrease3(increase3 / crossCount);
        stockCross.setIncrease4(increase4 / crossCount);
        stockCross.setIncrease5(increase5 / crossCount);


        //-------1---------
        stockCross.setIncrease102(count_increase02_1 / count_1);
        stockCross.setIncrease124(count_increase24_1 / count_1);
        stockCross.setIncrease146(count_increase46_1 / count_1);
        stockCross.setIncrease168(count_increase68_1 / count_1);
        stockCross.setIncrease1810(count_increase810_1 / count_1);
        stockCross.setIncrease110(count_increase10_1 / count_1);
        stockCross.setDescend102(count_descend02_1 / count_1);
        stockCross.setDescend124(count_descend24_1 / count_1);
        stockCross.setDescend146(count_descend46_1 / count_1);
        stockCross.setDescend168(count_descend68_1 / count_1);
        stockCross.setDescend1810(count_descend810_1 / count_1);
        stockCross.setDescend110(count_descend10_1 / count_1);
        stockCross.setCount1(count_1);


        stockCross.setIncrease202(count_increase02_2 / count_2);
        stockCross.setIncrease224(count_increase24_2 / count_2);
        stockCross.setIncrease246(count_increase46_2 / count_2);
        stockCross.setIncrease268(count_increase68_2 / count_2);
        stockCross.setIncrease2810(count_increase810_2 / count_2);
        stockCross.setIncrease210(count_increase10_2 / count_2);
        stockCross.setDescend202(count_descend02_2 / count_2);
        stockCross.setDescend224(count_descend24_2 / count_2);
        stockCross.setDescend246(count_descend46_2 / count_2);
        stockCross.setDescend268(count_descend68_2 / count_2);
        stockCross.setDescend2810(count_descend810_2 / count_2);
        stockCross.setDescend210(count_descend10_2 / count_2);
        stockCross.setCount2(count_2);

        stockCross.setIncrease302(count_increase02_3 / count_3);
        stockCross.setIncrease324(count_increase24_3 / count_3);
        stockCross.setIncrease346(count_increase46_3 / count_3);
        stockCross.setIncrease368(count_increase68_3 / count_3);
        stockCross.setIncrease3810(count_increase810_3 / count_3);
        stockCross.setIncrease310(count_increase10_3 / count_3);
        stockCross.setDescend302(count_descend02_3 / count_3);
        stockCross.setDescend324(count_descend24_3 / count_3);
        stockCross.setDescend346(count_descend46_3 / count_3);
        stockCross.setDescend368(count_descend68_3 / count_3);
        stockCross.setDescend3810(count_descend810_3 / count_3);
        stockCross.setDescend310(count_descend10_3 / count_3);
        stockCross.setCount3(count_3);

        stockCross.setIncrease402(count_increase02_4 / count_4);
        stockCross.setIncrease424(count_increase24_4 / count_4);
        stockCross.setIncrease446(count_increase46_4 / count_4);
        stockCross.setIncrease468(count_increase68_4 / count_4);
        stockCross.setIncrease4810(count_increase810_4 / count_4);
        stockCross.setIncrease410(count_increase10_4 / count_4);
        stockCross.setDescend402(count_descend02_4 / count_4);
        stockCross.setDescend424(count_descend24_4 / count_4);
        stockCross.setDescend446(count_descend46_4 / count_4);
        stockCross.setDescend468(count_descend68_4 / count_4);
        stockCross.setDescend4810(count_descend810_4 / count_4);
        stockCross.setDescend410(count_descend10_4 / count_4);
        stockCross.setCount4(count_4);

        stockCross.setIncrease502(count_increase02_5 / count_5);
        stockCross.setIncrease524(count_increase24_5 / count_5);
        stockCross.setIncrease546(count_increase46_5 / count_5);
        stockCross.setIncrease568(count_increase68_5 / count_5);
        stockCross.setIncrease5810(count_increase810_5 / count_5);
        stockCross.setIncrease510(count_increase10_5 / count_5);
        stockCross.setDescend502(count_descend02_5 / count_5);
        stockCross.setDescend524(count_descend24_5 / count_5);
        stockCross.setDescend546(count_descend46_5 / count_5);
        stockCross.setDescend568(count_descend68_5 / count_5);
        stockCross.setDescend5810(count_descend810_5 / count_5);
        stockCross.setDescend510(count_descend10_5 / count_5);
        stockCross.setCount5(count_5);

        stockCross.setCount(crossCount);
        iStockCrossDao.insert(stockCross);
    }


    @Override
    public Map<String, Object> getCrossEffect(String stockCode) {

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
            crossEffectAllInit(stockDate);
        }
        StockCross stockCrosst_000000 = iStockCrossDao.getStockCrosstByStockCode("000000");

        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> resultMapCross = new HashMap<>();
        Map<String, Object> resultMapCrossAll = new HashMap<>();
        resultMapCross.put("increase1", stockCrosst.getIncrease1());
        resultMapCross.put("increase2", stockCrosst.getIncrease2());
        resultMapCross.put("increase3", stockCrosst.getIncrease3());
        resultMapCross.put("increase4", stockCrosst.getIncrease4());
        resultMapCross.put("increase5", stockCrosst.getIncrease5());

        resultMapCross.put("increase102", stockCrosst.getIncrease102());
        resultMapCross.put("increase124", stockCrosst.getIncrease124());
        resultMapCross.put("increase146", stockCrosst.getIncrease146());
        resultMapCross.put("increase168", stockCrosst.getIncrease168());
        resultMapCross.put("increase1810", stockCrosst.getIncrease1810());
        resultMapCross.put("increase110", stockCrosst.getIncrease110());

        resultMapCross.put("increase202", stockCrosst.getIncrease202());
        resultMapCross.put("increase224", stockCrosst.getIncrease224());
        resultMapCross.put("increase246", stockCrosst.getIncrease246());
        resultMapCross.put("increase268", stockCrosst.getIncrease268());
        resultMapCross.put("increase2810", stockCrosst.getIncrease2810());
        resultMapCross.put("increase210", stockCrosst.getIncrease210());

        resultMapCross.put("increase302", stockCrosst.getIncrease302());
        resultMapCross.put("increase324", stockCrosst.getIncrease324());
        resultMapCross.put("increase346", stockCrosst.getIncrease346());
        resultMapCross.put("increase368", stockCrosst.getIncrease368());
        resultMapCross.put("increase3810", stockCrosst.getIncrease3810());
        resultMapCross.put("increase310", stockCrosst.getIncrease310());

        resultMapCross.put("increase402", stockCrosst.getIncrease402());
        resultMapCross.put("increase424", stockCrosst.getIncrease424());
        resultMapCross.put("increase446", stockCrosst.getIncrease446());
        resultMapCross.put("increase468", stockCrosst.getIncrease468());
        resultMapCross.put("increase4810", stockCrosst.getIncrease4810());
        resultMapCross.put("increase410", stockCrosst.getIncrease410());

        resultMapCross.put("increase502", stockCrosst.getIncrease502());
        resultMapCross.put("increase524", stockCrosst.getIncrease524());
        resultMapCross.put("increase546", stockCrosst.getIncrease546());
        resultMapCross.put("increase568", stockCrosst.getIncrease568());
        resultMapCross.put("increase5810", stockCrosst.getIncrease5810());
        resultMapCross.put("increase510", stockCrosst.getIncrease510());


        resultMapCross.put("descend102", stockCrosst.getDescend102());
        resultMapCross.put("descend124", stockCrosst.getDescend124());
        resultMapCross.put("descend146", stockCrosst.getDescend146());
        resultMapCross.put("descend168", stockCrosst.getDescend168());
        resultMapCross.put("descend1810", stockCrosst.getDescend1810());
        resultMapCross.put("descend110", stockCrosst.getDescend110());

        resultMapCross.put("descend202", stockCrosst.getDescend202());
        resultMapCross.put("descend224", stockCrosst.getDescend224());
        resultMapCross.put("descend246", stockCrosst.getDescend246());
        resultMapCross.put("descend268", stockCrosst.getDescend268());
        resultMapCross.put("descend2810", stockCrosst.getDescend2810());
        resultMapCross.put("descend210", stockCrosst.getDescend210());

        resultMapCross.put("descend302", stockCrosst.getDescend302());
        resultMapCross.put("descend324", stockCrosst.getDescend324());
        resultMapCross.put("descend346", stockCrosst.getDescend346());
        resultMapCross.put("descend368", stockCrosst.getDescend368());
        resultMapCross.put("descend3810", stockCrosst.getDescend3810());
        resultMapCross.put("descend310", stockCrosst.getDescend310());

        resultMapCross.put("descend402", stockCrosst.getDescend402());
        resultMapCross.put("descend424", stockCrosst.getDescend424());
        resultMapCross.put("descend446", stockCrosst.getDescend446());
        resultMapCross.put("descend468", stockCrosst.getDescend468());
        resultMapCross.put("descend4810", stockCrosst.getDescend4810());
        resultMapCross.put("descend410", stockCrosst.getDescend410());

        resultMapCross.put("descend502", stockCrosst.getDescend502());
        resultMapCross.put("descend524", stockCrosst.getDescend524());
        resultMapCross.put("descend546", stockCrosst.getDescend546());
        resultMapCross.put("descend568", stockCrosst.getDescend568());
        resultMapCross.put("descend5810", stockCrosst.getDescend5810());
        resultMapCross.put("descend510", stockCrosst.getDescend510());


        resultMapCrossAll.put("increase1", stockCrosst_000000.getIncrease1());
        resultMapCrossAll.put("increase2", stockCrosst_000000.getIncrease2());
        resultMapCrossAll.put("increase3", stockCrosst_000000.getIncrease3());
        resultMapCrossAll.put("increase4", stockCrosst_000000.getIncrease4());
        resultMapCrossAll.put("increase5", stockCrosst_000000.getIncrease5());


        resultMapCrossAll.put("increase102", stockCrosst_000000.getIncrease102());
        resultMapCrossAll.put("increase124", stockCrosst_000000.getIncrease124());
        resultMapCrossAll.put("increase146", stockCrosst_000000.getIncrease146());
        resultMapCrossAll.put("increase168", stockCrosst_000000.getIncrease168());
        resultMapCrossAll.put("increase1810", stockCrosst_000000.getIncrease1810());
        resultMapCrossAll.put("increase110", stockCrosst_000000.getIncrease110());

        resultMapCrossAll.put("increase202", stockCrosst_000000.getIncrease202());
        resultMapCrossAll.put("increase224", stockCrosst_000000.getIncrease224());
        resultMapCrossAll.put("increase246", stockCrosst_000000.getIncrease246());
        resultMapCrossAll.put("increase268", stockCrosst_000000.getIncrease268());
        resultMapCrossAll.put("increase2810", stockCrosst_000000.getIncrease2810());
        resultMapCrossAll.put("increase210", stockCrosst_000000.getIncrease210());

        resultMapCrossAll.put("increase302", stockCrosst_000000.getIncrease302());
        resultMapCrossAll.put("increase324", stockCrosst_000000.getIncrease324());
        resultMapCrossAll.put("increase346", stockCrosst_000000.getIncrease346());
        resultMapCrossAll.put("increase368", stockCrosst_000000.getIncrease368());
        resultMapCrossAll.put("increase3810", stockCrosst_000000.getIncrease3810());
        resultMapCrossAll.put("increase310", stockCrosst_000000.getIncrease310());

        resultMapCrossAll.put("increase402", stockCrosst_000000.getIncrease402());
        resultMapCrossAll.put("increase424", stockCrosst_000000.getIncrease424());
        resultMapCrossAll.put("increase446", stockCrosst_000000.getIncrease446());
        resultMapCrossAll.put("increase468", stockCrosst_000000.getIncrease468());
        resultMapCrossAll.put("increase4810", stockCrosst_000000.getIncrease4810());
        resultMapCrossAll.put("increase410", stockCrosst_000000.getIncrease410());

        resultMapCrossAll.put("increase502", stockCrosst_000000.getIncrease502());
        resultMapCrossAll.put("increase524", stockCrosst_000000.getIncrease524());
        resultMapCrossAll.put("increase546", stockCrosst_000000.getIncrease546());
        resultMapCrossAll.put("increase568", stockCrosst_000000.getIncrease568());
        resultMapCrossAll.put("increase5810", stockCrosst_000000.getIncrease5810());
        resultMapCrossAll.put("increase510", stockCrosst_000000.getIncrease510());


        resultMapCrossAll.put("descend102", stockCrosst_000000.getDescend102());
        resultMapCrossAll.put("descend124", stockCrosst_000000.getDescend124());
        resultMapCrossAll.put("descend146", stockCrosst_000000.getDescend146());
        resultMapCrossAll.put("descend168", stockCrosst_000000.getDescend168());
        resultMapCrossAll.put("descend1810", stockCrosst_000000.getDescend1810());
        resultMapCrossAll.put("descend110", stockCrosst_000000.getDescend110());

        resultMapCrossAll.put("descend202", stockCrosst_000000.getDescend202());
        resultMapCrossAll.put("descend224", stockCrosst_000000.getDescend224());
        resultMapCrossAll.put("descend246", stockCrosst_000000.getDescend246());
        resultMapCrossAll.put("descend268", stockCrosst_000000.getDescend268());
        resultMapCrossAll.put("descend2810", stockCrosst_000000.getDescend2810());
        resultMapCrossAll.put("descend210", stockCrosst_000000.getDescend210());

        resultMapCrossAll.put("descend302", stockCrosst_000000.getDescend302());
        resultMapCrossAll.put("descend324", stockCrosst_000000.getDescend324());
        resultMapCrossAll.put("descend346", stockCrosst_000000.getDescend346());
        resultMapCrossAll.put("descend368", stockCrosst_000000.getDescend368());
        resultMapCrossAll.put("descend3810", stockCrosst_000000.getDescend3810());
        resultMapCrossAll.put("descend310", stockCrosst_000000.getDescend310());

        resultMapCrossAll.put("descend402", stockCrosst_000000.getDescend402());
        resultMapCrossAll.put("descend424", stockCrosst_000000.getDescend424());
        resultMapCrossAll.put("descend446", stockCrosst_000000.getDescend446());
        resultMapCrossAll.put("descend468", stockCrosst_000000.getDescend468());
        resultMapCrossAll.put("descend4810", stockCrosst_000000.getDescend4810());
        resultMapCrossAll.put("descend410", stockCrosst_000000.getDescend410());

        resultMapCrossAll.put("descend502", stockCrosst_000000.getDescend502());
        resultMapCrossAll.put("descend524", stockCrosst_000000.getDescend524());
        resultMapCrossAll.put("descend546", stockCrosst_000000.getDescend546());
        resultMapCrossAll.put("descend568", stockCrosst_000000.getDescend568());
        resultMapCrossAll.put("descend5810", stockCrosst_000000.getDescend5810());
        resultMapCrossAll.put("descend510", stockCrosst_000000.getDescend510());


        resultMap.put("Cross", resultMapCross);
        resultMap.put("CrossAll", resultMapCrossAll);

        return resultMap;
    }


    @Override
    public Map<String, Object> getCrossEffectNew(String stockCode, int effectType) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        AnalyzeIncreaseDay analyzeIncreaseDay = new AnalyzeIncreaseDay();
        analyzeIncreaseDay.setStockDate(stockDate);
        analyzeIncreaseDay.setStockCode(stockCode);
        analyzeIncreaseDay.setEffectType(effectType);
        analyzeIncreaseDay.setIncreaseType(1);

        List<AnalyzeIncreaseDay> entryByStockCode = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

        if (entryByStockCode == null || entryByStockCode.size() < 1) {
            crossEffectInitNew(stockCode, stockDate);
        }


        Map<String, Object> resultmap = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            analyzeIncreaseDay.setIndexDay(i);
            analyzeIncreaseDay.setEffectType(effectType);
            analyzeIncreaseDay.setLevel(0);
            List<AnalyzeIncreaseDay> entryByStockCodeAll = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

            analyzeIncreaseDay.setLevel(1);
            List<AnalyzeIncreaseDay> entryByStockCodePart = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

            AnalyzeCorssEffectDto analyzeCorssEffectDto = getAnalyzeCorssEffectDto(entryByStockCodeAll.get(0), entryByStockCodePart.get(0));
            resultmap.put("crossEffect_" + i, analyzeCorssEffectDto);
        }

        analyzeIncreaseDay.setStockCode("000000");
        analyzeIncreaseDay.setStockDate(stockDate);
        List<AnalyzeIncreaseDay> entryByStockCodeListAll = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

        if (entryByStockCodeListAll == null || entryByStockCodeListAll.size() < 1) {
            crossEffectInitAllNew("000000", stockDate, effectType);
        }


        for (int i = 1; i < 6; i++) {
            analyzeIncreaseDay.setStockCode("000000");
            analyzeIncreaseDay.setIndexDay(i);
            analyzeIncreaseDay.setEffectType(0);
            analyzeIncreaseDay.setLevel(0);
            List<AnalyzeIncreaseDay> entryByStockCodeAll = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

            analyzeIncreaseDay.setLevel(1);
            List<AnalyzeIncreaseDay> entryByStockCodePart = iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);

            if (entryByStockCodeAll != null && entryByStockCodePart != null && entryByStockCodeAll.size() > 0 && entryByStockCodePart.size() > 0) {
                AnalyzeCorssEffectDto analyzeCorssEffectDto = getAnalyzeCorssEffectDto(entryByStockCodeAll.get(0), entryByStockCodePart.get(0));
                resultmap.put("crossEffectALL_" + i, analyzeCorssEffectDto);
            }
        }


        return resultmap;
    }

    @Override
    public Map<String, Object> getStockCrossEffectNewFinal(String stockCode, String crossType, String searchType) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        Map<String, Object> resultmap = new HashMap<>();
        AnalyzeIncreaseDay2 analyzeIncreaseDay2 = new AnalyzeIncreaseDay2();
        analyzeIncreaseDay2.setStockCode(stockCode);
        analyzeIncreaseDay2.setStockDate(stockDate);
        analyzeIncreaseDay2.setCrossType(crossType);
        analyzeIncreaseDay2.setSearchType(searchType);
        List<AnalyzeIncreaseDay2> listOwn = iStockAnalyzeIncreaseDay2Dao.getEntryByEntry(analyzeIncreaseDay2);

        if (listOwn == null || listOwn.size() == 0) {
            crossEffectInitNewFinal(stockCode);
            listOwn = iStockAnalyzeIncreaseDay2Dao.getEntryByEntry(analyzeIncreaseDay2);
        }
        resultmap.put("crossEffect", listOwn.get(0));

        analyzeIncreaseDay2.setStockCode("000000");
        List<AnalyzeIncreaseDay2> listAll = iStockAnalyzeIncreaseDay2Dao.getEntryByEntry(analyzeIncreaseDay2);

        if (listAll == null || listAll.size() == 0) {
            crossEffectInitNewFinalAll(stockDate, searchType);
            listAll = iStockAnalyzeIncreaseDay2Dao.getEntryByEntry(analyzeIncreaseDay2);
        }
        resultmap.put("crossEffectALL", listAll.get(0));


        System.out.println(resultmap);
        return resultmap;
    }


    @Override
    public void crossEffectInitNewFinal(String stockCode) {

        //删除历史数据
        iStockAnalyzeIncreaseDay2Dao.delByStockCode(stockCode, "");

        List<StockInfo> list = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        if (list != null && list.size() > 0) {

            double beforeDIF = list.get(0).getDIF();
            double beforeDEA = list.get(0).getEMAMACD();

            AnalyzeIncreaseDay2 analyzeIncreaseDay11_01 = new AnalyzeIncreaseDay2(stockCode, stockDate, "11", "01");
            AnalyzeIncreaseDay2 analyzeIncreaseDay10_01 = new AnalyzeIncreaseDay2(stockCode, stockDate, "10", "01");
            AnalyzeIncreaseDay2 analyzeIncreaseDay01_01 = new AnalyzeIncreaseDay2(stockCode, stockDate, "01", "01");
            AnalyzeIncreaseDay2 analyzeIncreaseDay00_01 = new AnalyzeIncreaseDay2(stockCode, stockDate, "00", "01");


            AnalyzeIncreaseDay2 analyzeIncreaseDay11_02 = new AnalyzeIncreaseDay2(stockCode, stockDate, "11", "02");
            AnalyzeIncreaseDay2 analyzeIncreaseDay10_02 = new AnalyzeIncreaseDay2(stockCode, stockDate, "10", "02");
            AnalyzeIncreaseDay2 analyzeIncreaseDay01_02 = new AnalyzeIncreaseDay2(stockCode, stockDate, "01", "02");
            AnalyzeIncreaseDay2 analyzeIncreaseDay00_02 = new AnalyzeIncreaseDay2(stockCode, stockDate, "00", "02");


            for (int i = 1; i < list.size(); i++) {
                Map<String, String> map = iStockInfoMacdServices.haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
                beforeDIF = list.get(i).getDIF();
                beforeDEA = list.get(i).getEMAMACD();

                if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来

                    switch (map.get("type")) {
                        case "00":
                            analyzeIncreaseDay00_01 = getIncreaseEffectFinal(list, i, analyzeIncreaseDay00_01);
                        case "01":
                            analyzeIncreaseDay01_01 = getIncreaseEffectFinal(list, i, analyzeIncreaseDay01_01);
                        case "10":
                            analyzeIncreaseDay10_01 = getIncreaseEffectFinal(list, i, analyzeIncreaseDay10_01);
                        case "11":
                            analyzeIncreaseDay11_01 = getIncreaseEffectFinal(list, i, analyzeIncreaseDay11_01);

                    }


                    switch (map.get("type")) {
                        case "00":
                            analyzeIncreaseDay00_02 = getIncreaseEffectMaxValue(list, i, analyzeIncreaseDay00_02);
                        case "01":
                            analyzeIncreaseDay01_02 = getIncreaseEffectMaxValue(list, i, analyzeIncreaseDay01_02);
                        case "10":
                            analyzeIncreaseDay10_02 = getIncreaseEffectMaxValue(list, i, analyzeIncreaseDay10_02);
                        case "11":
                            analyzeIncreaseDay11_02 = getIncreaseEffectMaxValue(list, i, analyzeIncreaseDay11_02);
                    }



                }
            }
            List<AnalyzeIncreaseDay2> ll = new ArrayList<>();
            ll.add(analyzeIncreaseDay11_01);
            ll.add(analyzeIncreaseDay10_01);
            ll.add(analyzeIncreaseDay00_01);
            ll.add(analyzeIncreaseDay01_01);

            ll.add(analyzeIncreaseDay11_02);
            ll.add(analyzeIncreaseDay10_02);
            ll.add(analyzeIncreaseDay00_02);
            ll.add(analyzeIncreaseDay01_02);

            iStockAnalyzeIncreaseDay2Dao.insert(ll);



        }
        System.out.println("金叉出现之后一定会涨 stockCode=" + stockCode);
    }


    /***
     * 金叉出现之后 最高价的 区间
     * @param stockCode
     */
    public void crossEffectInitNewFinalMaxvalue(String stockCode) {

        //删除历史数据
        iStockCrossDistributionDao.del(stockCode);

        List<StockInfo> list = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String stockDate = sdf.format(date);

        if (list != null && list.size() > 0) {

            double beforeDIF = list.get(0).getDIF();
            double beforeDEA = list.get(0).getEMAMACD();

            StockCrossDistribution stockCrossDistribution00 = new StockCrossDistribution(stockCode, stockDate, "00", "1");
            StockCrossDistribution stockCrossDistribution01 = new StockCrossDistribution(stockCode, stockDate, "01", "1");
            StockCrossDistribution stockCrossDistribution10 = new StockCrossDistribution(stockCode, stockDate, "10", "1");
            StockCrossDistribution stockCrossDistribution11 = new StockCrossDistribution(stockCode, stockDate, "11", "1");


            for (int i = 1; i < list.size(); i++) {
                Map<String, String> map = iStockInfoMacdServices.haveCross(list.get(i).getStockDate(), beforeDIF, beforeDEA, list.get(i).getDIF(), list.get(i).getEMAMACD());
                beforeDIF = list.get(i).getDIF();
                beforeDEA = list.get(i).getEMAMACD();

                if (!"-1".equals(map.get("type"))) { //有出现交叉 保存下来

                    switch (map.get("type")) {
                        case "00":
                            stockCrossDistribution00 = getIncreaseEffectMaxValueDistribution(list, i, stockCrossDistribution00);
                        case "01":
                            stockCrossDistribution01 = getIncreaseEffectMaxValueDistribution(list, i, stockCrossDistribution01);
                        case "10":
                            stockCrossDistribution10 = getIncreaseEffectMaxValueDistribution(list, i, stockCrossDistribution10);
                        case "11":
                            stockCrossDistribution11 = getIncreaseEffectMaxValueDistribution(list, i, stockCrossDistribution11);
                    }

                }
            }

            iStockCrossDistributionDao.insert(stockCrossDistribution00);
            iStockCrossDistributionDao.insert(stockCrossDistribution01);
            iStockCrossDistributionDao.insert(stockCrossDistribution10);
            iStockCrossDistributionDao.insert(stockCrossDistribution11);


        }
        System.out.println("金叉出现之后最高价的区间 stockCode=" + stockCode);
    }

    @Override
    public Map<String, Object> crossEffectInitNewFinalMaxValue(String  stockCode, String stockDate,String crossType,String dayNum) {

        Map<String, Object> resultmap = new HashMap<>();

        List<StockCrossDistribution> listOwn = iStockCrossDistributionDao.getStockCrosstDistributionList(stockCode, stockDate,crossType,dayNum);

        if (listOwn == null || listOwn.size() == 0) {
            crossEffectInitNewFinalMaxvalue(stockCode);
            listOwn = iStockCrossDistributionDao.getStockCrosstDistributionList(stockCode, stockDate,crossType,dayNum);
        }
        resultmap.put("crossEffect", listOwn.get(0));


        List<StockCrossDistribution> listAll = iStockCrossDistributionDao.getStockCrosstDistributionList("000000", stockDate,crossType,dayNum);

        if (listAll == null || listAll.size() == 0) {
            crossEffectInitNewFinalAllMaxvalue(stockDate, "1");
            listAll = iStockCrossDistributionDao.getStockCrosstDistributionList("000000", stockDate,crossType,dayNum);
        }
        resultmap.put("crossEffectALL", listAll.get(0));

        return resultmap;


    }


    public void crossEffectInitNewFinalAll(String stockDate, String searchType) {
        //删除历史数据
        iStockAnalyzeIncreaseDay2Dao.delByStockCode("000000", searchType);

        AnalyzeIncreaseDay2 analyzeIncreaseDay11 = new AnalyzeIncreaseDay2("000000", stockDate, "11", searchType);
        AnalyzeIncreaseDay2 analyzeIncreaseDay10 = new AnalyzeIncreaseDay2("000000", stockDate, "10", searchType);
        AnalyzeIncreaseDay2 analyzeIncreaseDay01 = new AnalyzeIncreaseDay2("000000", stockDate, "01", searchType);
        AnalyzeIncreaseDay2 analyzeIncreaseDay00 = new AnalyzeIncreaseDay2("000000", stockDate, "00", searchType);

        AnalyzeIncreaseDay2 analyzeIncreaseDay2 = new AnalyzeIncreaseDay2();
        analyzeIncreaseDay2.setSearchType(searchType);
        List<AnalyzeIncreaseDay2> entryByEntry = iStockAnalyzeIncreaseDay2Dao.getEntryByEntry(analyzeIncreaseDay2);

        for (int i = 0; i < entryByEntry.size(); i++) {
            switch (entryByEntry.get(i).getCrossType()) {
                case "00":
                    analyzeIncreaseDay00 = getIncreaseEffectFinalDay(entryByEntry.get(i), analyzeIncreaseDay00);
                    break;
                case "01":
                    analyzeIncreaseDay01 = getIncreaseEffectFinalDay(entryByEntry.get(i), analyzeIncreaseDay01);
                    break;
                case "10":
                    analyzeIncreaseDay10 = getIncreaseEffectFinalDay(entryByEntry.get(i), analyzeIncreaseDay10);
                    break;
                case "11":
                    analyzeIncreaseDay11 = getIncreaseEffectFinalDay(entryByEntry.get(i), analyzeIncreaseDay11);
                    break;
            }
        }
        iStockAnalyzeIncreaseDay2Dao.insert(analyzeIncreaseDay00);
        iStockAnalyzeIncreaseDay2Dao.insert(analyzeIncreaseDay01);
        iStockAnalyzeIncreaseDay2Dao.insert(analyzeIncreaseDay10);
        iStockAnalyzeIncreaseDay2Dao.insert(analyzeIncreaseDay11);


    }

    public void crossEffectInitNewFinalAllMaxvalue(String stockDate, String dayNum) {
        //删除历史数据
       iStockCrossDistributionDao.del("000000");
        StockCrossDistribution stockCrossDistribution11 = new StockCrossDistribution("000000", stockDate, "11", dayNum);
        StockCrossDistribution stockCrossDistribution10 = new StockCrossDistribution("000000", stockDate, "10", dayNum);
        StockCrossDistribution stockCrossDistribution01 = new StockCrossDistribution("000000", stockDate, "01", dayNum);
        StockCrossDistribution stockCrossDistribution00 = new StockCrossDistribution("000000", stockDate, "00", dayNum);

        List<StockCrossDistribution> entryByEntry = iStockCrossDistributionDao.getStockCrossList();

        for (int i = 0; i < entryByEntry.size(); i++) {
            switch (entryByEntry.get(i).getCrossType()) {
                case "00":
                    stockCrossDistribution00 = getIncreaseEffectFinalDayMaxvalue(entryByEntry.get(i), stockCrossDistribution00);
                    break;
                case "01":
                    stockCrossDistribution01 = getIncreaseEffectFinalDayMaxvalue(entryByEntry.get(i), stockCrossDistribution01);
                    break;
                case "10":
                    stockCrossDistribution10 = getIncreaseEffectFinalDayMaxvalue(entryByEntry.get(i), stockCrossDistribution10);
                    break;
                case "11":
                    stockCrossDistribution11 = getIncreaseEffectFinalDayMaxvalue(entryByEntry.get(i), stockCrossDistribution11);
                    break;
            }
        }
        iStockCrossDistributionDao.insert(stockCrossDistribution00);
        iStockCrossDistributionDao.insert(stockCrossDistribution01);
        iStockCrossDistributionDao.insert(stockCrossDistribution10);
        iStockCrossDistributionDao.insert(stockCrossDistribution11);
    }


    public static AnalyzeCorssEffectDto getAnalyzeCorssEffectDto(AnalyzeIncreaseDay analyzeIncreaseDayAll, AnalyzeIncreaseDay analyzeIncreaseDayPart) {

        AnalyzeCorssEffectDto analyzeCorssEffectDto = new AnalyzeCorssEffectDto();

        if (analyzeIncreaseDayAll.getIncrease10() != 0)
            analyzeCorssEffectDto.setIncrease10(analyzeIncreaseDayPart.getIncrease10() / analyzeIncreaseDayAll.getIncrease10());
        else
            analyzeCorssEffectDto.setIncrease10(0);


        if (analyzeIncreaseDayAll.getIncrease9() != 0)
            analyzeCorssEffectDto.setIncrease9(analyzeIncreaseDayPart.getIncrease9() / analyzeIncreaseDayAll.getIncrease9());
        else
            analyzeCorssEffectDto.setIncrease9(0);

        if (analyzeIncreaseDayAll.getIncrease8() != 0)
            analyzeCorssEffectDto.setIncrease8(analyzeIncreaseDayPart.getIncrease8() / analyzeIncreaseDayAll.getIncrease8());
        else
            analyzeCorssEffectDto.setIncrease8(0);

        if (analyzeIncreaseDayAll.getIncrease7() != 0)
            analyzeCorssEffectDto.setIncrease7(analyzeIncreaseDayPart.getIncrease7() / analyzeIncreaseDayAll.getIncrease7());
        else
            analyzeCorssEffectDto.setIncrease7(0);

        if (analyzeIncreaseDayAll.getIncrease6() != 0)
            analyzeCorssEffectDto.setIncrease6(analyzeIncreaseDayPart.getIncrease6() / analyzeIncreaseDayAll.getIncrease6());
        else
            analyzeCorssEffectDto.setIncrease6(0);

        if (analyzeIncreaseDayAll.getIncrease5() != 0)
            analyzeCorssEffectDto.setIncrease5(analyzeIncreaseDayPart.getIncrease5() / analyzeIncreaseDayAll.getIncrease5());
        else
            analyzeCorssEffectDto.setIncrease5(0);


        if (analyzeIncreaseDayAll.getIncrease4() != 0)
            analyzeCorssEffectDto.setIncrease4(analyzeIncreaseDayPart.getIncrease4() / analyzeIncreaseDayAll.getIncrease4());
        else
            analyzeCorssEffectDto.setIncrease4(0);

        if (analyzeIncreaseDayAll.getIncrease3() != 0)
            analyzeCorssEffectDto.setIncrease3(analyzeIncreaseDayPart.getIncrease3() / analyzeIncreaseDayAll.getIncrease3());
        else
            analyzeCorssEffectDto.setIncrease3(0);


        if (analyzeIncreaseDayAll.getIncrease2() != 0)
            analyzeCorssEffectDto.setIncrease2(analyzeIncreaseDayPart.getIncrease2() / analyzeIncreaseDayAll.getIncrease2());
        else
            analyzeCorssEffectDto.setIncrease2(0);

        if (analyzeIncreaseDayAll.getIncrease1() != 0)
            analyzeCorssEffectDto.setIncrease1(analyzeIncreaseDayPart.getIncrease1() / analyzeIncreaseDayAll.getIncrease1());
        else
            analyzeCorssEffectDto.setIncrease1(0);

        if (analyzeIncreaseDayAll.getIncrease0() != 0)
            analyzeCorssEffectDto.setIncrease0(analyzeIncreaseDayPart.getIncrease0() / analyzeIncreaseDayAll.getIncrease0());
        else
            analyzeCorssEffectDto.setIncrease0(0);


        if (analyzeIncreaseDayAll.getDescend1() != 0)
            analyzeCorssEffectDto.setDescend1(analyzeIncreaseDayPart.getDescend1() / analyzeIncreaseDayAll.getDescend1());
        else
            analyzeCorssEffectDto.setDescend1(0);


        if (analyzeIncreaseDayAll.getDescend2() != 0)
            analyzeCorssEffectDto.setDescend2(analyzeIncreaseDayPart.getDescend2() / analyzeIncreaseDayAll.getDescend2());
        else
            analyzeCorssEffectDto.setDescend2(0);

        if (analyzeIncreaseDayAll.getDescend3() != 0)
            analyzeCorssEffectDto.setDescend3(analyzeIncreaseDayPart.getDescend3() / analyzeIncreaseDayAll.getDescend3());
        else
            analyzeCorssEffectDto.setDescend3(0);

        if (analyzeIncreaseDayAll.getDescend3() != 0)
            analyzeCorssEffectDto.setDescend3(analyzeIncreaseDayPart.getDescend3() / analyzeIncreaseDayAll.getDescend3());
        else
            analyzeCorssEffectDto.setDescend3(0);

        if (analyzeIncreaseDayAll.getDescend5() != 0)
            analyzeCorssEffectDto.setDescend5(analyzeIncreaseDayPart.getDescend5() / analyzeIncreaseDayAll.getDescend5());
        else
            analyzeCorssEffectDto.setDescend5(0);


        if (analyzeIncreaseDayAll.getDescend6() != 0)
            analyzeCorssEffectDto.setDescend6(analyzeIncreaseDayPart.getDescend6() / analyzeIncreaseDayAll.getDescend6());
        else
            analyzeCorssEffectDto.setDescend6(0);

        if (analyzeIncreaseDayAll.getDescend7() != 0)
            analyzeCorssEffectDto.setDescend7(analyzeIncreaseDayPart.getDescend7() / analyzeIncreaseDayAll.getDescend7());
        else
            analyzeCorssEffectDto.setDescend7(0);

        if (analyzeIncreaseDayAll.getDescend8() != 0)
            analyzeCorssEffectDto.setDescend8(analyzeIncreaseDayPart.getDescend8() / analyzeIncreaseDayAll.getDescend8());
        else
            analyzeCorssEffectDto.setDescend8(0);

        if (analyzeIncreaseDayAll.getDescend9() != 0)
            analyzeCorssEffectDto.setDescend9(analyzeIncreaseDayPart.getDescend9() / analyzeIncreaseDayAll.getDescend9());
        else
            analyzeCorssEffectDto.setDescend9(0);

        if (analyzeIncreaseDayAll.getDescend10() != 0)
            analyzeCorssEffectDto.setDescend10(analyzeIncreaseDayPart.getDescend10() / analyzeIncreaseDayAll.getDescend10());
        else
            analyzeCorssEffectDto.setDescend10(0);

        if (analyzeIncreaseDayAll.getDescend20() != 0)
            analyzeCorssEffectDto.setDescend20(analyzeIncreaseDayPart.getDescend20() / analyzeIncreaseDayAll.getDescend20());
        else
            analyzeCorssEffectDto.setDescend20(0);

        return analyzeCorssEffectDto;
    }

    @Override
    public String crossEffectMacdValueInit(String stockCode) {
        return null;
    }

    @Override
    public void crossEffectMacdValueAllInit(String type) {
//todo
    }

    @Override
    public String getCrossEffectMacdValue(String stockCode) {
        //todo
        String jsonStr = JSON.toJSONString("");
        return jsonStr;
    }


}
