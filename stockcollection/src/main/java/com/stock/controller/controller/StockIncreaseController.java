package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockIncreaseAnalyze;
import com.stock.bean.po.StockInfo;
import com.stock.bean.vo.StockInfoVo;
import com.stock.controller.collection.StockIncreaseEffectCollection;
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
@RequestMapping("/stockIncrease")
public class StockIncreaseController {


    @Autowired
    StockIncreaseEffectCollection stockIncreaseEffectCollection;

    @PostMapping(value = "/getStockIncreaseEffect",consumes = "application/json")
    @ResponseBody
    public String getStockMacd( @RequestBody StockInfoVo stockInfoVo  ){
        String stockIncreaseEffect = stockIncreaseEffectCollection.getStockIncreaseEffect(stockInfoVo.getStockCode());
        return stockIncreaseEffect;
    }
}
