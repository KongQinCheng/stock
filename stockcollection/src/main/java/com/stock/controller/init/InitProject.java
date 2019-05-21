package com.stock.controller.init;


import com.stock.services.IStockAnalyzeIncreaseServices;
import com.stock.services.IStockAnalyzeMacdServices;
import com.stock.services.IWebDiaryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitProject implements ApplicationRunner {


    @Autowired
    IStockAnalyzeMacdServices iStockAnalyzeMacdServices;




    @Override
    public void run(ApplicationArguments args) {
        //项目初始化执行

        try {

//            iStockAnalyzeMacdServices.getStockCrossEffectNewFinal("603383","11");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常");
        }


    }
}
