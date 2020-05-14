package com.stock.mapper;

import com.stock.bean.po.StockList;
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
public interface StockListMapper {

    @Select("select * from stock_list where 1=1    and  status=1")
    @Results(id = "stockListResults", value = {
            @Result(column = "stockCode", property = "stockCode")})
    public List<StockList> getStockList();

    @Select("select * from stock_list where 1=1   and  status=1 order by stockCode desc")
    @ResultMap("stockListResults")
    public List<StockList> getStockListDesc();



    @Select("select * from stock_list where 1=1   and  status=1 limit #{limitNum}")
    @ResultMap("stockListResults")
    public List<StockList> getStockListLimit(@Param("limitNum") int limitNum);

    @Insert("insert into stock_list(stockCode,stockName,status) values (#{stockCode},#{stockName},#{status})")
    public void addStockList(@Param("stockCode") String stockCode, @Param("stockName") String stockName   ,  @Param("StockDate") String StockDate,  @Param("status") String status);


    @Update("update stock_list set stockName=#{stockName}  where stockCode= #{stockCode}")
    public void updateStockList(@Param("stockCode") String stockCode, @Param("stockName") String stockName);



    @Update("update stock_list set status=#{status}  where stockCode= #{stockCode}")
    public void updateStockListStatus(@Param("stockCode") String stockCode, @Param("status") String status);



    @Delete("DELETE from stock_list WHERE stockCode =#{stockCode}")
    public void delStockList(@Param("stockCode") String stockCode);


    @Select("  SELECT COUNT(*) as count FROM stock_list  WHERE stockCode =#{stockCode}")
    @Results(id = "isTableExistResults2", value = {
            @Result(column = "count", property = "count")})
    public double isExitStockList(String stockCode);


}
