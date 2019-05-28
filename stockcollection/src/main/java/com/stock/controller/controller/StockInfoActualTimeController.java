package com.stock.controller.controller;

import com.alibaba.fastjson.JSON;
import com.stock.bean.po.StockInfoActualtime;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockInfoActualtimeDao;
import com.stock.services.IStockInfoActualtimeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stock")
public class StockInfoActualTimeController {

    @Autowired
    IStockInfoActualtimeServices iStockInfoActualtimeServices ;

    /***
     * 判断出现金叉或者死叉之后出 前几天出现上涨的概率
     * @param
     * @return
     */
    @PostMapping(value = "/getStockActualTime", consumes = "application/json")
    @ResponseBody
    public String getStockActualTime(@RequestBody StockSearchVo stockSearchVo) {
        //删除表中当天的数据（计算实时的MACD的时候，添加进去的，数据不准）
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        List<StockInfoActualtime> byStockDate = iStockInfoActualtimeServices.getByStockDate(sdf2.format(day));
        String jsonStr = JSON.toJSONString(byStockDate);
        return jsonStr;
    }

}
