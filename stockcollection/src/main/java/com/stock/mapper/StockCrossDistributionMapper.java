package com.stock.mapper;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockCrossDistribution;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface StockCrossDistributionMapper {


    @Insert("insert into stock_cross_distribution (stockCode,stockDate,dayNum,crossType," +
            "increase0,increase1,increase2,increase3,increase4,increase5,increase6,increase7,increase8,increase9,increase10,count) "
            + "values('${stockCode}','${stockDate}','${dayNum}','${crossType}'," +
            "'${increase0}','${increase1}','${increase2}','${increase3}','${increase4}','${increase5}','${increase6}','${increase7}','${increase8}','${increase9}','${increase10}','${count}')")
    void insert(StockCrossDistribution stockCrossDistribution);


    @Select("SELECT * FROM stock_cross_distribution ")
    @Results(id = "effectCrossDistributionResults", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "stockDate", property = "stockDate"),
            @Result(column = "dayNum", property = "dayNum"),
            @Result(column = "crossType", property = "crossType"),
            @Result(column = "increase0", property = "increase0"),
            @Result(column = "increase1", property = "increase1"),
            @Result(column = "increase2", property = "increase2"),
            @Result(column = "increase3", property = "increase3"),
            @Result(column = "increase4", property = "increase4"),
            @Result(column = "increase5", property = "increase5"),
            @Result(column = "increase6", property = "increase6"),
            @Result(column = "increase7", property = "increase7"),
            @Result(column = "increase8", property = "increase8"),
            @Result(column = "increase9", property = "increase9"),
            @Result(column = "increase10", property = "increase10"),
            @Result(column = "count", property = "count"),
    })
    List<StockCrossDistribution> getStockCrossDistributionList();


    @Select("SELECT * FROM stock_cross_distribution where stockCode ='${stockCode}' and dayNum ='${dayNum}' and crossType ='${crossType}' and stockDate ='${stockDate}' ")
    @ResultMap("effectCrossDistributionResults")
    StockCrossDistribution getStockCrosstDistribution(@Param("stockCode") String stockCode,@Param("stockDate") String stockDate,@Param("crossType") String crossType,@Param("dayNum") String dayNum);



    @Select("SELECT * FROM stock_cross_distribution where stockCode ='${stockCode}' and dayNum ='${dayNum}' and crossType ='${crossType}' and stockDate ='${stockDate}'")
    @ResultMap("effectCrossDistributionResults")
    List<StockCrossDistribution> getStockCrosstDistributionList(@Param("stockCode") String stockCode,@Param("stockDate") String stockDate,@Param("crossType") String crossType,@Param("dayNum") String dayNum);

    @Select("SELECT * FROM stock_cross_distribution where stockCode ='${stockCode}'  and stockDate ='${stockDate}' ")
    @ResultMap("effectCrossDistributionResults")
    List<StockCrossDistribution> getStockCrosstByStockCodeAndStockDate(@Param("stockCode") String stockCode,@Param("stockDate") String stockDate);

    @Delete("DELETE FROM stock_cross_distribution where stockCode ='${stockCode}' ")
    void del(@Param("stockCode") String stockCode);


}
