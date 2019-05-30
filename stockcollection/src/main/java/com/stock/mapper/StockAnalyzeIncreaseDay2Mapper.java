package com.stock.mapper;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.AnalyzeIncreaseDay2;
import com.stock.sqlProvider.StockAnalyzeIncreaseDay2SqlProvider;
import com.stock.sqlProvider.StockAnalyzeIncreaseDaySqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * moduleInfo的持久层接口
 *
 * @author chenzuyi
 * @version Revision 1.0.0
 * *修改时间         | 修改内容
 */

@Mapper
public interface StockAnalyzeIncreaseDay2Mapper {

    @Insert("insert into stock_analyze_increase_day2 (stockCode,stockDate,crossType,searchType," +
            "day1,day2,day3,day4,day5,count) "
            + "values('${stockCode}','${stockDate}','${crossType}','${searchType}',  " +
            "'${day1}','${day2}','${day3}','${day4}','${day5}','${count}')")
    public void insert(AnalyzeIncreaseDay2 analyzeIncreaseDay);


    @Select("select * from stock_analyze_increase_day2 where 1=1 and crossType = '${crossType}' ")
    @Results(id = "analyzeIncreaseDay2Results", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "effectType", property = "effectType"),
            @Result(column = "day1", property = "day1"),
            @Result(column = "day2", property = "day2"),
            @Result(column = "day3", property = "day3"),
            @Result(column = "day4", property = "day4"),
            @Result(column = "day5", property = "day5"),
            @Result(column = "count", property = "count"),
            @Result(column = "stockDate", property = "stockDate")})
    public List<AnalyzeIncreaseDay2> getListAll(@Param("crossType") String crossType);


    @SelectProvider(type = StockAnalyzeIncreaseDay2SqlProvider.class, method = "getStockAnalyzeIncreaseDay")
    @ResultMap("analyzeIncreaseDay2Results")
    List<AnalyzeIncreaseDay2> getEntryByStockCode(AnalyzeIncreaseDay2 analyzeIncreaseDay);


    @Delete("delete  from stock_analyze_increase_day2 where stockCode = '${stockCode}'  ")
    public void delByStockCode(@Param("stockCode") String stockCode, @Param("searchType") String searchType);


    @Select("  SELECT COUNT(*) as count FROM stock_analyze_increase_day2  WHERE  stockDate ='${stockDate}' and stockCode = '${stockCode}'")
    @Results(id = "isNewAnalyzeCountResults", value = {
            @Result(column = "count", property = "count")})
    int isNewCount(@Param("stockCode") String stockCode, @Param("stockDate") String stockDate);


}
