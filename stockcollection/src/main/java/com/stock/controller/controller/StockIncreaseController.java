package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.stock.bean.vo.AnalyzeIncreaseDayVo;
import com.stock.bean.vo.StockInfoVo;
import com.stock.dao.IStockAnalyzeIncreaseDao;
import com.stock.services.IStockAnalyzeIncreaseServices;
import com.stock.services.IStockAnalyzeMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("/stockIncrease")
public class StockIncreaseController {


    @Autowired
    IStockAnalyzeIncreaseServices  iStockAnalyzeIncreaseServices  ;



    @PostMapping(value = "/getStockIncreaseEffect",consumes = "application/json")
    @ResponseBody
    public String getStockMacd( @RequestBody StockInfoVo stockInfoVo  ){
        //todo
        Map<String, Object> stockIncreaseEffect = iStockAnalyzeIncreaseServices.getStockIncreaseEffect(stockInfoVo.getStockCode(),"0");
        String jsonStr = JSON.toJSONString( stockIncreaseEffect );
        return jsonStr;
    }



    @PostMapping(value = "/getStockIncreaseEffectDay",consumes = "application/json")
    @ResponseBody
    public String getStockIncreaseEffectDay( @RequestBody AnalyzeIncreaseDayVo analyzeIncreaseDayVo  ){
        Map<String, Object> stockIncreaseEffect = iStockAnalyzeIncreaseServices.getStockIncreaseEffect(analyzeIncreaseDayVo.getStockCode(),analyzeIncreaseDayVo.getEffectType());
        String jsonStr = JSON.toJSONString( stockIncreaseEffect );
        System.out.println(jsonStr);
        return jsonStr;
    }
}
