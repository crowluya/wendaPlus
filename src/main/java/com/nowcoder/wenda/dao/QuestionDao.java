package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11.
 */
@Mapper
public interface QuestionDao {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = " title, content, created_date,user_id,comment_count";
    String SELECT_FIELDS = "id" + INSERT_FIELDS;
    @Insert({"insert into ", TABLE_NAME," (",INSERT_FIELDS,
            ") values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);




  //  @Update({"update", TABLE_NAME, "set password = #{password} where id = #{id}"})
    List<Question> selectLatestQuestions (@Param("userId" ) int userId,
                                             @Param("offset") int offset,
                                             @Param("limit") int limit);



    @Select({"select", SELECT_FIELDS," from ", TABLE_NAME,"where id = #{id}"})
    Question selectedById(int id);
    @Delete({"delete from", TABLE_NAME, "where id = #{id}"})
    void deleteById(int id);
}
