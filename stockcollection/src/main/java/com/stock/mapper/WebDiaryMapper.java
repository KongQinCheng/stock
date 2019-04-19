package com.stock.mapper;

import com.stock.bean.po.WebDiary;
import com.stock.bean.vo.DiaryVo;
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

    @Select("select * from web_diary where 1=1 order by createdate desc ,id desc")
    @Results(id = "webDiaryResults", value = {
                @Result(column = "text", property = "text"),
                @Result(column = "imgtext", property = "imgtext"),
                @Result(column = "createdate", property = "createdate"),
                @Result(column = "person", property = "person"),
                @Result(column = "id", property = "id")
                }
            )
     List<WebDiary> getDiaryAll();


    @Select("select * from web_diary where 1=1 order by createdate desc  limit ${beginIndex},${endIndex}")
    @ResultMap("webDiaryResults")
    List<WebDiary> getDiaryByIndex(DiaryVo diaryVo);

    @Select("select * from web_diary where 1=1 and id =#{id}")
    @ResultMap("webDiaryResults")
     WebDiary getDiaryById(@Param("id") String id);

    @Insert("insert into web_diary (createdate,text,imgtext,address,person) values ('${createdate}','${text}','${imgtext}','${address}','${person}')")
     void addToTable(WebDiary webDiary );


    @Update("update  web_diary set createdate ='${createdate}' where id =${id} ")
     void updateToTable(WebDiary webDiary );

    @Select("select count(*) as count from web_diary where 1=1 and createdate='${date}'  and text like '%${text}%' ")
    @Results(id = "webDiaryCountResults", value = {
            @Result(column = "count", property = "count")}
    )
    int isExitByText(@Param("text") String text,@Param("date") String date);




}
