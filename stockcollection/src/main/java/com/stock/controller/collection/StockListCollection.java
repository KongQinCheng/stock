package com.stock.controller.collection;


import com.stock.bean.po.StockList;
import com.stock.mapper.StockListMapper;
import com.stock.util.HtmlUtil;
import com.stock.util.SpringUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class StockListCollection {

    static HtmlUtil htmlUtil = new HtmlUtil();

    static StockListMapper stockListMapper = SpringUtil.getBean(StockListMapper.class);


    /***
     * 循环获取 网易的股票列表
     */
    public static void getWycjStockList() throws Exception {

        for (int i = 0; i < 94; i++) {

            String tempURL = "http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p=" + i + "&sr_p=-1";

            String html = htmlUtil.getHtmlByURL(tempURL, "GBK");

            //获取需要的正文
            html = htmlUtil.getHtmlByExpression("<table class=\"list_table\">[\\s\\S]*</table>", html);

            html = htmlUtil.getHtmlByExpression("<tr >[\\s\\S]*</tr>", html);

            html = htmlUtil.replaceHtml("class=\"blue\"", html);

            String[] strings = htmlUtil.splitByExpression("</tr>", html);


            for (int j = 0; j < strings.length; j++) {
                html = strings[j];

                String[] strings2 = htmlUtil.splitByExpression("</td>", html);

                String tempStockCode = strings2[0];
                String tempStockName = strings2[1];

                tempStockCode = tempStockCode.replaceAll("\\<.*?>", "").replaceAll("\t","").replaceAll(" ","");  //去掉所有HTML标签
                tempStockName = tempStockName.replaceAll("\\<.*?>", "");  //去掉所有HTML标签

                try {
                    stockListMapper.addStockList(tempStockCode, tempStockName);
                }catch (Exception e){
                    continue;
                }
            }

        }

    }


    /***
     * 获取 具体的 股票的历史信息
     * @param StockCode
     * @return
     * @throws ParseException
     */
    public static List<String> getURLbyStockCode(String StockCode) throws ParseException {

        String tempURL = "";
        List<String> urlList = new ArrayList<>();
        for (int year = 2002; year < 2020; year++) {
            for (int season = 1; season < 5; season++) {
                tempURL = "http://quotes.money.163.com/trade/lsjysj_" + StockCode + ".html?year=" + year + "&season=" + season;
                urlList.add(tempURL);
            }
        }
        return urlList;
    }


    /***
     * 获取数据库中 股票的列表
     * @return
     */
    public static List<StockList> getStockList()   {
        List<StockList> list  = stockListMapper.getStockList();
        return list;
    }


    /***
     * 删除股票列表中未上市的股票信息
     */
    public static void delStockList()   {

        List<StockList> stockList =new ArrayList<>();
        String[] arr={""};
        for (int i = 0; i < arr.length; i++) {
            StockList stockList1 =new StockList();
            stockList1.setStockCode(arr[i]);
            stockList.add(stockList1) ;
        }

        String resuStr ="";
        for (int i = 0; i <stockList.size() ; i++) {

            resuStr+= "delete  from  stock_list where stockCode ="+stockList.get(i).getStockCode() +";";
        }
        System.out.println(resuStr);
    }





}
