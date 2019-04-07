package com.stock.controller.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class shareTest{

    public static void main(String[] args)  throws Exception{


        List<String> urLbyStockCode = getURLbyStockCode("600036");
        for (int i = 0; i <urLbyStockCode.size() ; i++) {

            String html= getHtmlByURL("http://quotes.money.163.com/trade/lsjysj_600036.html?year=2017&season=1");

            //获取需要的正文
            html= getHtmlByExpression("</thead[\\s\\S]*</tr>    </table>",html);

            html= replaceHtml(" class='cGreen'",html);
            html= replaceHtml(" class='cRed'",html);

            html= getHtmlByExpression("<td>(.*?)</td>",html);

            String[] strings = splitByExpression("</td>", html);

            getBean(strings);
        }



    }

    public static List<String>  getURLbyStockCode(String StockCode) throws ParseException {

        String tempURL="";
        List<String> urlList =new ArrayList<>();
        for (int year = 2002; year <2020 ; year++) {
            for (int season = 1; season <5 ; season++) {
                tempURL="http://quotes.money.163.com/trade/lsjysj_"+StockCode+".html?year="+year+"&season="+season;
                urlList.add(tempURL);
            }
        }
        return  urlList;
    }



    public static String insertDb(ShareBean shareBean ) throws ParseException {

        System.out.println(shareBean.toString());
        System.out.println("-------------");
        return  shareBean.toString();
    }

    public static String getBean(String[] strArray ) throws ParseException {

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        ShareBean shareBean =new  ShareBean();
        for (int i = 0; i <strArray.length ; i++) {
             String tempStr= strArray[i].replace("<td>","").replace("</td>","");

             int modInt =i%11;
             switch (modInt){
                 case 0:
                     shareBean.setShareDate(format1.parse(tempStr));
                     break;
                 case 1:
                     shareBean.setKpj(tempStr);
                     break;
                 case 2:
                     shareBean.setZgj(tempStr);
                     break;
                 case 3:
                     shareBean.setZdj(tempStr);
                     break;
                 case 4:
                     shareBean.setSpj(tempStr);
                     break;
                 case 5:
                     shareBean.setZde(tempStr);
                     break;
                 case 6:
                     shareBean.setZdf(tempStr);
                     break;
                 case 7:
                     shareBean.setCjl(tempStr);
                     break;
                 case 8:
                     shareBean.setCjje(tempStr);
                     break;
                 case 9:
                     shareBean.setZf(tempStr);
                     break;
                 case 10:
                     shareBean.setHsl(tempStr);
                     insertDb(shareBean);
                     break;

             }
        }


        return "";
    }
    static class  ShareBean{
        private String StockCode ;  //代码
        private String shareName;   //名称
        private Date   shareDate;  //时间
        private String  kpj ;  //开盘价
        private String  zgj ;  //最高价
        private String  zdj ;  //最低价
        private String  spj ;  //收盘价
        private String  zde ;  //涨跌额
        private String  zdf ;  //涨跌幅(%)
        private String  cjl ;  //成交量(手)
        private String  cjje ;  //成交金额(万元)
        private String  zf ;  //振幅(%)
        private String  hsl ;  //换手率(%)

        public String getStockCode() {
            return StockCode;
        }

        public void setStockCode(String StockCode) {
            this.StockCode = StockCode;
        }

        public String getShareName() {
            return shareName;
        }

        public void setShareName(String shareName) {
            this.shareName = shareName;
        }

        public Date getShareDate() {
            return shareDate;
        }

        public void setShareDate(Date shareDate) {
            this.shareDate = shareDate;
        }

        public String getKpj() {
            return kpj;
        }

        public void setKpj(String kpj) {
            this.kpj = kpj;
        }

        public String getZgj() {
            return zgj;
        }

        public void setZgj(String zgj) {
            this.zgj = zgj;
        }

        public String getZdj() {
            return zdj;
        }

        public void setZdj(String zdj) {
            this.zdj = zdj;
        }

        public String getSpj() {
            return spj;
        }

        public void setSpj(String spj) {
            this.spj = spj;
        }

        public String getZde() {
            return zde;
        }

        public void setZde(String zde) {
            this.zde = zde;
        }

        public String getZdf() {
            return zdf;
        }

        public void setZdf(String zdf) {
            this.zdf = zdf;
        }

        public String getCjl() {
            return cjl;
        }

        public void setCjl(String cjl) {
            this.cjl = cjl;
        }

        public String getCjje() {
            return cjje;
        }

        public void setCjje(String cjje) {
            this.cjje = cjje;
        }

        public String getZf() {
            return zf;
        }

        public void setZf(String zf) {
            this.zf = zf;
        }

        public String getHsl() {
            return hsl;
        }

        public void setHsl(String hsl) {
            this.hsl = hsl;
        }

        @Override
        public String toString() {
            return "ShareBean{" +
                    "StockCode='" + StockCode + '\'' +
                    ", shareName='" + shareName + '\'' +
                    ", shareDate=" + shareDate +
                    ", kpj='" + kpj + '\'' +
                    ", zgj='" + zgj + '\'' +
                    ", zdj='" + zdj + '\'' +
                    ", spj='" + spj + '\'' +
                    ", zde='" + zde + '\'' +
                    ", zdf='" + zdf + '\'' +
                    ", cjl='" + cjl + '\'' +
                    ", cjje='" + cjje + '\'' +
                    ", zf='" + zf + '\'' +
                    ", hsl='" + hsl + '\'' +
                    '}';
        }
    }



    public static String[] splitByExpression(String expression ,String html)   {

        StringBuilder htmlStr =new StringBuilder();
        String[] resultArray = html.split(expression);
//        <td>2017-03-31</td><td>19.02</td><td>19.25</td><td>19.02</td><td>19.17</td><td>0.15</td><td>0.79</td><td>339,623</td><td>65,095</td><td>1.21</td><td>0.16</td>
        return resultArray;
    }



    public static String replaceHtml(String oldChar ,String html)   {
        StringBuilder htmlStr =new StringBuilder();
        html=html.replace(oldChar,"");
        System.out.println(html);
        System.out.println("--------");
        return html;
    }



    public static String getHtmlByExpression(String expression ,String html)   {

        StringBuilder htmlStr =new StringBuilder();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(html);
        while(matcher.find()) {
            htmlStr.append(matcher.group());
        }
        System.out.println(htmlStr.toString());
        System.out.println("------------");
        return htmlStr.toString();
    }
    public static String getHtmlByURL(String u) throws Exception {

        StringBuilder htmlStr =new StringBuilder();
        URL url = new URL(u);// 根据链接（字符串格式），生成一个URL对象

        HttpURLConnection urlConnection = (HttpURLConnection) url
                .openConnection();// 打开URL

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), "UTF-8"));// 得到输入流，即获得了网页的内容
        String line; // 读取输入流的数据，并显示
        while ((line = reader.readLine()) != null) {
            htmlStr.append(line);
        }
        System.out.println(htmlStr.toString());
        System.out.println("------------------");
        return  htmlStr.toString();
    }
}
