package com.stock.controller.web;


import com.stock.bean.po.StockNewData;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.services.IStockNewDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/stock/stockNewDataController")
public class StockNewDataController {

@Autowired
    IStockNewDataServices iStockNewDataServices;

    public List<StockNewData> getStockNewDataListByVo(@RequestBody StockNewDataVo stockNewDataVo){
        //根据收盘价的区间，获取所有股票
        return iStockNewDataServices.getNewData(stockNewDataVo);
    }

}
