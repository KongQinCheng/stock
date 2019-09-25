package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockInfoHslServices;
import com.stock.services.IStockInfoKdjServices;
import com.stock.services.IStockInfoMacdServices;

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
public class StockHslController {

    @Autowired
    IStockInfoHslServices iStockInfoHslServices;

    @Autowired
    IStockInfoMacdServices iStockInfoMacdServices;

    @Autowired
    IStockInfoDao iStockInfoDao;

    /***
     * MACD值
     * @return
     */
    @RequestMapping("/toStockHsl")
    public String toStockMacd(){
        return "stock/stock_hsl";
    }


    @PostMapping(value = "/getStockHslValueRegion", consumes = "application/json")
    @ResponseBody
    public String getStockKdjValueRegion(@RequestBody StockNewDataVo stockNewDataVo) {

        List<StockNewData> stockListByStockCode = iStockInfoHslServices.getStockHslValueRegion(stockNewDataVo);
        String jsonStr = JSON.toJSONString(stockListByStockCode);
        return jsonStr.toString();
    }


    public static void main(String[] args)
    {
        /*
         Map<String,String> map=new HashMap<String,String>();
        Map 对象存入 用户名,密码,电话号码
         */

        Map<String,Object> map2=new HashMap<String,Object>();
        //Map 对象存入 用户名,密码,电话号码
        map2.put("username", "liyang");


        Map<String,Object> map=new HashMap<String,Object>();
        //Map 对象存入 用户名,密码,电话号码
        map.put("username", "liyang");
        map.put("pwd", map2 );
        map.put("telephone", "152232323");

        //Map 转成  JSONObject 字符串
        JSONObject jsonObj=new JSONObject(map);

        System.out.println(jsonObj.toString());

}


}
