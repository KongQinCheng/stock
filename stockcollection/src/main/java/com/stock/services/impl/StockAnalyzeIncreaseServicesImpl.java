package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.StockAnalyzeIncrease;
import com.stock.bean.po.StockInfo;
import com.stock.dao.IStockAnalyzeIncreaseDayDao;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockAnalyzeIncreaseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockAnalyzeIncreaseServicesImpl implements IStockAnalyzeIncreaseServices {


    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @Autowired
    IStockInfoDao iStockInfoDao;


    @Autowired
    IStockAnalyzeIncreaseDayDao iStockAnalyzeIncreaseDayDao;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public void getStockAnalyzeIncreaseAll(String stockCode) {

        //根据股票代码查询历史数据
        List<StockInfo> stockInfoList = iStockInfoDao.getStockListByStockCode(stockCode, 999999999);

        iStockAnalyzeIncreaseDayDao.delByStockCode(stockCode, "0");

        //查询股票的 涨幅区间 所在的天数 占总发行天数的百分比
        getIncreaseEffectAfterday(stockCode, stockInfoList, 0, 0);

        // effectType 1：前一天的涨幅区间对 后一天上涨的影响
        for (int i = 1; i < 6; i++) {
            getIncreaseEffectAfterday(stockCode, stockInfoList, i, 1);
        }

        //effectType 2：前一天上涨对后一天涨幅的影响
        for (int i = 1; i < 6; i++) {
            getIncreaseEffectAfterday(stockCode, stockInfoList, i, 2);
        }


    }


    public void getStockIncreaseAnalyzeToTable(List<StockInfo> stockInfoList) {

        //前 N 天的的涨幅对后一天的影响 最终 的概率为
        for (int i = 0; i < 30; i++) {
            getProbabilityByBeforeDay(stockInfoList, i);
        }


        //前  N 天的 平均 涨幅对后一天的影响 最终 的概率为
        for (int i = 1; i < 31; i++) {
            getAvgByBeforeDay(stockInfoList, i);
        }

    }

    @Override
    public void insert(AnalyzeIncreaseDay analyzeIncreaseDay) {
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDay);

    }

    @Override
    public List<AnalyzeIncreaseDay> getListAll(String effectType) {
        return iStockAnalyzeIncreaseDayDao.getListAll(effectType);
    }

    @Override
    public List<AnalyzeIncreaseDay> getEntryByStockCode(String stockCode, String stockDate, String effectType, String increaseType) {
        return iStockAnalyzeIncreaseDayDao.getEntryByStockCode(stockCode, stockDate, effectType, increaseType);
    }

    @Override
    public List<AnalyzeIncreaseDay> getEntryByEntry(AnalyzeIncreaseDay analyzeIncreaseDay) {
        return iStockAnalyzeIncreaseDayDao.getEntryByEntry(analyzeIncreaseDay);
    }


    @Override
    public void delByStockCode(String stockCode, String increaseType) {
        iStockAnalyzeIncreaseDayDao.delByStockCode(stockCode, increaseType);
    }

    @Override
    public boolean isNewCount(String stockCode, String stockDate) {
        return iStockAnalyzeIncreaseDayDao.isNewCount(stockCode, stockDate);
    }

    @Override
    public Map<String, Object> getStockIncreaseEffect(String stockCode) {
        return null;
    }


    /***
     * 前面后一天的上浮 对今天的影响
     * @param list
     * @param beginIndex
     * @return
     */
    public double getProbabilityByBeforeDay(List<StockInfo> list, int beginIndex) {

        double beforeDayIncrease = 0; //初始化涨幅为0
        double increaseCount = 0;

        for (int i = beginIndex; i < list.size(); i++) {
            if (beforeDayIncrease >= 0 && list.get(i).getZdf() >= 0) {
                increaseCount++;
            }
            beforeDayIncrease = list.get(i - beginIndex).getZdf();
//            System.out.println("前 "+beginIndex+++" 天的涨幅对后一天的影响的概率为："+increaseCount/list.size());
        }
        beginIndex = beginIndex + 1;
        System.out.println("前 " + beginIndex + "天的涨幅对后一天的影响 最终 的概率为：" + increaseCount / list.size()); //0.2565789473684211
        return increaseCount / list.size();
    }

    /***
     *   前  avgDay 天的 平均 涨幅对后一天的影响 最终 的概率为
     * @param list
     * @param avgDay
     * @return
     */
    public double getAvgByBeforeDay(List<StockInfo> list, int avgDay) {


        double increaseCount = 0;
        double[] zdfArrzy = new double[avgDay];

        for (int i = 0; i < list.size(); i++) {

            if (getCount(zdfArrzy) > 0 && list.get(i).getZdf() >= 0) {
                increaseCount++;
            }
            zdfArrzy[i % avgDay] = list.get(i).getZdf();
        }
        System.out.println("前 " + avgDay + "天的 平均 涨幅对后一天的影响 最终 的概率为：" + increaseCount / list.size()); //0.2565789473684211
        return increaseCount / list.size();

    }

    public double getCount(double[] zdfArrzy) {
        double increaseCount = 0;
        for (int i = 0; i < zdfArrzy.length; i++) {
            increaseCount += zdfArrzy[i];
        }
        return increaseCount;
    }


    public double getProbability(List<StockInfo> list, int beginIndex, int endIndex) {

        for (int i = beginIndex; i < endIndex; i++) {


        }

        return 0;
    }


    public void getIncreaseEffectAfterday(String stockCode, List<StockInfo> stockInfoList, int dayNum, int effectType) {

        iStockAnalyzeIncreaseDayDao.delByStockCode(stockCode, "0");

        if (stockInfoList.size() >= dayNum) {
            AnalyzeIncreaseDay analyzeIncreaseDay = new AnalyzeIncreaseDay();

            if (effectType == 0) {
                for (int i = 0; i < stockInfoList.size(); i++) {
                    analyzeIncreaseDay = getStockIncreaseBase(stockInfoList.get(i), analyzeIncreaseDay);
                }
                analyzeIncreaseDay.setIndexDay(0);
                analyzeIncreaseDay.setEffectType(0);
            }

            //1：前一天的涨幅区间对 后一天上涨的影响，
            if (effectType == 1) {
                for (int i = dayNum; i < stockInfoList.size(); i++) {
                    double zdf = stockInfoList.get(i).getZdf();
                    if (zdf >= 0) {
                        analyzeIncreaseDay = getStockIncreaseBase(stockInfoList.get(i - dayNum), analyzeIncreaseDay);
                    }
                }
                analyzeIncreaseDay.setIndexDay(dayNum);
                analyzeIncreaseDay.setEffectType(1);
            }
            //2：前一天上涨对后一天涨幅的影响
            if (effectType == 2) {
                for (int i = 0; i < stockInfoList.size(); i++) {
                    double zdf = stockInfoList.get(i).getZdf();
                    if (zdf >= 0) {
                        if (i + dayNum < stockInfoList.size())
                            analyzeIncreaseDay = getStockIncreaseBase(stockInfoList.get(i + dayNum), analyzeIncreaseDay);
                    }
                }
                analyzeIncreaseDay.setIndexDay(dayNum);
                analyzeIncreaseDay.setEffectType(2);
            }

            //若 今天为上涨的情况 ，前一天的涨幅分别为多少
            analyzeIncreaseDay.setStockCode(stockCode);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date befor_day = new Date();
            analyzeIncreaseDay.setStockDate(sdf2.format(befor_day));
            analyzeIncreaseDay.setCount(stockInfoList.size() + 0.0);
            analyzeIncreaseDay.setIncreaseType(0);
            iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDay);
        }
        System.out.println("涨幅影响 更新完毕stockCode=" + stockCode);
    }

    //统计所有股票的涨跌幅的影响。
    public void getStockIncreaseAnalyzeToTableAll(String effectType) {

        List<AnalyzeIncreaseDay> listAll = iStockAnalyzeIncreaseDayDao.getListAll(effectType);
        AnalyzeIncreaseDay analyzeIncreaseDay = new AnalyzeIncreaseDay();
        for (int i = 0; i < listAll.size(); i++) {
            analyzeIncreaseDay.setIncrease10(analyzeIncreaseDay.getIncrease10() + listAll.get(i).getIncrease10());
            analyzeIncreaseDay.setIncrease9(analyzeIncreaseDay.getIncrease9() + listAll.get(i).getIncrease9());
            analyzeIncreaseDay.setIncrease8(analyzeIncreaseDay.getIncrease8() + listAll.get(i).getIncrease8());
            analyzeIncreaseDay.setIncrease7(analyzeIncreaseDay.getIncrease7() + listAll.get(i).getIncrease7());
            analyzeIncreaseDay.setIncrease6(analyzeIncreaseDay.getIncrease6() + listAll.get(i).getIncrease6());
            analyzeIncreaseDay.setIncrease5(analyzeIncreaseDay.getIncrease5() + listAll.get(i).getIncrease5());
            analyzeIncreaseDay.setIncrease4(analyzeIncreaseDay.getIncrease4() + listAll.get(i).getIncrease4());
            analyzeIncreaseDay.setIncrease3(analyzeIncreaseDay.getIncrease3() + listAll.get(i).getIncrease3());
            analyzeIncreaseDay.setIncrease2(analyzeIncreaseDay.getIncrease2() + listAll.get(i).getIncrease2());
            analyzeIncreaseDay.setIncrease1(analyzeIncreaseDay.getIncrease1() + listAll.get(i).getIncrease1());
            analyzeIncreaseDay.setIncrease0(analyzeIncreaseDay.getIncrease0() + listAll.get(i).getIncrease0());

            analyzeIncreaseDay.setDescend1(analyzeIncreaseDay.getDescend1() + listAll.get(i).getDescend1());
            analyzeIncreaseDay.setDescend2(analyzeIncreaseDay.getDescend2() + listAll.get(i).getDescend2());
            analyzeIncreaseDay.setDescend3(analyzeIncreaseDay.getDescend3() + listAll.get(i).getDescend3());
            analyzeIncreaseDay.setDescend4(analyzeIncreaseDay.getDescend4() + listAll.get(i).getDescend4());
            analyzeIncreaseDay.setDescend5(analyzeIncreaseDay.getDescend5() + listAll.get(i).getDescend5());
            analyzeIncreaseDay.setDescend6(analyzeIncreaseDay.getDescend6() + listAll.get(i).getDescend6());
            analyzeIncreaseDay.setDescend7(analyzeIncreaseDay.getDescend7() + listAll.get(i).getDescend7());
            analyzeIncreaseDay.setDescend8(analyzeIncreaseDay.getDescend8() + listAll.get(i).getDescend8());
            analyzeIncreaseDay.setDescend9(analyzeIncreaseDay.getDescend9() + listAll.get(i).getDescend9());
            analyzeIncreaseDay.setDescend10(analyzeIncreaseDay.getDescend10() + listAll.get(i).getDescend10());
            analyzeIncreaseDay.setDescend20(analyzeIncreaseDay.getDescend20() + listAll.get(i).getDescend20());
            analyzeIncreaseDay.setCount(analyzeIncreaseDay.getCount() + listAll.get(i).getCount());
        }
        analyzeIncreaseDay.setEffectType(Integer.valueOf(effectType));
        analyzeIncreaseDay.setStockCode("000000");

        Date dt = new Date();
        String created_at = sdf.format(dt);
        analyzeIncreaseDay.setStockDate(created_at);
        analyzeIncreaseDay.setIncreaseType(0);
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDay);

    }

    //判断是否是今天最新的统计数据
    public Map<String, Object> getStockIncreaseEffect(String stockCode, String effectType) {


        Date dt = new Date();
        String created_at = sdf.format(dt);
        Map<String, Object> resultMap = new HashMap<>();

        //查找昨日的涨跌幅
        List<StockInfo> newStockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.DESC.toString(), 1);
        if (newStockListByStockCode.size() == 1) {
            resultMap.put("zdf", newStockListByStockCode.get(0).getZdf());
        } else {
            resultMap.put("zdf", 0);
        }

        //查当前的的stockCode
        List<AnalyzeIncreaseDay> entryByStockCode = iStockAnalyzeIncreaseDayDao.getEntryByStockCode(stockCode, "", effectType, "0");
        if (entryByStockCode == null || entryByStockCode.size() < 1) {
            getIncreaseEffectAfterday(stockCode, newStockListByStockCode, 0, 0);
            entryByStockCode = iStockAnalyzeIncreaseDayDao.getEntryByStockCode(stockCode, created_at, effectType, "0");
        }

        List<AnalyzeIncreaseDay> entryByStockCode_000000 = iStockAnalyzeIncreaseDayDao.getEntryByStockCode("000000", created_at, effectType, "0");
        if (entryByStockCode_000000 == null || entryByStockCode_000000.size() == 0) {
            iStockAnalyzeIncreaseDayDao.delByStockCode("000000", "0");
            getStockIncreaseAnalyzeToTableAll(effectType);
            entryByStockCode_000000 = iStockAnalyzeIncreaseDayDao.getEntryByStockCode("000000", created_at, effectType, "0");
        }


        resultMap.put("resultMapOwn", entryByStockCode);
        resultMap.put("resultMapAll", entryByStockCode_000000);

        return resultMap;
    }


    /***
     *
     * @param stockCode
     * @param stockInfoList
     */
    private void getStockIncrease(String stockCode, List<StockInfo> stockInfoList) {
        AnalyzeIncreaseDay analyzeIncreaseDay = new AnalyzeIncreaseDay();
        for (int i = 0; i < stockInfoList.size(); i++) {
            analyzeIncreaseDay = getStockIncreaseBase(stockInfoList.get(i), analyzeIncreaseDay);
        }
        analyzeIncreaseDay.setStockCode(stockCode);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date befor_day = new Date();
        analyzeIncreaseDay.setStockDate(sdf2.format(befor_day));
        analyzeIncreaseDay.setCount(stockInfoList.size() + 0.0);
        analyzeIncreaseDay.setIndexDay(0);
        analyzeIncreaseDay.setEffectType(0);
        analyzeIncreaseDay.setIncreaseType(0);
        iStockAnalyzeIncreaseDayDao.insert(analyzeIncreaseDay);
    }


    public static AnalyzeIncreaseDay getStockIncreaseBase(AnalyzeIncreaseDay analyzeIncreaseDay, AnalyzeIncreaseDay analyzeIncreaseDayReturn) {

        analyzeIncreaseDayReturn.setIncrease10(analyzeIncreaseDayReturn.getIncrease10() + analyzeIncreaseDay.getIncrease10());
        analyzeIncreaseDayReturn.setIncrease9(analyzeIncreaseDayReturn.getIncrease9() + analyzeIncreaseDay.getIncrease9());
        analyzeIncreaseDayReturn.setIncrease8(analyzeIncreaseDayReturn.getIncrease8() + analyzeIncreaseDay.getIncrease8());
        analyzeIncreaseDayReturn.setIncrease7(analyzeIncreaseDayReturn.getIncrease7() + analyzeIncreaseDay.getIncrease7());
        analyzeIncreaseDayReturn.setIncrease6(analyzeIncreaseDayReturn.getIncrease6() + analyzeIncreaseDay.getIncrease6());
        analyzeIncreaseDayReturn.setIncrease5(analyzeIncreaseDayReturn.getIncrease5() + analyzeIncreaseDay.getIncrease5());
        analyzeIncreaseDayReturn.setIncrease4(analyzeIncreaseDayReturn.getIncrease4() + analyzeIncreaseDay.getIncrease4());
        analyzeIncreaseDayReturn.setIncrease3(analyzeIncreaseDayReturn.getIncrease3() + analyzeIncreaseDay.getIncrease3());
        analyzeIncreaseDayReturn.setIncrease2(analyzeIncreaseDayReturn.getIncrease2() + analyzeIncreaseDay.getIncrease2());
        analyzeIncreaseDayReturn.setIncrease1(analyzeIncreaseDayReturn.getIncrease1() + analyzeIncreaseDay.getIncrease1());
        analyzeIncreaseDayReturn.setIncrease0(analyzeIncreaseDayReturn.getIncrease0() + analyzeIncreaseDay.getIncrease0());

        analyzeIncreaseDayReturn.setDescend1(analyzeIncreaseDayReturn.getDescend1() + analyzeIncreaseDay.getDescend1());
        analyzeIncreaseDayReturn.setDescend2(analyzeIncreaseDayReturn.getDescend2() + analyzeIncreaseDay.getDescend2());
        analyzeIncreaseDayReturn.setDescend3(analyzeIncreaseDayReturn.getDescend3() + analyzeIncreaseDay.getDescend3());
        analyzeIncreaseDayReturn.setDescend4(analyzeIncreaseDayReturn.getDescend4() + analyzeIncreaseDay.getDescend4());
        analyzeIncreaseDayReturn.setDescend5(analyzeIncreaseDayReturn.getDescend5() + analyzeIncreaseDay.getDescend5());
        analyzeIncreaseDayReturn.setDescend6(analyzeIncreaseDayReturn.getDescend6() + analyzeIncreaseDay.getDescend6());
        analyzeIncreaseDayReturn.setDescend7(analyzeIncreaseDayReturn.getDescend7() + analyzeIncreaseDay.getDescend7());
        analyzeIncreaseDayReturn.setDescend8(analyzeIncreaseDayReturn.getDescend8() + analyzeIncreaseDay.getDescend8());
        analyzeIncreaseDayReturn.setDescend9(analyzeIncreaseDayReturn.getDescend9() + analyzeIncreaseDay.getDescend9());
        analyzeIncreaseDayReturn.setDescend10(analyzeIncreaseDayReturn.getDescend10() + analyzeIncreaseDay.getDescend10());
        analyzeIncreaseDayReturn.setDescend20(analyzeIncreaseDayReturn.getDescend20() + analyzeIncreaseDay.getDescend20());
        return analyzeIncreaseDayReturn;
    }

    public static AnalyzeIncreaseDay getStockIncreaseBase(StockInfo stockInfo, AnalyzeIncreaseDay analyzeIncreaseDay) {

        if (stockInfo.getZdf() >= 10) {
            analyzeIncreaseDay.setIncrease10(analyzeIncreaseDay.getIncrease10() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 9) {
            analyzeIncreaseDay.setIncrease9(analyzeIncreaseDay.getIncrease9() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 8) {
            analyzeIncreaseDay.setIncrease8(analyzeIncreaseDay.getIncrease8() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 7) {
            analyzeIncreaseDay.setIncrease7(analyzeIncreaseDay.getIncrease7() + 1.0);
            return analyzeIncreaseDay;
        }

        if (stockInfo.getZdf() >= 6) {
            analyzeIncreaseDay.setIncrease6(analyzeIncreaseDay.getIncrease6() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 5) {
            analyzeIncreaseDay.setIncrease5(analyzeIncreaseDay.getIncrease5() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 4) {
            analyzeIncreaseDay.setIncrease4(analyzeIncreaseDay.getIncrease4() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 3) {
            analyzeIncreaseDay.setIncrease3(analyzeIncreaseDay.getIncrease3() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 2) {
            analyzeIncreaseDay.setIncrease2(analyzeIncreaseDay.getIncrease2() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 1) {
            analyzeIncreaseDay.setIncrease1(analyzeIncreaseDay.getIncrease1() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= 0) {
            analyzeIncreaseDay.setIncrease0(analyzeIncreaseDay.getIncrease0() + 1.0);
            return analyzeIncreaseDay;
        }


        if (stockInfo.getZdf() >= -1) {
            analyzeIncreaseDay.setDescend1(analyzeIncreaseDay.getDescend1() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -2) {
            analyzeIncreaseDay.setDescend2(analyzeIncreaseDay.getDescend2() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -3) {
            analyzeIncreaseDay.setDescend3(analyzeIncreaseDay.getDescend3() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -4) {
            analyzeIncreaseDay.setDescend4(analyzeIncreaseDay.getDescend4() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -5) {
            analyzeIncreaseDay.setDescend5(analyzeIncreaseDay.getDescend5() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -6) {
            analyzeIncreaseDay.setDescend6(analyzeIncreaseDay.getDescend6() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -7) {
            analyzeIncreaseDay.setDescend7(analyzeIncreaseDay.getDescend7() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -8) {
            analyzeIncreaseDay.setDescend8(analyzeIncreaseDay.getDescend8() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -9) {
            analyzeIncreaseDay.setDescend9(analyzeIncreaseDay.getDescend9() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -10) {
            analyzeIncreaseDay.setDescend10(analyzeIncreaseDay.getDescend10() + 1.0);
            return analyzeIncreaseDay;
        }
        if (stockInfo.getZdf() >= -11) {
            analyzeIncreaseDay.setDescend20(analyzeIncreaseDay.getDescend20() + 1.0);
            return analyzeIncreaseDay;
        }

        return analyzeIncreaseDay;
    }
}
