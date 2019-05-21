package com.stock.sqlProvider;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.AnalyzeIncreaseDay2;
import org.apache.ibatis.jdbc.SQL;

public class StockAnalyzeIncreaseDay2SqlProvider {

    public String getStockAnalyzeIncreaseDay(AnalyzeIncreaseDay2 analyzeIncreaseDay) {
        return new SQL() {
            {
                SELECT(" a.* ");
                FROM(" stock_analyze_increase_day2 a ");
                WHERE(" 1=1  ");

                if (!("".equals(analyzeIncreaseDay.getStockCode()) || analyzeIncreaseDay.getStockCode()==null)) {
                    WHERE(" a.stockCode = '"+ analyzeIncreaseDay.getStockCode() +"'" );
                }

                if (!("".equals(analyzeIncreaseDay.getStockDate()) || analyzeIncreaseDay.getStockDate()==null)) {
                    WHERE(" a.stockDate = '"+ analyzeIncreaseDay.getStockDate() +"'" );
                }
                if (!("".equals(analyzeIncreaseDay.getCrossType()) || analyzeIncreaseDay.getCrossType()==null)) {
                    WHERE(" a.crossType = '"+ analyzeIncreaseDay.getCrossType() +"'" );
                }

            }
        }.toString();
    }
    
}
