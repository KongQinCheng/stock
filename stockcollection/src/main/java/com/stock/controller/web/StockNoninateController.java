package com.stock.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stock.Enum.CrossType;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.to.StockNimateTo;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockNewDataServices;
import com.stock.services.IStockNoninateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class StockNoninateController {

   @Autowired
    IStockInfoServices iStockInfoServices;

    @Autowired
    IStockNewDataServices iStockNewDataServices;

    @Autowired
    IStockNoninateServices iStockNoninateServices;

    public static void main(String[] args)  throws Exception{

        String stockCode ="603383";
//        getNewInfoToTable(stockCode,30);
    }

    @PostMapping(value = "/getNomainateCross",consumes = "application/json")
    @ResponseBody
    public String getNomainateCross( @RequestBody StockNewDataVo stockNewDataVo  ){
        List<StockNewData> newDatalist = iStockNewDataServices.getNewData(stockNewDataVo);
        if (newDatalist.size()<1){
            return "";
        }

        List<StockNimateTo> list =new ArrayList<>();
        for (int i = 0; i <newDatalist.size(); i++) {

                        List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode("603383", SortType.ASC.toString(), stockNewDataVo.getDayNum());

//            List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode(newDatalist.get(i).getStockCode(), SortType.ASC.toString(), stockNewDataVo.getDayNum());

            Map<String,Object>  checkResult = iStockNoninateServices.isExitCross(newStockListByStockCodelist, 10,CrossType.GOLD_CROSS.toString());
            if ((boolean)checkResult.get("result")){
                StockNimateTo stockNimateTo=new  StockNimateTo();
                stockNimateTo.setStockCode(newDatalist.get(i).getStockCode());
                stockNimateTo.setSpj(newDatalist.get(i).getSpj()+"");
                stockNimateTo.setStockDate(checkResult.get("date").toString());
                list.add(stockNimateTo);
            }
        }

        String jsonStr = JSON.toJSONString( list );
        return jsonStr;
    }


    @PostMapping(value = "/getNomainateCrossInfo",consumes = "application/json")
    @ResponseBody
    public String getNomainateCrossInfo( @RequestBody StockNewDataVo stockNewDataVo  ){

        List<StockNewData> newDatalist = iStockNewDataServices.getNewData(stockNewDataVo);
        if (newDatalist.size()<1){
            return "";
        }
        for (int i = 0; i <newDatalist.size(); i++) {

            List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode(newDatalist.get(i).getStockCode(), SortType.ASC.toString(), stockNewDataVo.getDayNum());

            List<Map<String, String>> stockNoninateCross = iStockNoninateServices.getStockNoninateCross(newStockListByStockCodelist, 10);

        }
        return "";

    }

}
