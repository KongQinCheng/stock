package com.stock.mapper;

import com.stock.bean.po.AnalyzeIncreaseDay2;
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


    @Select("select * from stock_info_actualtime where 1=1 ")
    @Results(id = "stockinfoActualtimeResults", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "spj", property = "spj"),
            @Result(column = "stockDate", property = "stockDate")})
    public List<AnalyzeIncreaseDay2> getListAll(@Param("crossType") String crossType);


    @Delete("delete  from stock_info_actualtime where stockCode = '${stockCode}' ")
    public void delByStockCode(@Param("stockCode") String stockCode);



}
