package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockInfoKdjServices;
import com.stock.services.IStockInfoMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/stock")
public class StockKdjController {

    @Autowired
    IStockInfoKdjServices iStockInfoKdjServices;

    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;

    @Autowired
    IStockInfoDao iStockInfoDao;

    /***
     * MACD值
     * @return
     */
    @RequestMapping("/toStockKdjCross")
    public String toStockMacd(){
        return "stock/stock_kdj_cross";
    }

    @RequestMapping("/toStockKdjRegion")
    public String toStockKdjRegion(){
        return "stock/stock_kdj_region";
    }

    @RequestMapping("/toStockKdjRegionCross")
    public String toStockKdjRegionCross(){
        return "stock/stock_kdj_region_cross";
    }


    @RequestMapping("/toStockKdjRSIRegion")
    public String toStockKdjRSIRegion(){
        return "stock/stock_macd_kdj_rsi";
    }

    @RequestMapping("/toStockKdjCciRegion")
    public String toStockKdjCciRegion(){
        return "stock/stock_kdj_cci";
    }


    @RequestMapping("/toStockKdjCciRegionActualtime")
    public String toStockKdjCciRegionActualtime(){
        return "stock/stock_kdj_cci_actualtime";
    }



    @PostMapping(value = "/getStockKdjCross", consumes = "application/json")
    @ResponseBody
    public String getStockKdjCross(@RequestBody StockSearchVo stockSearchVo) {
        List<Map<String, Object>> stockListByStockCode = iStockInfoKdjServices.getKdjCrossAll(stockSearchVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }

    @PostMapping(value = "/getStockKdjValueRegion", consumes = "application/json")
    @ResponseBody
    public String getStockKdjValueRegion(@RequestBody StockNewDataVo stockNewDataVo) {

        List<StockNewData> stockListByStockCode = iStockInfoKdjServices.getStockKdjValueRegion(stockNewDataVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }


    @PostMapping(value = "/getStockKdjRSIValueRegion", consumes = "application/json")
    @ResponseBody
    public String getStockKdjRSIValueRegion(@RequestBody StockNewDataVo stockNewDataVo) {
        List<StockNewData> stockListByStockCode = iStockInfoKdjServices.getStockKdjValueRegion(stockNewDataVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }


    @PostMapping(value = "/getStockKdjCciValueRegion", consumes = "application/json")
    @ResponseBody
    public String getStockKdjCciValueRegion(@RequestBody StockNewDataVo stockNewDataVo) {
        List<StockNewData> stockListByStockCode = iStockInfoKdjServices.getStockKdjValueRegion(stockNewDataVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }

    @PostMapping(value = "/getStockKdjCciValueRegionActualtime", consumes = "application/json")
    @ResponseBody
    public String getStockKdjCciValueRegionActualtime(@RequestBody StockNewDataVo stockNewDataVo) {
        List<StockNewData> stockListByStockCode = iStockInfoKdjServices.getStockKdjValueRegionActualtime(stockNewDataVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }





    @PostMapping(value = "/getStockKdjValueRegionCross", consumes = "application/json")
    @ResponseBody
    public String getStockKdjValueRegionCross(@RequestBody StockNewDataVo stockNewDataVo) {
        stockNewDataVo.setKvaluemin(0.01);
        stockNewDataVo.setKvaluemax(20);

        stockNewDataVo.setDvaluemin(0.01);
        stockNewDataVo.setDvaluemax(20);

        stockNewDataVo.setJvaluemin(0.01);
        stockNewDataVo.setJvaluemax(20);

        List<StockNewData> stockListByStockCode = iStockInfoKdjServices.getStockKdjValueRegion(stockNewDataVo);
        List<StockNewData> resultList =new ArrayList<>();

        for (int i = 0; i <stockListByStockCode.size() ; i++) {
            List<StockInfo> newStockListByStockCodelist = iStockInfoDao.getNewStockListByStockCode(stockListByStockCode.get(i).getStockCode(), SortType.ASC.toString(), 5);
            Map<String, Object> existCross = iStockInfoMacdServices.isExistCross(newStockListByStockCodelist, 5, "11");
            if ((boolean)existCross.get("result")){
                resultList.add(stockListByStockCode.get(i));
                continue;
            }

            Map<String, Object> existCross1 = iStockInfoMacdServices.isExistCross(newStockListByStockCodelist, 5, "10");
            if ((boolean)existCross1.get("result")){
                resultList.add(stockListByStockCode.get(i));
                continue;
            }
        }

        String jsonStr = JSON.toJSONString(resultList);
        return jsonStr.toString();
    }
}
