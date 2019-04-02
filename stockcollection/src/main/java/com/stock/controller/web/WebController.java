package com.stock.controller.web;


import com.alibaba.fastjson.JSON;
import com.stock.bean.StockInfo;
import com.stock.bean.StockInfoVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static com.stock.controller.collection.StockKdjCollection.*;



@Controller
public class WebController {

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/toMain")
    public String toMain(){
        return "main";
    }

    @RequestMapping("/toEma")
    public String toEma(){
        return "ema";
    }

    @RequestMapping("/toTest")
    public String toTest(){
        return "macd";
    }


    @RequestMapping("/toMacd")
    public String toMacd(){
        return "macd";
    }


    @RequestMapping("/toNominate")
    public String toNominate(){
        return "nominate";
    }




    @PostMapping(value = "/getStockMacd",consumes = "application/json")
    @ResponseBody
    public String getStockMacd( @RequestBody StockInfoVo stockInfoVo  ){

        List<StockInfo> stockListByShareCode = new ArrayList<>();
        stockListByShareCode = getStockListByShareCode(stockInfoVo.getStockCode());

        String jsonStr = JSON.toJSONString( stockListByShareCode );

        return  jsonStr.toString();
    }
}
