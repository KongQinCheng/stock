package com.stock.controller.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stock.Enum.CrossType;
import com.stock.Enum.SortType;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockNewData;
import com.stock.bean.to.StockNominateTo;
import com.stock.bean.vo.StockNewDataVo;
import com.stock.bean.vo.StockSearchVo;
import com.stock.dao.IStockNewDataDao;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockNewDataServices;
import com.stock.services.IStockNoninateServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    IStockNewDataDao iStockNewDataDao;

    @RequestMapping("/toNominateIndex")
    public String toNominateIndex(){
        return "nominate/stock_nominate_index";
    }

    @RequestMapping("/toNominateInfo")
    public String toNominateInfo(){
        return "nominate/stock_nominate_info";
    }

    @RequestMapping("/toNominateCross")
    public String toNominateCross(String searchType){
        return "nominate/stock_nominate_cross";
    }



    /***
     * 获取价格区间相关数据的 周期内的涨跌幅
     * @param stockNewDataVo
     * @return
     */
    @PostMapping(value = "/getNominateInfo",consumes = "application/json")
    @ResponseBody
    public String getNominateInfo( @RequestBody StockNewDataVo stockNewDataVo){

        List<StockNewData> stockListByStockCode = new ArrayList<>();
        double zdf_1 =0;
        double zdf_2 =0;
        double zdf_3 =0;
        double zdf_5 =0;
        double zdf_10 =0;
        double zdf_15 =0;
        double zdf_30 =0;
        double zdf_count=0;

        //根据价格区间获取股票
        stockListByStockCode = getNominateList(stockNewDataVo);

        if (stockListByStockCode.size()<=0){  //无数据
            return null;
        }

        for (int i = 0; i < stockListByStockCode.size(); i++) {
            zdf_count =zdf_count+stockListByStockCode.get(i).getZdf();

            if (stockListByStockCode.size()>0 &&i==0){ //获取上一日 涨幅
                zdf_1 =stockListByStockCode.get(i).getZdf();
                continue;
            }

            if (stockListByStockCode.size()>1 &&i==1){ //获取上两日 涨幅
                zdf_2 =stockListByStockCode.get(i).getZdf();
                continue;
            }
            if (stockListByStockCode.size()>2 &&i==2){ //获取上三日 涨幅
                zdf_3 =stockListByStockCode.get(i).getZdf();
                continue;
            }

            if (stockListByStockCode.size()>4 &&i==4){ //获取五日平均 涨幅
                zdf_5 = zdf_count/5;
                continue;
            }
            if (stockListByStockCode.size()>9 &&i==9){   //获取十日平均 涨幅
                zdf_10 = zdf_count/10;
                continue;
            }

            if (stockListByStockCode.size()>14 &&i==14){ //获取十五日平均 涨幅
                zdf_15 = zdf_count/15;
                continue;
            }

            if (stockListByStockCode.size()>29 &&i==29){  //获取三十日平均 涨幅
                zdf_30 = zdf_count/30;
                continue;
            }
        }

        Map<String,Object> map =new HashMap<>();
        map.put("zdf_1",zdf_1);
        map.put("zdf_2",zdf_2);
        map.put("zdf_3",zdf_3);
        map.put("zdf_5",zdf_5);
        map.put("zdf_10",zdf_10);
        map.put("zdf_15",zdf_15);
        map.put("zdf_30",zdf_30);
        String jsonStr = JSON.toJSONString( map );
        return  jsonStr.toString();
    }


    /***
     * 获取价格区间的股票
     * @param stockNewDataVo
     * @return
     */
    public List<StockNewData> getNominateList(StockNewDataVo stockNewDataVo){
        List<StockNewData> list = iStockNewDataServices.getNewData(stockNewDataVo);
        return  list;
    }


    /***
     * 获取详细交叉点的信息
     * @param
     * @return
     */
    @PostMapping(value = "/getNominateCross",consumes = "application/json")
    @ResponseBody
    public String getNomainateCross( @RequestBody StockSearchVo stockSearchVo  ){

        StockNewDataVo stockNewDataVo =new StockNewDataVo();
        BeanUtils.copyProperties(stockSearchVo,stockNewDataVo);

        List<StockNewData> newDatalist = iStockNewDataServices.getNewData(stockNewDataVo);
        if (newDatalist.size()<1){
            return "";
        }

        List<StockNominateTo> list =new ArrayList<>();
        for (int i = 0; i <newDatalist.size(); i++) {

            List<StockInfo> newStockListByStockCodelist = iStockInfoServices.getNewStockListByStockCode(newDatalist.get(i).getStockCode(), SortType.ASC.toString(), stockSearchVo.getDayNum());

            Map<String,Object>  checkResult = iStockNoninateServices.isExitCross(newStockListByStockCodelist, 10,stockSearchVo.getCrossType());
            if ((boolean)checkResult.get("result")){
                StockNominateTo stockNominateTo=new  StockNominateTo();
                stockNominateTo.setStockCode(newDatalist.get(i).getStockCode());
                stockNominateTo.setSpj(newDatalist.get(i).getSpj()+"");
                stockNominateTo.setStockDate(checkResult.get("date").toString());
                list.add(stockNominateTo);
            }
        }

        String jsonStr = JSON.toJSONString( list );
        return jsonStr;
    }

}
