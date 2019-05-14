package com.stock.bean.po;


import lombok.Data;

@Data
public class AnalyzeIncreaseDayMore {

        AnalyzeIncreaseDay analyzeIncreaseDayAll;
        AnalyzeIncreaseDay analyzeIncreaseDayPart;

        public  AnalyzeIncreaseDayMore(){}

        public  AnalyzeIncreaseDayMore(String stockCode,String stockDate,int indexDay,int increaseType){
                AnalyzeIncreaseDay analyzeIncreaseDayAll =new AnalyzeIncreaseDay();
                analyzeIncreaseDayAll.setStockCode(stockCode);
                analyzeIncreaseDayAll.setStockDate(stockDate);
                analyzeIncreaseDayAll.setIndexDay(indexDay);
                analyzeIncreaseDayAll.setIncreaseType(increaseType);
                analyzeIncreaseDayAll.setLevel(0);

                AnalyzeIncreaseDay analyzeIncreaseDayPart =new AnalyzeIncreaseDay();
                analyzeIncreaseDayPart.setStockCode(stockCode);
                analyzeIncreaseDayPart.setStockDate(stockDate);
                analyzeIncreaseDayPart.setIndexDay(indexDay);
                analyzeIncreaseDayPart.setIncreaseType(increaseType);
                analyzeIncreaseDayPart.setLevel(1);
                this.analyzeIncreaseDayAll=analyzeIncreaseDayAll;
                this.analyzeIncreaseDayPart=analyzeIncreaseDayPart;
        }


}
