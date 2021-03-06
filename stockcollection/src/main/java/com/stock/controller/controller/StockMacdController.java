package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.vo.StockInfoVo;
import com.stock.dao.IStockInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/stock")
public class StockMacdController {

    @Autowired
    IStockInfoDao iStockInfoDao;


    /***
     * MACD值
     * @return
     */
    @RequestMapping("/toStockMacd")
    public String toStockMacd(){
        return "stock/stock_macd";
    }



    @PostMapping(value = "/getStockMacd", consumes = "application/json")
    @ResponseBody
    public String getStockMacd(@RequestBody StockInfoVo stockInfoVo) {
        List<StockInfo> stockListByStockCode = new ArrayList<>();
        stockListByStockCode = iStockInfoDao.getNewStockListByStockCode(stockInfoVo.getStockCode(), SortType.ASC.toString(), stockInfoVo.getLimitNum());
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }
}
