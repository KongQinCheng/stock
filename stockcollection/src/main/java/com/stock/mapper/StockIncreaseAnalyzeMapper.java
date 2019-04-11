package com.stock.mapper;

import com.stock.bean.po.StockIncreaseAnalyze;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


/**
 * moduleInfo的持久层接口
 *
 * @author chenzuyi
 * @version Revision 1.0.0
 * *修改时间         | 修改内容
 */

@Mapper
public interface StockIncreaseAnalyzeMapper {

    @Insert("insert into `stock_increase_analyze` (stockCode,stockDate,increase1,increase2,increase3,increase4,increase5,avg1,avg2,avg3,avg4,avg5,avg6,avg7,avg8,avg9,avg10,avg11,avg12,avg13,avg14,,avg15,avg16,avg17,avg18,avg19,avg20,avg21,avg22,avg23,avg24,avg25,avg26,avg27,avg28,avg29,avg30) "
            + "values(#{stockCode},'${stockDate}',${stockDate},${increase1},${increase2},${increase3},${increase4},${increase5},${avg1},${avg2},${avg3},${avg4},${avg5},${avg6},${avg7},${avg8},${avg9},${avg10},${avg11},${avg12},${avg13},${avg14},${avg15},${avg16},${avg17},${avg18},${avg19},${avg20},${avg21},${avg22},${avg23},${avg24},${avg25},${avg26},${avg27},${avg28},${avg29},${avg30})")
    public void insert(StockIncreaseAnalyze StockIncreaseAnalyze);


    public void deleteAll();


}
