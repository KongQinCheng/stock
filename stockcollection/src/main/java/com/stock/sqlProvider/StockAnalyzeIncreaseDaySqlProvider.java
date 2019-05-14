package com.stock.sqlProvider;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.vo.StockNewDataVo;
import org.apache.ibatis.jdbc.SQL;

public class StockAnalyzeIncreaseDaySqlProvider {

    public String getStockAnalyzeIncreaseDay(AnalyzeIncreaseDay analyzeIncreaseDay) {
        return new SQL() {
            {
                SELECT(" a.* ");
                FROM(" stock_analyze_increase_day a ");
                WHERE(" 1=1  ");

                if (!"".equals(analyzeIncreaseDay.getStockCode())) {
                    WHERE(" a.stockCode = '"+ analyzeIncreaseDay.getStockCode() +"'" );
                }

                if (!"".equals(analyzeIncreaseDay.getStockDate())) {
                    WHERE(" a.stockDate = '"+ analyzeIncreaseDay.getStockDate() +"'" );
                }
                if (!"".equals(analyzeIncreaseDay.getEffectType()+"")) {
                    WHERE(" a.effectType = '"+ analyzeIncreaseDay.getEffectType() +"'" );
                }
                if (!"".equals(analyzeIncreaseDay.getIncreaseType()+"")) {
                    WHERE(" a.increaseType = '"+ analyzeIncreaseDay.getIncreaseType() +"'" );
                }

                if (! ("".equals(analyzeIncreaseDay.getIndexDay()+"") || analyzeIncreaseDay.getIndexDay()==0)) {
                    WHERE(" a.indexDay = '"+ analyzeIncreaseDay.getIndexDay() +"'" );
                }

                WHERE(" a.level = '"+ analyzeIncreaseDay.getLevel() +"'" );
                ORDER_BY("a.indexDay ");

            }
        }.toString();
    }
    
}
