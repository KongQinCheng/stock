package com.stock.controller.web;


import com.alibaba.fastjson.JSON;
import com.stock.bean.po.StockInfo;
import com.stock.bean.vo.StockNewDataVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stock.controller.collection.StockNominateCollection.getStockListByStockNominateVo;


@Controller
public class NominateController {

    DecimalFormat df = new DecimalFormat("#.00");


    @PostMapping(value = "/getNomainate",consumes = "application/json")
    @ResponseBody
    public String getStockMacd( @RequestBody StockNewDataVo stockNewDataVo  ){

        List<StockInfo> stockListByStockCode = new ArrayList<>();


        double zdf_1 =0;
        double zdf_2 =0;
        double zdf_3 =0;
        double zdf_5 =0;
        double zdf_10 =0;
        double zdf_15 =0;
        double zdf_30 =0;

        double zdf_count=0;

        //根据价格区间获取股票
        stockListByStockCode = getStockListByStockNominateVo(stockNewDataVo);

        if (stockListByStockCode ==null ||stockListByStockCode.size()<=0){  //无数据
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
}
