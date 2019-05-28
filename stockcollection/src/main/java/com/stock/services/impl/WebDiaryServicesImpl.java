package com.stock.services.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;
import com.stock.dao.IWebDiaryDao;
import com.stock.services.IWebDiaryServices;
import com.stock.util.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
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

@Service
public class WebDiaryServicesImpl implements IWebDiaryServices {

    @Autowired
    IWebDiaryDao iWebDiaryDao;

    @Autowired
    HtmlUtil htmlUtil;

    @Autowired
    IWebDiaryServices iWebDiaryServices;

    @Override
    public List<WebDiary> getDiaryAll() {
        return iWebDiaryDao.getDiaryAll();
    }

    @Override
    public List<WebDiary> getDiaryByIndex(DiaryVo diaryVo) {
        return iWebDiaryDao.getDiaryByIndex(diaryVo);
    }

    @Override
    public WebDiary getDiaryById(String id) {
        return iWebDiaryDao.getDiaryById(id);
    }

    @Override
    public void addToTable(WebDiary webDiary) {
        iWebDiaryDao.addToTable(webDiary);
    }

    @Override
    public void updateToTable(WebDiary webDiary) {
        iWebDiaryDao.updateToTable(webDiary);
    }

    @Override
    public boolean isExitByText(String text,String date) {
        return  iWebDiaryDao.isExitByText(text, date);
    }


    @Override
    public  void  getWeiBoByUser() throws Exception {
        String xinlanUid[]= {"3269067024","7002995970"};
        for (int i = 0; i < xinlanUid.length; i++) {
            String person="cmf";
            if (i==1){
                person="gwq";
            }
            getDiary(xinlanUid[i], "", "0" ,person);
        }
        System.out.println("微博信息获取成功");
    }


    public String getDiary(String uid, String since_id, String type,String person) throws Exception {

        String temp_since_id = "";
        String tempURL = "";
        if ("0".equals(type)) {
            tempURL = "https://m.weibo.cn/api/container/getIndex?type=uid&value=" + uid + "&containerid=107603" + uid;

        }
        String html = getHtmlByURL(tempURL, "GBK");
        if (html.indexOf("<title>302 Found</title>")>-1){
            return "";
        }

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
                            href="https://m.weibo.cn"+href;
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


    /**
     * 无法获取全量数据的时候，将微博数据保存成 html 格式，进行读取
     * @throws Exception
     */
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

//        System.out.println("time+=---"+time);
//        System.out.println("text+=---"+text);
        }
    }


    /**
     * 当微博内容文字过多的时候 ，通过链接，查看完整数据。
     * @param tempURL
     * @return
     * @throws Exception
     */
    public  String getDetailInfo( String  tempURL) throws Exception {

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






}
