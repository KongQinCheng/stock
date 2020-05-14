package com.stock.mapper;

import com.stock.bean.po.AnalyzeIncreaseDay;
import com.stock.bean.po.StockAnalyzeIncrease;
import com.stock.sqlProvider.StockAnalyzeIncreaseDaySqlProvider;
import com.stock.sqlProvider.StockNewDataSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;



@Mapper
public interface StockAnalyzeIncreaseDayMapper {

    @Insert("insert into stock_analyze_increase_day (stockCode,stockDate,indexDay,effectType,increaseType,level,crossType," +
            "increase10,increase9,increase8,increase7,increase6,increase5,increase4,increase3,increase2,increase1,increase0," +
            "descend1,descend2,descend3,descend4,descend5,descend6,descend7,descend8,descend9,descend10,descend20,count) "
            + "values('${stockCode}','${stockDate}','${indexDay}','${effectType}', '${increaseType}', '${level}','${crossType}', " +
            "'${increase10}','${increase9}','${increase8}','${increase7}','${increase6}','${increase5}','${increase4}','${increase3}','${increase2}','${increase1}','${increase0}'," +
            "'${descend1}','${descend2}','${descend3}','${descend4}','${descend5}','${descend6}','${descend7}','${descend8}','${descend9}','${descend10}','${descend20}','${count}')")
    public void insert(AnalyzeIncreaseDay analyzeIncreaseDay);


    @Select("select * from stock_analyze_increase_day where 1=1 and effectType = '${effectType}' ")
    @Results(id = "analyzeIncreaseDayResults", value = {
            @Result(column = "stockCode", property = "stockCode"),

            @Result(column = "indexDay", property = "indexDay"),
            @Result(column = "effectType", property = "effectType"),
            @Result(column = "count", property = "count"),

            @Result(column = "increase10", property = "increase10"),
            @Result(column = "increase9", property = "increase9"),
            @Result(column = "increase8", property = "increase8"),
            @Result(column = "increase7", property = "increase7"),
            @Result(column = "increase6", property = "increase6"),
            @Result(column = "increase5", property = "increase5"),
            @Result(column = "increase4", property = "increase4"),
            @Result(column = "increase3", property = "increase3"),
            @Result(column = "increase2", property = "increase2"),
            @Result(column = "increase1", property = "increase1"),

            @Result(column = "increase0", property = "increase0"),

            @Result(column = "descend1", property = "descend1"),
            @Result(column = "descend2", property = "descend2"),
            @Result(column = "descend3", property = "descend3"),
            @Result(column = "descend4", property = "descend4"),
            @Result(column = "descend5", property = "descend5"),
            @Result(column = "descend6", property = "descend6"),
            @Result(column = "descend7", property = "descend7"),
            @Result(column = "descend8", property = "descend8"),
            @Result(column = "descend9", property = "descend9"),
            @Result(column = "descend10", property = "descend10"),
            @Result(column = "descend20", property = "descend20"),

            @Result(column = "count", property = "count"),
            @Result(column = "stockDate", property = "stockDate")})
    public List<AnalyzeIncreaseDay> getListAll(@Param("effectType")  String effectType);


//    @SelectProvider(type = StockAnalyzeIncreaseDaySqlProvider.class, method = "getStockAnalyzeIncreaseDay")
//    @ResultMap("analyzeIncreaseDayResults")
//    List<AnalyzeCorssEffectDto> getEntryByStockCode(@Param("stockCode") String stockCode,@Param("stockDate")  String stockDate, @Param("effectType") String effectType,@Param("increaseType") String  increaseType);


    @SelectProvider(type = StockAnalyzeIncreaseDaySqlProvider.class, method = "getStockAnalyzeIncreaseDay")
    @ResultMap("analyzeIncreaseDayResults")
    List<AnalyzeIncreaseDay> getEntryByStockCode(AnalyzeIncreaseDay analyzeIncreaseDay);


    @Delete("delete  from stock_analyze_increase_day where stockCode = '${stockCode}' and increaseType= '${increaseType}'")
    public void delByStockCode(@Param("stockCode") String stockCode,@Param("increaseType") String increaseType);


    @Select("  SELECT COUNT(*) as count FROM stock_analyze_increase_day  WHERE  stockDate ='${stockDate}' and stockCode = '${stockCode}'")
    @Results(id = "isNewAnalyzeCountResults", value = {
            @Result(column = "count", property = "count")})
    int isNewCount(@Param("stockCode") String stockCode, @Param("stockDate") String stockDate);


}
