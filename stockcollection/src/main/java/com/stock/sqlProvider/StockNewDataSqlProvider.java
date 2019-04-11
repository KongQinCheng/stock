package com.stock.sqlProvider;

import com.stock.bean.vo.StockNewDataVo;
import org.apache.ibatis.jdbc.SQL;
public class StockNewDataSqlProvider {
    
    public String getStockNewDataListByVo(StockNewDataVo stockNewDataVo) {
        return new SQL() {
            {
                SELECT(" a.* ");

                FROM(" stock_new_data a ");
                WHERE(" a.stockDate = (SELECT MAX(stockDate) FROM stock_new_data )  ");
                if (stockNewDataVo.getSpjmin() != 0.0) {
                    WHERE(" a.spj >= "+ stockNewDataVo.getSpjmin());
                }
                if (stockNewDataVo.getSpjmax() != 0.0) {
                    WHERE(" a.spj <= "+stockNewDataVo.getSpjmax());
                }
            }
        }.toString();
    }
    
}
