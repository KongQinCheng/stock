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


                if (stockNewDataVo.getSpjmin() != 0.0) {
                    WHERE(" a.spj >= "+ stockNewDataVo.getSpjmin());
                }
                if (stockNewDataVo.getSpjmax() != 0.0) {
                    WHERE(" a.spj <= "+ stockNewDataVo.getSpjmax());
                }

                if (stockNewDataVo.getKvaluemin() != 0.0) {
                    WHERE(" a.k_value >= "+ stockNewDataVo.getKvaluemin());
                }
                if (stockNewDataVo.getKvaluemax() != 0.0) {
                    WHERE(" a.k_value <= "+ stockNewDataVo.getKvaluemax());
                }

                if (stockNewDataVo.getDvaluemin() != 0.0) {
                    WHERE(" a.d_value >= "+stockNewDataVo.getDvaluemin());
                }
                if (stockNewDataVo.getDvaluemax() != 0.0) {
                    WHERE(" a.d_value <= "+stockNewDataVo.getDvaluemax());
                }

                if (stockNewDataVo.getJvaluemin() != 0.0) {
                    WHERE(" a.j_value >= "+stockNewDataVo.getJvaluemin());
                }
                if (stockNewDataVo.getJvaluemax() != 0.0) {
                    WHERE(" a.j_value <= "+stockNewDataVo.getJvaluemax());
                }


                if (stockNewDataVo.getRsivaluemin1() != 0.0) {
                    WHERE(" a.RSI06 >= "+stockNewDataVo.getRsivaluemin1());
                }
                if (stockNewDataVo.getRsivaluemax1() != 0.0) {
                    WHERE(" a.RSI06 <= "+stockNewDataVo.getRsivaluemax1());
                }

                if (stockNewDataVo.getRsivaluemin2() != 0.0) {
                    WHERE(" a.RSI12 >= "+stockNewDataVo.getRsivaluemin2());
                }
                if (stockNewDataVo.getRsivaluemax2() != 0.0) {
                    WHERE(" a.RSI12 <= "+stockNewDataVo.getRsivaluemax2());
                }

                if (stockNewDataVo.getRsivaluemin3() != 0.0) {
                    WHERE(" a.RSI24 >= "+stockNewDataVo.getRsivaluemin3());
                }
                if (stockNewDataVo.getRsivaluemax3() != 0.0) {
                    WHERE(" a.RSI24 <= "+stockNewDataVo.getRsivaluemax3());
                }


                if (stockNewDataVo.getCcivaluemin() != 0.0) {
                    WHERE(" a.cci >= "+stockNewDataVo.getCcivaluemin());
                }
                if (stockNewDataVo.getCcivaluemax() != 0.0) {
                    WHERE(" a.cci <= "+stockNewDataVo.getCcivaluemax());
                }

                if (stockNewDataVo.getHslmin() != 0.0) {
                    WHERE(" a.hsl >= "+stockNewDataVo.getHslmin());
                }
                if (stockNewDataVo.getHslmax() != 0.0) {
                    WHERE(" a.hsl <= "+stockNewDataVo.getHslmax());
                }

                ORDER_BY("a.cci");

            }
        }.toString();
    }


    public String getStockHslValueRegionByVo(StockNewDataVo stockNewDataVo) {
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
                    WHERE(" a.spj <= "+ stockNewDataVo.getSpjmax());
                }

                if (stockNewDataVo.getHslmin() != 0.0) {
                    WHERE(" a.hsl >= "+ stockNewDataVo.getHslmin());
                }
                if (stockNewDataVo.getHslmax() != 0.0) {
                    WHERE(" a.hsl <= "+ stockNewDataVo.getHslmax());
                }



            }
        }.toString();
    }



    
}
