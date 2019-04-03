package com.stock.mapper;

import com.stock.bean.StockInfo;
import com.stock.bean.StockInfoNew;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * moduleInfo的持久层接口
 *
 * @author chenzuyi
 * @version Revision 1.0.0
 * *修改时间         | 修改内容
 */
public interface StockInfoNewMapper {

    //    @Select("select * from stock_info_${stockCode} where 1=1 and Date(stockDate) > '2018-12-10' ORDER BY stockDate ASC ")
    @Select("select * from stock_info_${stockCode} where 1=1  ORDER BY stockDate ASC ")
    @Results(id = "stockInfoNewResults", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "kpj", property = "kpj"),
            @Result(column = "zgj", property = "zgj"),
            @Result(column = "zdj", property = "zdj"),
            @Result(column = "spj", property = "spj"),
            @Result(column = "zde", property = "zde"),
            @Result(column = "zdf", property = "zdf"),
            @Result(column = "cjl", property = "cjl"),
            @Result(column = "cjje", property = "cjje"),
            @Result(column = "zf", property = "zf"),
            @Result(column = "hsl", property = "hsl"),
            @Result(column = "k_value", property = "kValue"),
            @Result(column = "d_value", property = "dValue"),
            @Result(column = "j_value", property = "jValue"),

            @Result(column = "EMA12", property = "EMA12"),
            @Result(column = "EMA26", property = "EMA26"),
            @Result(column = "DIF", property = "DIF"),
            @Result(column = "EMAMACD", property = "EMAMACD"),
            @Result(column = "BAR", property = "BAR"),

            @Result(column = "shareDate", property = "shareDate")})
    public List<StockInfo> getStockListByShareCode(StockInfo stockInfo);


    @Select("select * from  ( select * from stock_info_${stockCode} where 1=1  ORDER BY stockDate DESC limit 100 ) a ORDER BY a.stockDate ")
    @ResultMap("stockInfoNewResults")
    public List<StockInfo> getStockListByShareCodeLimit10(StockInfo stockInfo);


    @Select("${tempSql}")
    @ResultMap("stockInfoNewResults")
    public List<StockInfo> getStockListByShareCodeLimit(@Param("tempSql")String tempSql);


    @Insert("insert into stock_info_new30 (stockCode,stockDate,kpj,zgj,zdj,spj,zde,zdf,cjl,cjje,zf,hsl,EMA12,EMA26,DIF,EMAMACD,BAR) "
            + "values(${stockCode},'${stockDate}',${kpj},${zgj},${zdj},${spj},${zde},${zdf},${cjl},${cjje},${zf},${hsl},${EMA12},${EMA26},${DIF},${EMAMACD},${BAR})")
    public void addStockInfoNew(StockInfoNew stockInfoNew);




}
