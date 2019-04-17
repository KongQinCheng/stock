package com.stock.controller.web;


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
     * MACD值
     * @return
     */
    @RequestMapping("/toStockMacd")
    public String toStockMacd(){
        return "stock/stock_macd";
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
    @RequestMapping("/toNominateCross")
    public String toNominateCross(String searchType){
        return "nominate/stock_nominate_cross";
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



}
