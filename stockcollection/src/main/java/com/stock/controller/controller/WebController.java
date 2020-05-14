package com.stock.controller.controller;


import com.stock.controller.init.TimedTask;
import com.stock.services.IStockListServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class WebController {




    @Autowired
    TimedTask timedTask;

    @Autowired
    IStockListServices iStockListServices;

    //首页
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "web/index";
    }

    @RequestMapping("/web/toHome")
    public String toHome(){
        return "web/home";
    }

    //日记列表页
    @RequestMapping("/web/toDiaryList")
    public String toDiaryList(){
        return "web/diary_list";
    }
    //日记详情页
    @RequestMapping("/web/toDiaryInfo")
    public String toDiaryInfo(){
        return "web/diary_info";
    }

    //初始化失败的时候执行
    @RequestMapping("/web/init")
    public void init() throws Exception {
        iStockListServices.getStockNewList();
        timedTask.getStockInfo();
    }


    //初始化失败的时候执行
    @RequestMapping("/web/stockListInit")
    public void stockListInit() throws Exception {
        iStockListServices.getStockNewList();
    }
}
