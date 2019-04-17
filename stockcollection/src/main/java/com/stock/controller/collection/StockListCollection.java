package com.stock.controller.collection;


import com.stock.bean.po.StockList;
import com.stock.services.IStockListServices;
import com.stock.util.HtmlUtil;
import com.stock.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StockListCollection {


    @Autowired
    IStockListServices iStockListServices;

    @Autowired
    StockInfoCollection stockInfoCollection;

    @Autowired
    HtmlUtil htmlUtil;

    /***
     * 循环获取 网易的股票列表
     */
    public  void getWycjStockList() throws Exception {

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
                    iStockListServices.addStockList(tempStockCode, tempStockName);
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
    public  List<StockList> getStockList()   {
        List<StockList> list  = iStockListServices.getStockList();
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


    /***
     * 获取最新上市的股票的信息
     */
    public  List<Map<String,String>> getStockNewListCode() throws Exception {

        List<Map<String,String>>  resultList= new ArrayList<>();

        String tempURL = "http://vip.stock.finance.sina.com.cn/corp/go.php/vRPD_NewStockIssue/page/1.phtml?page=1&cngem=0&orderBy=NetDate&orderType=desc";
        String html = htmlUtil.getHtmlByURL(tempURL, "GBK");

        //获取需要的正文
        html = htmlUtil.getHtmlByExpression("<table id=\"NewStockTable\">[\\s\\S]*</table>", html);

//        String  temphtml = htmlUtil.getHtmlByExpression("<thead>[\\s\\S]*</thead>", html);
//        html =html.replaceAll(temphtml,"");
        html =html.replaceAll(" class=\"tr_2\"","");

        html = htmlUtil.getHtmlByExpression("<tr>[\\s\\S]*</tr>", html);
        String[] strings = htmlUtil.splitByExpression("</tr>", html);

        String namehtml="";
        for (int i = 0; i < strings.length; i++) {

            html = strings[i];

            String[] strings2 = htmlUtil.splitByExpression("</td>", html);
            html = strings2[0];


            html = htmlUtil.getHtmlByExpression("<div align=\"center\">[\\s\\S]*</div>", html);
            html=html.replaceAll("<div align=\"center\">","").replaceAll("</div>","");
            if (html.length()!=6){
                continue;
            }
            Map<String,String> resultmap = new HashMap<>();
            resultmap.put("stockCode",html);

            namehtml = strings2[2];
            namehtml = htmlUtil.getHtmlByExpression("<div align=\"center\">[\\s\\S]*</div>", namehtml);
            namehtml=namehtml.replaceAll("<div[^>]*>", "");
            namehtml=namehtml.replaceAll("</div>", "");
            namehtml=namehtml.replaceAll("<a[^>]*>", "");
            namehtml=namehtml.replaceAll("</a>", "");
            namehtml=namehtml.replaceAll("\t", "");
            namehtml=namehtml.replaceAll(" ", "");
            resultmap.put("stockName",namehtml);
            resultList.add(resultmap);
        }
        return resultList;
    }


    public void getStockNewList() throws Exception {
        List<Map<String,String>> stockNewList = getStockNewListCode();
        for (int i = 0; i <stockNewList.size() ; i++) {
            if(!iStockListServices.isExitStockList(stockNewList.get(i).get("stockCode").toString())){
                //保存到列表中
                iStockListServices.addStockList(stockNewList.get(i).get("stockCode").toString(),stockNewList.get(i).get("stockName").toString());

                //查询股票相关的历史数据保存到新表中。
                stockInfoCollection.getWycjSituation(stockNewList.get(i).get("stockCode").toString());
            }
        }


    }

}
