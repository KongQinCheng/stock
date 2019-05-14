package com.stock.services.impl;

import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockList;
import com.stock.dao.IStockInfoDao;
import com.stock.dao.IStockListDao;
import com.stock.services.IStockInfoServices;
import com.stock.services.IStockListServices;
import com.stock.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockListServicesImpl implements IStockListServices {

    @Autowired
    IStockListDao iStockListDao;

    @Autowired
    HtmlUtil htmlUtil;


    @Autowired
    IStockInfoServices iStockInfoServices  ;

    @Override
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
                    iStockListDao.addStockList(tempStockCode, tempStockName);
                }catch (Exception e){
                    continue;
                }
            }
        }
    }

    @Override
    public void getStockNewList() throws Exception {
        List<Map<String,String>> stockNewList = getStockNewListCode();
        for (int i = 0; i <stockNewList.size() ; i++) {
            if(!iStockListDao.isExitStockList(stockNewList.get(i).get("stockCode").toString())){
                //保存到列表中
                iStockListDao.addStockList(stockNewList.get(i).get("stockCode").toString(),stockNewList.get(i).get("stockName").toString());

                //查询股票相关的历史数据保存到新表中。
                iStockInfoServices.getStockInfoHistory(stockNewList.get(i).get("stockCode").toString());
            }
        }
    }

    @Override
    public List<StockList> getStockList() {
        return iStockListDao.getStockList();
    }

    @Override
    public List<StockList> getStockListLimit(int limit) {
        return iStockListDao.getStockListLimit(limit);
    }

    @Override
    public boolean isExitStockList(String stockCode) {
        return iStockListDao.isExitStockList(stockCode);
    }

    @Override
    public void addStockList(String stockCode, String stockName) {
         iStockListDao.addStockList(stockCode,stockName);
    }


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
}
