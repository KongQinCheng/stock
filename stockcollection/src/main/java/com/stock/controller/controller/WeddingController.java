package com.stock.controller.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.dao.IStockInfoDao;
import com.stock.services.IStockInfoHslServices;
import com.stock.services.IStockInfoMacdServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/wedding/aCai")
public class WeddingController {


    /***
     * MACD值
     * @return
     */
    @RequestMapping("/toWedding")
    public String toStockMacd(){
        return "wedding/index";
    }


}
