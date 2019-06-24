package com.stock.sqlProvider;

import com.stock.bean.vo.StockNewDataVo;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.jdbc.SQL;
public class StockNewDataSqlProvider {
    
    public String getStockNewDataListByVo(StockNewDataVo stockNewDataVo) {
        return new SQL() {
            {
                SELECT(" a.* ");

                FROM(" stock_new_data a ");
                WHERE(" a.stockDate = (SELECT MAX(stockDate) FROM stock_new_data )  ");

                if (!("".equals(stockNewDataVo.getStockCode())||stockNewDataVo.getStockCode()==null)) {
                    WHERE(" a.stockCode = '"+ stockNewDataVo.getStockCode() +"'" );
                }

                if (stockNewDataVo.getSpjmin() != 0.0) {
                    WHERE(" a.spj >= "+ stockNewDataVo.getSpjmin());
                }
                if (stockNewDataVo.getSpjmax() != 0.0) {
                    WHERE(" a.spj <= "+stockNewDataVo.getSpjmax());
                }

                WHERE(" a.zdf >= "+ stockNewDataVo.getZdfmin());
                WHERE(" a.zdf <= "+stockNewDataVo.getZdfmax());


                ORDER_BY("a.zdf desc ");

            }
        }.toString();
    }

    public String getStockKdjValueRegionByVo(StockNewDataVo stockNewDataVo) {
        return new SQL() {
            {
                SELECT(" a.* ");

                FROM(" stock_new_data a ");
                WHERE(" a.stockDate = (SELECT MAX(stockDate) FROM stock_new_data )  ");

                if (!("".equals(stockNewDataVo.getStockCode())||stockNewDataVo.getStockCode()==null)) {
                    WHERE(" a.stockCode = '"+ stockNewDataVo.getStockCode() +"'" );
                }

                if (stockNewDataVo.getKValueMin() != 0.0) {
                    WHERE(" a.k_value >= "+ stockNewDataVo.getKValueMin());
                }
                if (stockNewDataVo.getKValueMax() != 0.0) {
                    WHERE(" a.k_value <= "+ stockNewDataVo.getKValueMax());
                }

                if (stockNewDataVo.getDValueMin() != 0.0) {
                    WHERE(" a.d_value >= "+stockNewDataVo.getDValueMin());
                }
                if (stockNewDataVo.getDValueMax() != 0.0) {
                    WHERE(" a.d_value <= "+stockNewDataVo.getDValueMax());
                }

                if (stockNewDataVo.getJValueMin() != 0.0) {
                    WHERE(" a.j_value >= "+stockNewDataVo.getJValueMin());
                }
                if (stockNewDataVo.getJValueMax() != 0.0) {
                    WHERE(" a.j_value <= "+stockNewDataVo.getJValueMax());
                }

            }
        }.toString();
    }

    
}
