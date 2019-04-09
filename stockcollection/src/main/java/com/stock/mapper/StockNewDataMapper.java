package com.stock.mapper;

import com.stock.bean.StockInfo;
import com.stock.bean.StockNewData;
import com.stock.bean.StockNewDataVo;
import com.stock.sqlProvider.StockNewDataSqlProvider;
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
public interface StockNewDataMapper {

    @Insert("insert into stock_new_data (stockCode,stockDate,kpj,zgj,zdj,spj,zde,zdf,cjl,cjje,zf,hsl,EMA12,EMA26,DIF,EMAMACD,BAR) "
            + "values(#{stockCode},'${stockDate}',${kpj},${zgj},${zdj},${spj},${zde},${zdf},${cjl},${cjje},${zf},${hsl},${EMA12},${EMA26},${DIF},${EMAMACD},${BAR})")
    public void insert(StockInfo stockInfo);

    @Delete("DELETE from stock_new_data WHERE 1=1")
    public void deleteAll();


    @Select("select * from stock_new_data ")
    @Results(id = "stockNewDataResults", value = {
            @Result(column = "id", property = "id"),
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
    public  List<StockNewData> getNewDataAll(StockNewDataVo stockNewDataVo);


    @SelectProvider(type = StockNewDataSqlProvider.class,method = "getStockNewDataListByVo")
    @ResultMap("stockNewDataResults")
    public  List<StockNewData> getStockNewDataListByVo(StockNewDataVo stockNewDataVo);


//    @SelectProvider(type = cn.anicert.maintenance.common.sqlProvider.StockNewDataSqlProvider.class, method = "updateGroupInfo")
//    public void updateGroupInfo(GroupInfo groupInfo);

//    @SelectProvider()



    @Select("select * from stock_new_data where 1=1  ORDER BY stockDate ASC limit ${limitNum} ")
    @ResultMap("stockNewDataResults")
    public List<StockNewData> getStockListByStockCode(@Param("stockCode") String stockCode, @Param("limitNum") int limitNum);




}
