package com.stock.controller.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stock")
public class WebStockController {

    /***
     * 首页
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "stock/index";
    }

    /***
     * 跳转到导航页面 页面内容包括价格曲线，MACD值，金叉和死叉
     * @return
     */
    @RequestMapping("/toStockInfoList")
    public String toStockInfoList(){
        return "stock/stock_info_list";
    }

    /**
     * 价格曲线图
     * @return
     */
    @RequestMapping("/toStockPrice")
    public String toStockPrice(){
        return "stock/stock_price";
    }



    /***
     * 新股列表
     * @return
     */
    @RequestMapping("/toStockNewList")
    public String toStockNewList(){
        return "stock/stock_new_list";
    }

    /***
     * 金叉和死叉
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockCross")
    public String toStockCross(String searchType){
        return "stock/stock_cross";
    }




    /***
     * 查看涨跌幅
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockZdf")
    public String toStockZdf(String searchType){
        return "stock/stock_zdf";
    }


    /***
     * 查看涨跌幅 影响
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockIncreaseEffect")
    public String toStockIncreaseEffect(String searchType){
        return "stock/stock_increase_effect";
    }


    /***
     * 查看涨跌幅 影响
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockIncreaseEffectDay")
    public String toStockIncreaseEffectDay(String searchType){
        return "stock/stock_increase_effect_day";
    }

    @RequestMapping("/toStockIncreaseEffectDay2")
    public String toStockIncreaseEffectDay2(String searchType){
        return "stock/stock_increase_effect_day2";
    }





    /***
     * 查看涨跌幅 影响
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockCrossEffect")
    public String toStockCrossEffect(String searchType){
        return "stock/stock_cross_effect";
    }



    /***
     * 查看出现金叉时候 涨幅的区间
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockCrossIncreaseEffect")
    public String toStockCrossIncreaseEffect(String searchType){
        return "stock/stock_cross_increase_effect";
    }


    /***
     * 查看出现金叉时候 涨幅的区间
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockCrossIncreaseEffectNew")
    public String toStockCrossIncreaseEffectNew(String searchType){
        return "stock/stock_cross_increase_effect_new";
    }


    /***
     * 查看出现金叉时候 涨幅的区间
     * @param searchType
     * @return
     */
    @RequestMapping("/toStockAnalyze")
    public String toStockAnalyze(String searchType){
        return "stock/stock_analyze";
    }
}
