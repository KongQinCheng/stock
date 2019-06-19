package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.vo.StockInfoVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockInfoKdjServices;
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


    /***
     * MACD值
     * @return
     */
    @RequestMapping("/toStockKdjCross")
    public String toStockMacd(){
        return "stock/stock_kdj_cross";
    }



    @PostMapping(value = "/getStockKdjCross", consumes = "application/json")
    @ResponseBody
    public String getStockKdjCross(@RequestBody StockSearchVo stockSearchVo) {
        List<Map<String, Object>> stockListByStockCode = iStockInfoKdjServices.getKdjCrossAll(stockSearchVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }
}
