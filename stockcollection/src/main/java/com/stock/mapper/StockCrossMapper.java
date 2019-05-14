package com.stock.mapper;

import com.stock.bean.po.StockCross;
import com.stock.bean.po.StockInfo;
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
public interface StockCrossMapper {


    @Insert("insert into effect_cross (stockCode,stockDate,increase1,increase2,increase3,increase4,increase5," +
            "increase102,increase124,increase146,increase168,increase1810,increase110," +
            "descend102,descend124,descend146,descend168,descend1810,descend110,count1," +

            "increase202,increase224,increase246,increase268,increase2810,increase210," +
            "descend202,descend224,descend246,descend268,descend2810,descend210,count2," +

            "increase302,increase324,increase346,increase368,increase3810,increase310," +
            "descend302,descend324,descend346,descend368,descend3810,descend310,count3," +

            "increase402,increase424,increase446,increase468,increase4810,increase410," +
            "descend402,descend424,descend446,descend468,descend4810,descend410,count4," +

            "increase502,increase524,increase546,increase568,increase5810,increase510," +
            "descend502,descend524,descend546,descend568,descend5810,descend510,count5," +

            "count) "
            + "values('${stockCode}','${stockDate}','${increase1}','${increase2}','${increase3}','${increase4}','${increase5}'," +
            "'${increase102}','${increase124}','${increase146}','${increase168}','${increase1810}','${increase110}'," +
            "'${descend102}','${descend124}','${descend146}','${descend168}','${descend1810}','${descend110}','${count1}'," +

            "'${increase202}','${increase224}','${increase246}','${increase268}','${increase2810}','${increase210}'," +
            "'${descend202}','${descend224}','${descend246}','${descend268}','${descend2810}','${descend210}','${count2}'," +

            "'${increase302}','${increase324}','${increase346}','${increase368}','${increase3810}','${increase310}'," +
            "'${descend302}','${descend324}','${descend346}','${descend368}','${descend3810}','${descend310}','${count3}'," +

            "'${increase402}','${increase424}','${increase446}','${increase468}','${increase4810}','${increase410}'," +
            "'${descend402}','${descend424}','${descend446}','${descend468}','${descend4810}','${descend410}','${count4}'," +

            "'${increase502}','${increase524}','${increase546}','${increase568}','${increase5810}','${increase510}'," +
            "'${descend502}','${descend524}','${descend546}','${descend568}','${descend5810}','${descend510}','${count5}'," +
            "'${count}')")
     void insert(StockCross stockCross);


    @Select("SELECT * FROM effect_cross ")
    @Results(id = "effectCrossResults", value = {
            @Result(column = "stockCode", property = "stockCode"),
            @Result(column = "stockDate", property = "stockDate"),
            @Result(column = "increase1", property = "increase1"),
            @Result(column = "increase2", property = "increase2"),
            @Result(column = "increase3", property = "increase3"),
            @Result(column = "increase4", property = "increase4"),
            @Result(column = "increase5", property = "increase5"),
            @Result(column = "count", property = "count"),
    })
    List<StockCross> getStockCrossList();


    @Select("SELECT * FROM effect_cross where stockCode ='${stockCode}'")
    @ResultMap("effectCrossResults")
    StockCross getStockCrosstByStockCode(@Param("stockCode") String stockCode);


    @Select("  SELECT COUNT(*) as count FROM effect_cross WHERE stockCode ='${stockCode}' and stockDate ='${stockDate}'")
    @Results(id = "isCrossExistResults", value = {
            @Result(column = "count", property = "count")})
     double isExistByStockCodeAndDate(@Param("stockCode") String stockCode,@Param("stockDate") String stockDate);



@Delete("delete from effect_cross where stockCode='${stockCode}'")
    void delete(@Param("stockCode") String stockCode);


}
