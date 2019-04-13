package com.stock.mapper;

import com.stock.bean.po.StockList;
import com.stock.bean.po.WebDiary;
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
public interface WebDiaryMapper {

    @Select("select * from web_diary where 1=1 ")
    @Results(id = "webDiaryResults", value = {
                @Result(column = "text", property = "text"),
                @Result(column = "imgtext", property = "imgtext"),
                @Result(column = "createdate", property = "createdate"),
                @Result(column = "id", property = "id")
                }
            )
     List<WebDiary> getDiaryAll();

    @Select("select * from web_diary where 1=1 and id =#{id}")
    @ResultMap("webDiaryResults")
     WebDiary getDiaryById(@Param("id") String id);

}
