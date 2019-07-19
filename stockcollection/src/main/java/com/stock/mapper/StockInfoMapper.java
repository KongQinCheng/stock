package com.stock.mapper;

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
public interface StockInfoMapper {

    //    @Select("select * from stock_info_${stockCode} where 1=1 and Date(stockDate) > '2018-12-10' ORDER BY stockDate ASC ")
    @Select("select * from stock_info_${stockCode} where 1=1  ORDER BY stockDate ASC limit ${limitNum} ")
    @Results(id = "stockBeanResults", value = {
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

            @Result(column = "RSI06", property = "RSI06"),
            @Result(column = "RSI12", property = "RSI12"),
            @Result(column = "RSI24", property = "RSI24"),

            @Result(column = "shareDate", property = "shareDate")})
    public List<StockInfo> getStockListByStockCode(@Param("stockCode") String stockCode, @Param("limitNum") int limitNum);



    @Select("select b.* from (select * from stock_info_${stockCode} where 1=1  ORDER BY stockDate DESC limit ${limitNum}) b ORDER BY b.stockDate ${sortType} ")
    @ResultMap("stockBeanResults")
    public List<StockInfo> getNewStockListByStockCode(@Param("stockCode") String stockCode, @Param("sortType") String sortType, @Param("limitNum") int limitNum);


    @Select("select * from  ( select * from stock_info_${stockCode} where 1=1  ORDER BY stockDate DESC limit ${limitNum} ) a ORDER BY a.stockDate ")
    @ResultMap("stockBeanResults")
    public List<StockInfo> getStockListByStockCodeLimit(@Param("stockCode") String stockCode, @Param("limitNum") int limitNum);


    @Select("select * from stock_info_${stockCode} where stockDate ='${stockDate}' ")
    @ResultMap("stockBeanResults")
    public List<StockInfo> getStockListByStockCodeAndStockDateLimit(@Param("stockCode") String stockCode, @Param("stockDate") String stockDate);



    @Insert("insert into stock_info_${stockCode} (stockCode,stockDate,kpj,zgj,zdj,spj,zde,zdf,cjl,cjje,zf,hsl) "
            + "values('${stockCode}','${stockDate}',${kpj},${zgj},${zdj},${spj},${zde},${zdf},${cjl},${cjje},${zf},${hsl})")
    public void addStockInfo(StockInfo stockCode);

    //------- 创建表----------
    @Update(" CREATE TABLE  ${tableName} ( " +
            "`stockCode`  varchar(100) NULL DEFAULT NULL ,\n" +
            "`stockDate`  datetime NOT NULL ,\n" +
            "`kpj`  double NULL DEFAULT NULL ,\n" +
            "`zgj`  double NULL DEFAULT NULL ,\n" +
            "`zdj`  double NULL DEFAULT NULL ,\n" +
            "`spj`  double NULL DEFAULT NULL ,\n" +
            "`zde`  double NULL DEFAULT NULL ,\n" +
            "`zdf`  double NULL DEFAULT NULL ,\n" +
            "`cjl`  double NULL DEFAULT NULL ,\n" +
            "`cjje`  double NULL DEFAULT NULL ,\n" +
            "`zf`  double NULL DEFAULT NULL ,\n" +
            "`hsl`  double NULL DEFAULT NULL ,\n" +
            "`k_value`  double NULL DEFAULT NULL ,\n" +
            "`d_value`  double NULL DEFAULT NULL ,\n" +
            "`j_value`  double NULL DEFAULT NULL ,\n" +
            "`EMA12`  double NULL DEFAULT NULL ,\n" +
            "`EMA26`  double NULL DEFAULT NULL ,\n" +
            "`DIF`  double NULL DEFAULT NULL ,\n" +
            "`EMAMACD`  double NULL DEFAULT NULL ,\n" +
            "`BAR`  double NULL DEFAULT NULL ," +
            " PRIMARY KEY (`stockDate`)" +
            ") " +
            "ENGINE=InnoDB " +
            "DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci " +
            "ROW_FORMAT=COMPACT ;")
    public void createTableByTableName(@Param("tableName") String tableName);



    @Select("  SELECT COUNT(*) as count FROM information_schema.TABLES WHERE table_name =#{tableName}")
    @Results(id = "isTableExistResults", value = {
            @Result(column = "count", property = "count")})
    public double isTableExist(String tableName);



    @Update("update stock_info_${stockCode} set k_value=#{kValue},d_value=#{dValue},j_value=#{jValue} where stockCode =#{stockCode} and  stockDate =#{stockDate}")
    public void updateStockInfo(StockInfo stockInfo);


    @Update("update stock_info_${stockCode} set EMA12=#{EMA12},EMA26=#{EMA26},DIF=#{DIF} ,EMAMACD=#{EMAMACD} ,BAR=#{BAR}  where stockDate =#{stockDate}")
    public void updateStockInfoMacd(StockInfo stockInfo);


    @Update("update stock_info_${stockCode} set k_value='${kValue}',d_value='${dValue}',j_value='${jValue}'  where stockDate ='${stockDate}'")
    public void updateStockInfoKDJ(StockInfo stockInfo);


    @Update("update stock_info_${stockCode} set RSI06='${RSI06}',RSI12='${RSI12}',RSI24='${RSI24}'  where stockDate ='${stockDate}'")
    public void updateStockInfoRSI(StockInfo stockInfo);



    @Update("update stock_info_${stockCode} set EMA12=#{EMA12},EMA26=#{EMA26},DIF=#{DIF} ,EMAMACD=#{EMAMACD} ,BAR=#{BAR} ")
    public void updateStockInfoMacdNoDate(StockInfo stockInfo);



    @Update("DROP TABLE  stock_info_${stockCode}")
    public void delTableByStockCode(StockInfo stockInfo);



    @Select("  SELECT COUNT(*) as count FROM stock_info_${stockCode}  WHERE stockDate =#{stockDate}")
    @Results(id = "isRoweExistResults", value = {
            @Result(column = "count", property = "count")})
    public double isRoweExist(StockInfo stockInfo);



    @Update("UPDATE stock_info_${stockCode} set stockCode =#{stockCode}")
    public void updateStockCode(@Param("stockCode") String stockCode);


    @Delete("delete from  stock_info_${stockCode} where stockDate='${stockDate}' ")
    public void delStockInfo(@Param("stockCode") String stockCode,@Param("stockDate") String stockDate);


    @Delete("delete from  stock_info_${stockCode} where kpj='0.0' ")
    public void delEmptyStockInfo(@Param("stockCode") String stockCode);


    @Update(" ALTER TABLE `stock_info_${stockCode}` ADD COLUMN `RSI06`  double NULL AFTER `BAR`, ADD COLUMN `RSI12`  double NULL AFTER `RSI06`, ADD COLUMN `RSI24`  double NULL AFTER `RSI12`;")
    public void alterTable(@Param("stockCode") String stockCode);






}
