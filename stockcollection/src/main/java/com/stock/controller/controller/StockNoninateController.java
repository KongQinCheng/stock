package com.stock.controller.controller;

import com.alibaba.fastjson.JSON;
import com.stock.Enum.CrossType;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.to.StockNominateTo;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stock")
public class StockNoninateController {

    @Autowired
    IStockInfoServices iStockInfoServices;

    @Autowired
    IStockNewDataServices iStockNewDataServices;

    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;


    @Autowired
    IStockAnalyzeMacdServices iStockAnalyzeMacdServices;

    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @Autowired
    IStockAnalyzeIncreaseServices iStockAnalyzeIncreaseServices;



    @RequestMapping("/toStockIncrease")
    public String toStockIncrease() {
        return "stock/stock_increase";
    }


    @RequestMapping("/toStockMultiple")
    public String toStockMultiple() {
        return "stock/stock_multiple";
    }


    /***
     * 获取综合信息
     * @param stockNewDataVo
     * @return
     */
    @PostMapping(value = "/getStockMultiple", consumes = "application/json")
    @ResponseBody
    public String getStockMultiple(@RequestBody StockNewDataVo stockNewDataVo) {

        //根据价格区间获取股票
        List<StockNewData> stockListByStockCode = getNominateList(stockNewDataVo);

        if (stockListByStockCode.size() <= 0) {  //无数据
            return null;
        }

        List<StockNominateTo> list = new ArrayList<>();

        if (!CrossType.NONE.toString().equals(stockNewDataVo.getCrossType())) {
            for (int i = 0; i < stockListByStockCode.size(); i++) {
                List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode(stockListByStockCode.get(i).getStockCode(), SortType.ASC.toString(), 30);
                Map<String, Object> checkResult = iStockInfoMacdServices.isExistCross(newStockListByStockCodelist, 10, stockNewDataVo.getCrossType());
                if ((boolean) checkResult.get("result")) {

                    StockNominateTo stockNominateTo = new StockNominateTo();
                    stockNominateTo.setStockCode(newStockListByStockCodelist.get(i).getStockCode());
                    stockNominateTo.setSpj(newStockListByStockCodelist.get(i).getSpj() + "");
                    stockNominateTo.setStockDate(checkResult.get("date").toString());
                    list.add(stockNominateTo);
                }
            }
        } else {
            for (int i = 0; i < stockListByStockCode.size(); i++) {
                StockNominateTo stockNominateTo = new StockNominateTo();
                stockNominateTo.setStockCode(stockListByStockCode.get(i).getStockCode());
                stockNominateTo.setSpj(stockListByStockCode.get(i).getSpj() + "");
                stockNominateTo.setStockDate("");
                list.add(stockNominateTo);
            }
        }
        String jsonStr = JSON.toJSONString(list);
        return jsonStr;
    }


    /***
     * 获取价格区间相关数据的 周期内的涨跌幅
     * @param stockNewDataVo
     * @return
     */
    @PostMapping(value = "/getIncrease", consumes = "application/json")
    @ResponseBody
    public String getNominateInfo(@RequestBody StockNewDataVo stockNewDataVo) {

        List<StockNewData> stockListByStockCode = new ArrayList<>();
        double zdf_1 = 0;
        double zdf_2 = 0;
        double zdf_3 = 0;
        double zdf_5 = 0;
        double zdf_10 = 0;
        double zdf_15 = 0;
        double zdf_30 = 0;
        double zdf_count = 0;

        //根据价格区间获取股票
        stockListByStockCode = getNominateList(stockNewDataVo);

        if (stockListByStockCode.size() <= 0) {  //无数据
            return null;
        }

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (int i = 0; i < stockListByStockCode.size(); i++) {
            ;
            List<StockInfo> newStockListByStockCodeList = iStockInfoServices.getNewStockListByStockCode(stockListByStockCode.get(i).getStockCode() + "", SortType.DESC.toString(), 30);
            Map<String, Object> zdfMap = getZdf(newStockListByStockCodeList);
            zdfMap.put("stockCode", stockListByStockCode.get(i).getStockCode());
            zdfMap.put("spj", stockListByStockCode.get(i).getSpj());
            resultList.add(zdfMap);
        }


        String jsonStr = JSON.toJSONString(resultList);
        return jsonStr.toString();
    }


    /***
     * 获取价格区间的股票
     * @param stockNewDataVo
     * @return
     */
    public List<StockNewData> getNominateList(StockNewDataVo stockNewDataVo) {
        List<StockNewData> list = iStockNewDataServices.getNewData(stockNewDataVo);
        return list;
    }


    public Map<String, Object> getZdf(List<StockInfo> list) {

        double zdf_0 = 0;
        double zdf_1 = 0;
        double zdf_2 = 0;
        double zdf_3 = 0;
        double zdf_5 = 0;
        double zdf_10 = 0;
        double zdf_15 = 0;
        double zdf_30 = 0;
        double zdf_count = 0;

        for (int i = 0; i < list.size(); i++) {


            zdf_count = zdf_count + list.get(i).getZdf();

            if (list.size() > 0 && i == 0) { //获取上一日 涨幅
                zdf_1 = list.get(i).getZdf();
                continue;
            }

            if (list.size() > 1 && i == 1) { //获取上两日 涨幅
                zdf_2 = list.get(i).getZdf();
                continue;
            }
            if (list.size() > 2 && i == 2) { //获取上三日 涨幅
                zdf_3 = list.get(i).getZdf();
                continue;
            }

            if (list.size() > 4 && i == 4) { //获取五日平均 涨幅
                zdf_5 = zdf_count / 5;
                continue;
            }
            if (list.size() > 9 && i == 9) {   //获取十日平均 涨幅
                zdf_10 = zdf_count / 10;
                continue;
            }

            if (list.size() > 14 && i == 14) { //获取十五日平均 涨幅
                zdf_15 = zdf_count / 15;
                continue;
            }

            if (list.size() > 29 && i == 29) {  //获取三十日平均 涨幅
                zdf_30 = zdf_count / 30;
                continue;
            }

        }

        Map<String, Object> map = new HashMap<>();
        map.put("zdf_1", zdf_1);
        map.put("zdf_2", zdf_2);
        map.put("zdf_3", zdf_3);
        map.put("zdf_5", zdf_5);
        map.put("zdf_10", zdf_10);
        map.put("zdf_15", zdf_15);
        map.put("zdf_30", zdf_30);
        return map;

    }


    /***
     * 获取详细交叉点的信息
     * @param
     * @return
     */
    @PostMapping(value = "/getCross", consumes = "application/json")
    @ResponseBody
    public String getCross(@RequestBody StockSearchVo stockSearchVo) {

        StockNewDataVo stockNewDataVo = new StockNewDataVo();
        BeanUtils.copyProperties(stockSearchVo, stockNewDataVo);

        List<StockNewData> newDatalist = iStockNewDataServices.getNewData(stockNewDataVo);
        if (newDatalist.size() < 1) {
            return "";
        }

        List<StockNominateTo> list = new ArrayList<>();
        for (int i = 0; i < newDatalist.size(); i++) {

            List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode(newDatalist.get(i).getStockCode(), SortType.ASC.toString(), stockSearchVo.getDayNum());

            Map<String, Object> checkResult = iStockInfoMacdServices.isExistCross(newStockListByStockCodelist, 10, stockSearchVo.getCrossType());
            if ((boolean) checkResult.get("result")) {
                StockNominateTo stockNominateTo = new StockNominateTo();
                stockNominateTo.setStockCode(newDatalist.get(i).getStockCode());
                stockNominateTo.setSpj(newDatalist.get(i).getSpj() + "");
                stockNominateTo.setZdf(newDatalist.get(i).getZdf() + "");
                stockNominateTo.setStockDate(checkResult.get("date").toString());

//                //获取该股票当前的涨跌的影响
//                Map<String, Object> resultMap = iStockAnalyzeIncreaseServices.getStockIncreaseEffect(newDatalist.get(i).getStockCode());
//                Map<String, Object> map = (Map<String, Object>) resultMap.get("resultMapOwn");
//                Map<String, Object> mapAll = (Map<String, Object>) resultMap.get("resultMapAll");



//
//                if (newDatalist.get(i).getZdf() >= 10) {
//                    stockNominateTo.setZdfEffect(map.get("increase10").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("increase10").toString());
//                } else if (newDatalist.get(i).getZdf() >= 8) {
//                    stockNominateTo.setZdfEffect(map.get("increase810").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("increase810").toString());
//                } else if (newDatalist.get(i).getZdf() >= 6) {
//                    stockNominateTo.setZdfEffect(map.get("increase68").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("increase68").toString());
//                } else if (newDatalist.get(i).getZdf() >= 4) {
//                    stockNominateTo.setZdfEffect(map.get("increase46").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("increase46").toString());
//                } else if (newDatalist.get(i).getZdf() >= 2) {
//                    stockNominateTo.setZdfEffect(map.get("increase24").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("increase24").toString());
//                } else if (newDatalist.get(i).getZdf() >= 0) {
//                    stockNominateTo.setZdfEffect(map.get("increase02").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("increase02").toString());
//                } else if (newDatalist.get(i).getZdf() >=-2) {
//                    stockNominateTo.setZdfEffect(map.get("descend02").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("descend02").toString());
//                } else if (newDatalist.get(i).getZdf() >= -4) {
//                    stockNominateTo.setZdfEffect(map.get("descend46").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("descend46").toString());
//                } else if (newDatalist.get(i).getZdf() >= -6) {
//                    stockNominateTo.setZdfEffect(map.get("descend68").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("descend68").toString());
//                } else if (newDatalist.get(i).getZdf() >= -8) {
//                    stockNominateTo.setZdfEffect(map.get("descend68").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("descend68").toString());
//                } else if (newDatalist.get(i).getZdf() >= -8) {
//                    stockNominateTo.setZdfEffect(map.get("descend810").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("descend810").toString());
//                } else {
//                    stockNominateTo.setZdfEffect(map.get("descend10").toString());
//                    stockNominateTo.setZdfEffectAll(mapAll.get("descend10").toString());
//                }
                list.add(stockNominateTo);
            }
        }

        String jsonStr = JSON.toJSONString(list);
        return jsonStr;
    }


    /***
     * 判断出现金叉或者死叉之后出 前几天出现上涨的概率
     * @param
     * @return
     */
    @PostMapping(value = "/getStockCrossEffect", consumes = "application/json")
    @ResponseBody
    public String getStockCrossEffect(@RequestBody StockSearchVo stockSearchVo) {
        Map<String, Object> resultMap = iStockAnalyzeMacdServices.getCrossEffect(stockSearchVo.getStockCode());
        String jsonStr = JSON.toJSONString(resultMap);
        return jsonStr;
    }


    /***
     * 判断出现金叉或者死叉之后出 前几天出现上涨的概率
     * @param
     * @return
     */
    @PostMapping(value = "/getStockCrossEffectNew", consumes = "application/json")
    @ResponseBody
    public String getStockCrossEffectNew(@RequestBody StockSearchVo stockSearchVo) {
        Map<String, Object> resultMap = iStockAnalyzeMacdServices.getCrossEffectNew(stockSearchVo.getStockCode(),Integer.valueOf(stockSearchVo.getEffectType()));
        String jsonStr = JSON.toJSONString(resultMap);
        return jsonStr;
    }


    /***
     * 判断出现金叉或者死叉之后出 前几天出现上涨的概率
     * @param
     * @return
     */
    @PostMapping(value = "/getStockCrossEffectNewFinal", consumes = "application/json")
    @ResponseBody
    public String getStockCrossEffectNewFinal(@RequestBody StockSearchVo stockSearchVo) {
        Map<String, Object> resultMap = iStockAnalyzeMacdServices.getStockCrossEffectNewFinal(stockSearchVo.getStockCode(),Integer.valueOf(stockSearchVo.getEffectType()));
        String jsonStr = JSON.toJSONString(resultMap);
        return jsonStr;
    }

}
