package com.stock.controller.collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stock.bean.po.WebDiary;
import com.stock.services.IWebDiaryServices;
import com.stock.util.HtmlUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class WeiboCollection {

    @Autowired
    HtmlUtil htmlUtil;


    @Autowired
    IWebDiaryServices iWebDiaryServices;


    /***
     * 循环获取微博信息
     */
    public List<Map<String, String>> getDiaryAll() throws Exception {

//        String xinlanUid[]= {"3269067024","7002995970"};
        String since_id = getDiary("3269067024", "4359981349864609", "1" ,"");


        return null;
    }

    int count = 0;

    public String getDiary(String uid, String since_id, String type,String person) throws Exception {

        String temp_since_id = "";
        String tempURL = "";
        if ("0".equals(type)) {
            tempURL = "https://m.weibo.cn/api/container/getIndex?type=uid&value=" + uid + "&containerid=107603" + uid;

        }
        String html = getHtmlByURL(tempURL, "GBK");
        JSONObject jsonObject = JSON.parseObject(html);
        if ("1".equals(jsonObject.get("ok").toString())) {
            JSONObject jsonObject_data = JSON.parseObject(jsonObject.get("data").toString());
            JSONArray jsonArray = JSON.parseArray(jsonObject_data.get("cards").toString());

            for (int i = 0; i < jsonArray.size(); i++) {
//                System.out.println(jsonArray.get(i).toString());
                JSONObject jsonObject_card = JSON.parseObject(jsonArray.get(i).toString());
                if ("9".equals(jsonObject_card.get("card_type").toString())) {

                    temp_since_id = jsonObject_card.get("itemid").toString().split("_-_")[1];
                    JSONObject jsonObject_info = JSON.parseObject(jsonObject_card.get("mblog").toString());

                    String text = "";
                    String created_at = "";
                    String bmiddle_pic = "";

                    if (jsonObject_info.containsKey("text")) {
                        text = jsonObject_info.get("text").toString();
                        text=text.replaceAll("'","\"");


                        if (text.indexOf("全文</a>")>-1){
                            String href= htmlUtil.getHtmlByExpression("<a href=\"/status/(.*?)全文</a>",text);
                            href=href.replace("<a href=\"","");
                            href=href.replace("\">全文</a>","");
                            href="http://m.weibo.cn"+href;
                            text = getDetailInfo(href);
                            text=text.replaceAll("'","\"");
                        }

                        if (text.indexOf("302 Found")>-1){
                            continue;
                        }
                    }

                    if (jsonObject_info.containsKey("created_at")) {
                        created_at = jsonObject_info.get("created_at").toString();

                        if (created_at.indexOf("分钟前")>-1||created_at.indexOf("刚刚")>-1||created_at.indexOf("小时前")>-1||created_at.indexOf("昨天")>-1){
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                            Date befor_day = null;
                            Calendar c = Calendar.getInstance();
                            if (created_at.indexOf("刚刚")>-1){
                                c.add(Calendar.SECOND, 1);
                                befor_day = c.getTime();
                            }
                            if (created_at.indexOf("分钟前")>-1){
                                String tempStr=created_at.replaceAll("分钟前","");
                                int num = - Integer.valueOf(tempStr);
                                c.add(Calendar.MINUTE, num);
                                befor_day = c.getTime();
                            }
                            if (created_at.indexOf("小时前")>-1){
                                String tempStr=created_at.replaceAll("小时前","");
                                int num = - Integer.valueOf(tempStr);
                                c.add(Calendar.HOUR, num);
                                 befor_day = c.getTime();
                            }
                            if (created_at.indexOf("昨天")>-1){
                                c.add(Calendar.DAY_OF_MONTH, -1);
                                befor_day = c.getTime();
                            }
                            created_at=sdf2.format(befor_day);
                        }else{
                            created_at="2019-"+created_at;
                        }

                        created_at=created_at.substring(0,10);

                    }
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date dt1 = df.parse(created_at);
                    Date dt2 = df.parse("2019-04-18");
                    if (dt1.getTime()>dt2.getTime()){
                        if (!iWebDiaryServices.isExitByText(text,created_at)){
                            if (jsonObject_info.containsKey("bmiddle_pic")) {
                                bmiddle_pic = jsonObject_info.get("bmiddle_pic").toString();
                            }
                            insertDiary( created_at, bmiddle_pic, text,person);
                        }
                    }
                }
            }
        }
        return temp_since_id;
    }


    public  String getHtmlByURL(String u, String charsetName) throws Exception {

        StringBuilder htmlStr = new StringBuilder();
//        System.out.println("");
        URL url = new URL(u);// 根据链接（字符串格式），生成一个URL对象

        HttpURLConnection urlConnection = (HttpURLConnection) url
                .openConnection();// 打开URL


        urlConnection.setRequestProperty("Accept", "application/json, text/plain, */*");
        urlConnection.setRequestProperty("MWeibo-Pwa", "1");

        urlConnection.setRequestProperty("Referer", "http://m.weibo.cn/u/3269067024");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        urlConnection.setRequestProperty("X-XSRF-TOKEN", "7fb321");


        BufferedReader reader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), charsetName));// 得到输入流，即获得了网页的内容
        String line; // 读取输入流的数据，并显示
        while ((line = reader.readLine()) != null) {
            htmlStr.append(line);
        }

        return htmlStr.toString();
    }


    public void insertDiary(String created_at, String bmiddle_pic, String text,String person) {
        WebDiary webDiary = new WebDiary();
        webDiary.setCreatedate(created_at);
        webDiary.setAddress("");
        webDiary.setImgtext(bmiddle_pic);
        text = text.replaceAll("'", "\"");
        webDiary.setText(text);
        webDiary.setPerson(person);
        iWebDiaryServices.addToTable(webDiary);
    }


    public void HttpClientTest() {


        String uri ="http://m.weibo.cn/api/container/getIndex?type=uid&value=3269067024&containerid=1076033269067024&since_id=4359981349864609";
        // (1) 创建HttpGet实例
        HttpGet get = new HttpGet(uri);

        // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse response = client.execute(get);

            // (3) 读取返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
//                System.out.println( EntityUtils.toString(entity,"UTF-8"));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void  update(){

        List<WebDiary> diaryAll = iWebDiaryServices.getDiaryAll();

        int countccc=0;
        for (int i = 0; i < diaryAll.size(); i++) {
            if (diaryAll.get(i).getCreatedate().toString().length()>10){
                diaryAll.get(i).setCreatedate(  diaryAll.get(i).getCreatedate().substring(0,10) );
                iWebDiaryServices.updateToTable(diaryAll.get(i));

                countccc++;
            }
        }

//        System.out.println("count++"+ countccc);

    }




    public static void main(String[] args) throws Exception {
//        getDiaryinit();
    }

    public  void  getDiaryinit() throws Exception {



     String    fileDir="C:\\Users\\KongCheng\\Desktop\\gwqwb\\weibo.html";
        Path file = Paths.get(fileDir);
        Charset charset = Charset.forName("UTF-8");

        StringBuilder inputStr=new StringBuilder();
        String line = null;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
        while ((line = reader.readLine()) != null) {

            inputStr.append(line);

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(inputStr.toString());

        HtmlUtil htmlUtil =new HtmlUtil();
        String temphtml="";

        temphtml= htmlUtil.getHtmlByExpression("<body>(.*?)</body>",inputStr.toString());
//        System.out.println(temphtml);

        String[] strings = htmlUtil.splitByExpression("<h4>赞</h4></div></footer></div></div></div>", temphtml);
//        System.out.println(temphtml);


        for (int i = 0; i < strings.length; i++) {
//            System.out.println(strings[i]);
        String time= htmlUtil.getHtmlByExpression("<span class=\"time\">(.*?)</span>",strings[i]);

            time=time.replaceAll("<span class=\"time\">","");
            time=time.replaceAll("</span>","");

            if (time.indexOf("分")>-1||time.indexOf("刚刚")>-1||time.indexOf("小时")>-1){
                time="2019-04-17";
            }
            if (time.length()<6){
                time="2019-"+time;
            }


        String text= htmlUtil.getHtmlByExpression("<div class=\"weibo-og\"><div class=\"weibo-text\">(.*?)</div>",strings[i]);
            text=text.replaceAll("./weibo_files/","//h5.sinaimg.cn/m/emoticon/icon/default/");

            if (text.indexOf("全文</a>")>-1){
                String href= htmlUtil.getHtmlByExpression("<a href=\"/status/(.*?)全文</a>",text);

                href=href.replace("<a href=\"","");
                href=href.replace("\">全文</a>","");
                href="http://m.weibo.cn"+href;
                text = getDetailInfo(href);
            }

            insertDiary( time, "", text,"");
//
//        System.out.println("time+=---"+time);
//        System.out.println("text+=---"+text);
        }


//            String html_list="<div class=\"card m-panel card9\"><div class=\"card-wrap\"><div class=\"card-main\"><!----><header class=\"weibo-top m-box m-avatar-box\"><a class=\"m-img-box\"><img src=\"./weibo_files/007DVR9Uly8g0e9rrgzvzj30hs0hs74o.jpg\"><!----></a><div class=\"m-box-col m-box-dir m-box-center\"><div class=\"m-text-box\"><a><h3 class=\"m-text-cut\">          甘文强爱陈敏芳          <!----></h3></a><h4 class=\"m-text-cut\"><span class=\"time\">22小时前</span><span class=\"from\"> 来自 小米8周年旗舰手机</span></h4></div></div><!----></header><article class=\"weibo-main\"><div class=\"weibo-og\"><div class=\"weibo-text\"><a href=\"https://m.weibo.cn/search?containerid=231522type%3D1%26t%3D10%26q%3D%23%E5%88%86%E6%89%8B%E7%9A%84%E6%AF%8F%E4%B8%80%E5%A4%A9%23&amp;extparam=%23%E5%88%86%E6%89%8B%E7%9A%84%E6%AF%8F%E4%B8%80%E5%A4%A9%23&amp;luicode=10000011&amp;lfid=1076037002995970\" data-hide=\"\"><span class=\"surl-text\">#分手的每一天#</span></a> 哎呀。今天跟你聊天说话的次数。又变少了。我真的好想跟你多聊一聊。在想你的时候。我就更新一下，我做的网站。我觉得现在效果还行。但是。云服务器就有点带宽，不够了。不像你打开页面就能看见我想你。晚上这边暂时还只更新了股票相关的。查询价格区间啊！新股上市啊！长福啊！数据虽 ...<a href=\"https://m.weibo.cn/status/4362165034595680\">全文</a></div><div><div class=\"weibo-media f-media\"><div class=\"weibo-media-wraps\"><div data-v-07aebe3c=\"\" class=\"card m-panel card26\"><a data-v-07aebe3c=\"\" href=\"javascript:;\"><div data-v-07aebe3c=\"\" class=\"card-wrap\"><div data-v-07aebe3c=\"\" class=\"card-main\"><div data-v-07aebe3c=\"\" class=\"m-box\"><div data-v-07aebe3c=\"\" class=\"m-img-box\"><img data-v-07aebe3c=\"\" src=\"./weibo_files/00001a91ly9g0ottqhdu0j20500500tl.jpg\" data-img=\"https://wx2.sinaimg.cn/thumb180/00001a91ly9g0ottqhdu0j20500500tl.jpg\"></div><div data-v-07aebe3c=\"\" class=\"m-box-col m-box-dir m-box-center\"><div data-v-07aebe3c=\"\" class=\"m-text-box\"><h3 data-v-07aebe3c=\"\" class=\"m-text-cut\">#分手的每一天#</h3><!----><h4 data-v-07aebe3c=\"\" class=\"m-text-cut\">76讨论   4.4万阅读</h4><!----></div></div><!----></div></div></div></a></div></div></div></div></div><!----></article><footer class=\"m-ctrl-box m-box-center-a\"><div class=\"m-diy-btn m-box-col m-box-center m-box-center-a\"><i class=\"m-font m-font-forward\"></i><h4>      转发    </h4></div><span class=\"m-line-gradient\"></span><div class=\"m-diy-btn m-box-col m-box-center m-box-center-a\"><i class=\"m-font m-font-comment\"></i><h4>      评论    </h4></div><span class=\"m-line-gradient\"></span><div class=\"m-diy-btn m-box-col m-box-center m-box-center-a\"><i class=\"m-icon m-icon-like\"></i>";
//        System.out.println(html_list);
//        String time= htmlUtil.getHtmlByExpression("<span class=\"time\">(.*?)</span>",html_list);
//        String text= htmlUtil.getHtmlByExpression("<div class=\"weibo-text\">(.*?)</div>",html_list);
////
//        System.out.println("time+=---"+time);
//        System.out.println("text+=---"+text);


    }



    public  String getDetailInfo( String  tempURL) throws Exception {
//        System.out.println(tempURL);

        String html = getHtmlByURL(tempURL, "UTF-8");

//        System.out.println("------"+html);
        HtmlUtil htmlUtil = new HtmlUtil();
       int beginindex= html.indexOf("\"text\":");
       int  endindex=  html.indexOf("\"textLength\":");
       if (beginindex>-1&&endindex>-1)
        html=html.substring(beginindex,endindex);
        html=html.replaceAll("\"text\": \"","");
 //        System.out.println("------"+html);

        html=html.replaceAll("./weibo_files/","//h5.sinaimg.cn/m/emoticon/icon/default/");
        return html;
    }



    public  void  getWeiBoByUser() throws Exception {

        String xinlanUid[]= {"3269067024","7002995970"};
        for (int i = 0; i < xinlanUid.length; i++) {
            String person="cmf";
            if (i==1){
                person="gwq";
            }
            getDiary(xinlanUid[i], "", "0" ,person);
        }


    }

    public  void  getDiaryinit(String userId ) throws Exception {


        String html = getHtmlByURL("http://m.weibo.cn/u/"+userId, "utf-8");

        HtmlUtil htmlUtil =new HtmlUtil();
        String temphtml="";

        temphtml= htmlUtil.getHtmlByExpression("<body>(.*?)</body>",html);

        String[] strings = htmlUtil.splitByExpression("<h4>赞</h4></div></footer></div></div></div>", temphtml);


        for (int i = 0; i < strings.length; i++) {
            String time= htmlUtil.getHtmlByExpression("<span class=\"time\">(.*?)</span>",strings[i]);

            time=time.replaceAll("<span class=\"time\">","");
            time=time.replaceAll("</span>","");

            if (time.indexOf("分")>-1||time.indexOf("刚刚")>-1||time.indexOf("小时")>-1){
                time="2019-04-19";
            }
            if (time.length()<6){
                time="2019-"+time;
            }


            String text= htmlUtil.getHtmlByExpression("<div class=\"weibo-og\"><div class=\"weibo-text\">(.*?)</div>",strings[i]);


            if (text.indexOf("全文</a>")>-1){
                String href= htmlUtil.getHtmlByExpression("<a href=\"/status/(.*?)全文</a>",text);

                href=href.replace("<a href=\"","");
                href=href.replace("\">全文</a>","");
                href="http://m.weibo.cn"+href;
                text = getDetailInfo(href);
            }

            //判断数据库是否存在


        }

    }
}
