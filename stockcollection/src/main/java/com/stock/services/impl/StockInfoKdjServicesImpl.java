package com.stock.services.impl;

import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.dao.IStockNewDataDao;
import com.stock.mapper.StockInfoMapper;
import com.stock.services.IStockInfoKdjServices;
import com.stock.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockInfoKdjServicesImpl implements IStockInfoKdjServices {

    static DecimalFormat df = new java.text.DecimalFormat("#.###");

    @Autowired
    IStockInfoDao iStockInfoDao;

    @Autowired
    IStockListDao iStockListDao;

    @Autowired
    IStockNewDataDao iStockNewDataDao ;

    @Override
    public  void getKDJValue(String stockCode) throws Exception {

        List<StockInfo> stockinfoList = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(),15);

        double[] lszgjArray = new double[9];
        double[] lszdjArray = new double[9];

        double maxValue=0.0;
        double minVaule=0.0;
        double jValue=0.0;

        if (stockinfoList.size()<10){
            return;
        }

        //将前面9天的数据保存的列表中
        for (int i = 0; i <9 ; i++) {
            StockInfo stockInfo = stockinfoList.get(i);
            lszgjArray[i] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[i] = Double.valueOf(stockInfo.getZdj());
        }

        double kValue = 50.0;
        double dValue = 50.0;

//        double kValue = 68.02;
//        double dValue = 54.28;

        //从第9天开始计算KDJ值
        for (int i = 9; i <stockinfoList.size() ; i++) {

            int insertArrayIndex = i % 9;
            StockInfo stockInfo = stockinfoList.get(i);
            lszgjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZgj());
            lszdjArray[insertArrayIndex] = Double.valueOf(stockInfo.getZdj());

            if (!(stockinfoList.get(i).getKValue()==0.0&& stockinfoList.get(i).getDValue()==0.0&& stockinfoList.get(i).getJValue()==0.0)){
                kValue =stockinfoList.get(i).getKValue();
                dValue=stockinfoList.get(i).getDValue();
                continue;
            }

            maxValue = getValueByType(lszgjArray, 1);
            minVaule = getValueByType(lszdjArray, 0);
            if (maxValue !=minVaule){
                double rsv = getRsv((double) (stockinfoList.get(i).getSpj()), (double)maxValue, minVaule);
                kValue = getK(kValue, rsv);
                dValue = getD(dValue, kValue);
                jValue = getJ(kValue, dValue);
            }

           StockInfo  stockInfo1 = stockinfoList.get(i);
           stockInfo1.setStockCode(stockCode);
           stockInfo1.setKValue(kValue);
           stockInfo1.setDValue(dValue);
           stockInfo1.setJValue(jValue);
           stockInfo1.setStockDate(stockinfoList.get(i).getStockDate());
            iStockInfoDao.updateStockInfoKDJ(stockInfo1);
        }
        System.out.println("KDJ 更新完毕stockCode=" + stockCode);
    }

    @Override
    public Map<String, Object> getKdjCross(String stockCode,int dayNum, String crossType) {
        List<StockInfo> stockInfoList = iStockInfoDao.getNewStockListByStockCode(stockCode, SortType.ASC.toString(), dayNum+2);
        Map<String, Object> existCross = isExistCross(stockInfoList, dayNum, crossType);
        return existCross;
    }

    @Override
    public List<Map<String, Object>> getKdjCrossAll(StockSearchVo stockSearchVo) {
        List<Map<String, Object>> list =new ArrayList<>();

        List<StockList> stockList = iStockListDao.getStockList();
//        for (int i = 0; i < 100; i++) {
        for (int i = 0; i < stockList.size(); i++) {
            Map<String, Object> kdjCross = getKdjCross(stockList.get(i).getStockCode().replaceAll("\t", "") + "",stockSearchVo.getDayNum() , stockSearchVo.getCrossType());
            if ((boolean)kdjCross.get("result")==true){
                list.add(kdjCross);
            }
        }
        return list;
    }

    @Override
    public List<StockNewData> getStockKdjValueRegion(StockNewDataVo stockNewDataVo) {
        return iStockNewDataDao.getStockKdjValueRegion(stockNewDataVo);
    }

    private Map<String, Object> isExistCross(List<StockInfo> list, int dayNum, String crossType) {

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", false);
        if (list.size() < 1) {
            return resultMap;
        }

        double beforeK = list.get(0).getKValue();
        double beforeD = list.get(0).getDValue();
        double beforeJ = list.get(0).getJValue();

        //查找 K 值和 D 值 最后的值
        String stockCode =list.get(0).getStockCode();
        for (int i = 1; i < list.size(); i++) {
            Map<String, String> map = haveCross(list.get(i).getStockDate(), beforeK, beforeD, list.get(i).getKValue(), list.get(i).getDValue());
            beforeK = list.get(i).getKValue();
            beforeD = list.get(i).getDValue();

            if (crossType.equals(map.get("type"))) {
                resultMap.put("result", true);
                resultMap.put("stockCode",stockCode );
                resultMap.put("spj",list.get(list.size()-1).getSpj());
                resultMap.put("zdf",list.get(list.size()-1).getZdf());
                resultMap.put("stockDate", list.get(i).getStockDate());
                return resultMap;
            }

        }
        return resultMap;
    }

    private Map<String, String> haveCross(String day, double beforeK , double beforeD, double todayK , double todayD) {


        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("type", "-1");

        //出现金叉    beforeD-beforeK>0   &&  todayD - todayK < 0
        // KDJ有效金叉,条件是：K>=D20%
        if (beforeD - beforeK > 0 && todayD - todayK < 0) {
            if (beforeK < 20 && beforeD < 20 &&  beforeK> 0.2*beforeD) {  //超买
                resultMap.put("type", "11");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 KDJ 金叉 超买");
            }
        }

        //出现死叉
        if (beforeD - beforeK < 0 && todayD - todayK > 0) {
            if (todayD > 80 && todayK > 80) { //超卖
                resultMap.put("type", "00");
                resultMap.put("time", day);
                resultMap.put("desc", "出现 KDJ 死叉 超卖");
            }
        }
        return resultMap;
    }



    /***
     *
     * @param array
     * @param type  0:查最小值  1：查最大值
     * @return
     */
    public static double getValueByType(double[] array, int type) {
        double result = array[0];
        for (int i = 0; i < array.length; i++) {
            if (type == 0) {
                if (result > array[i]) {
                    result = array[i];
                }
            }
            if (type == 1) {
                if (result < array[i]) {
                    result = array[i];
                }
            }
        }
        return result;
    }

    public static double getRsv(double spj, double zgj, double zdj) {
        //RSV=（收盘价－最低价）/（最高价－最低价）×100
        return (spj - zdj) / (zgj - zdj) * 100 ;
    }

    public static double getK(double beforeDayK, double todayRSV) {

        // K值=2/3×第8日K值+1/3×第9日RSV
        return (2 / 3.0000 * beforeDayK) + (1 / 3.0000 * todayRSV);
    }

    public static double getD(double beforeDayD, double todayK) throws Exception {

        // D值=2/3×第8日D值+1/3×第9日K值
        return 2 / 3.0000 * beforeDayD + 1 / 3.0000 * todayK;
    }

    public static double getJ(double todayK, double todayD) throws Exception {

        // J值=3*第9日K值-2*第9日D值
        return 3 * todayK - 2 * todayD;
    }



}
