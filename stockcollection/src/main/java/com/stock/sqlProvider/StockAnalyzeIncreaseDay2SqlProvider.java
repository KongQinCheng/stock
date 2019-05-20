package com.stock.sqlProvider;

import com.stock.bean.po.AnalyzeIncreaseDay;
import org.apache.ibatis.jdbc.SQL;

public class StockAnalyzeIncreaseDay2SqlProvider {

    public String getStockAnalyzeIncreaseDay(AnalyzeIncreaseDay analyzeIncreaseDay) {
        return new SQL() {
            {
                SELECT(" a.* ");
                FROM(" stock_analyze_increase_day2 a ");
                WHERE(" 1=1  ");

                if (!"".equals(analyzeIncreaseDay.getStockCode())) {
                    WHERE(" a.stockCode = '"+ analyzeIncreaseDay.getStockCode() +"'" );
                }

                if (!"".equals(analyzeIncreaseDay.getStockDate())) {
                    WHERE(" a.stockDate = '"+ analyzeIncreaseDay.getStockDate() +"'" );
                }
                if (!"".equals(analyzeIncreaseDay.getCrossType()+"")) {
                    WHERE(" a.crossType = '"+ analyzeIncreaseDay.getCrossType() +"'" );
                }

            }
        }.toString();
    }
    
}
