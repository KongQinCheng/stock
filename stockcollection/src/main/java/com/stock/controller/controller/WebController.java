package com.stock.controller.controller;


import com.stock.controller.init.TimedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class WebController {




    @Autowired
    TimedTask timedTask;

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
        timedTask.getStockInfo();
    }


}
