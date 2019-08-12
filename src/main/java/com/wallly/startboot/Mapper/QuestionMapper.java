package com.wallly.startboot.Mapper;

import com.wallly.startboot.Model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {
    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{id}")
    Integer countByAccountId(@Param(value = "id") Integer id);

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question where id = #{id}")
    Question findById(Integer id);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where creator = #{id} limit #{offset},#{size}")
    List<Question> listProfile(@Param(value = "id") Integer id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Update("update question set title = #{title},description = #{description},tag = #{tag} where id = #{id}")
    void update(Question question);

    @Update("update question set view_count = #{viewCount} + 1 where id = #{id}")
    void updateByExampleSelective(Question question);

    @Update("update question set comment_count = #{commentCount} + 1 where id = #{id}")
    void updateCommentCount(Question question);
}
