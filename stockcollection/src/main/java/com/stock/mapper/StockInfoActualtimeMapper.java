package com.stock.mapper;

import com.stock.bean.po.AnalyzeIncreaseDay2;
import com.stock.bean.po.StockInfo;
import com.stock.bean.po.StockInfoActualtime;
import com.stock.sqlProvider.StockAnalyzeIncreaseDay2SqlProvider;
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
public interface StockInfoActualtimeMapper {

    @Insert("insert into stock_info_actualtime (stockCode,stockDate,spj) "
            + "values('${stockCode}','${stockDate}','${spj}')")
    public void insert(StockInfoActualtime stockInfoActualtime);


    @Select("select * from stock_info_actualtime where 1=1 and stockDate = '${stockDate}' order by spj desc ")
    @Results(id = "stockinfoActualtimeResults", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "spj", property = "spj"),
            @Result(column = "stockDate", property = "stockDate")})
    public List<StockInfoActualtime> getByStockDate(@Param("stockDate") String stockDate);


    @Select("select * from stock_info_actualtime where 1=1  and effectDay1 is  null order by stockDate desc ")
    @ResultMap("stockinfoActualtimeResults")
    public List<StockInfoActualtime> getByNullData();



    @Delete("delete  from stock_info_actualtime where stockCode = '${stockCode}' ")
    public void delByStockCode(@Param("stockCode") String stockCode);


    @Delete("delete  from stock_info_actualtime where stockCode = '${stockCode}'and  stockDate = '${stockDate}' ")
    public void delByStockCodeAndStockDate(@Param("stockCode") String stockCode,@Param("stockDate") String stockDate);

    @Update("update  stock_info_actualtime set effectDay1=${effectDay1}, effectDay2=${effectDay2}, effectDay3=${effectDay3}, effectDay4=${effectDay4}, effectDay5=${effectDay5} where stockCode = '${stockCode}'and  stockDate = '${stockDate}' ")
    public void update(StockInfoActualtime stockInfoActualtime);



}
