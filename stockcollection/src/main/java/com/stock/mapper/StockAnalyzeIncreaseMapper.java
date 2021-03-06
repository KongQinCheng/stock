package com.stock.mapper;

import com.stock.bean.po.StockAnalyzeIncrease;
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
public interface StockAnalyzeIncreaseMapper {

        @Insert("insert into stock_analyze_increase (stockCode,stockDate,increase02,increase24,increase46,increase68,increase810,increase10," +
            "descend02,descend24,descend46,descend68,descend810,descend10,szcount,count) "
            + "values('${stockCode}','${stockDate}','${increase02}','${increase24}','${increase46}','${increase68}','${increase810}','${increase10}'," +
            "'${descend02}','${descend24}','${descend46}','${descend68}','${descend810}','${descend10}','${szcount}','${count}')")
    public void insert(StockAnalyzeIncrease StockAnalyzeIncrease);




    @Select("select * from stock_analyze_increase where 1=1  ")
    @Results(id = "stockIncreaseAnalyzeResults", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "increase02", property = "increase02"),
            @Result(column = "increase24", property = "increase24"),
            @Result(column = "increase46", property = "increase46"),
            @Result(column = "increase68", property = "increase68"),
            @Result(column = "increase810", property = "increase810"),
            @Result(column = "increase10", property = "increase10"),
            @Result(column = "descend02", property = "descend02"),
            @Result(column = "descend24", property = "descend24"),
            @Result(column = "descend46", property = "descend46"),
            @Result(column = "descend68", property = "descend68"),
            @Result(column = "descend810", property = "descend810"),
            @Result(column = "descend10", property = "descend10"),
            @Result(column = "szcount", property = "szcount"),
            @Result(column = "count", property = "count"),
            @Result(column = "stockDate", property = "stockDate")})
    public List<StockAnalyzeIncrease> getListAll();

    @Select("select * from stock_analyze_increase where stockCode = '${stockCode}'  and  stockDate = '${stockDate}' limit 0,1 ")
    @ResultMap("stockIncreaseAnalyzeResults")
    StockAnalyzeIncrease getEntryByStockCode(@Param("stockCode") String stockCode , @Param("stockDate") String stockDate);

    @Delete("delete  from stock_analyze_increase where stockCode = '${stockCode}' ")
    public void delByStockCode(@Param("stockCode") String stockCode);


    @Select("  SELECT COUNT(*) as count FROM stock_analyze_increase  WHERE  stockDate ='${stockDate}' and stockCode = '${stockCode}'")
    @Results(id = "isNewAnalyzeCountResults", value = {
            @Result(column = "count", property = "count")})
     int isNewCount(@Param("stockCode") String stockCode ,@Param("stockDate") String stockDate);



}
