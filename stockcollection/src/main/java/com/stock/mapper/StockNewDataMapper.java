package com.stock.mapper;

import com.stock.bean.StockInfo;
import com.stock.bean.StockInfoNew;
import com.stock.bean.StockNominateVo;
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




}
