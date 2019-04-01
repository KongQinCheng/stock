package com.stock.mapper;

import com.stock.bean.StockInfo;
import com.stock.bean.StockList;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * moduleInfo的持久层接口
 *
 * @author chenzuyi
 * @version Revision 1.0.0
 * *修改时间         | 修改内容
 */
public interface StockListMapper {

    @Select("select * from stock_list where 1=1 ")
    @Results(id = "stockListResults", value = {
            @Result(column = "stockCode", property = "stockCode")})
    public List<StockList> getStockList();

    @Insert("insert into stock_list(stockCode,stockName) values (#{stockCode},#{stockName})")
    public void addStockList(@Param("stockCode")String  stockCode,@Param("stockName")String stockName);


    @Delete("DELETE from stock_list WHERE stockCode =#{stockCode}")
    public void delStockList(@Param("stockCode")String  stockCode);




}
