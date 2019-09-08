package com.wallly.startboot.Mapper;

import com.wallly.startboot.Model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {
    @Update("update comment set content_count = content_count + #{contentCount} where id = #{id}")
    void incContentCount(Comment contentCount);

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,content) values (#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectById(Integer id);

    @Select("select * from comment where parent_id = #{id} and type = #{type} order by gmt_create desc")
    List<Comment> listByQuestionId(@Param(value = "id") Integer id, @Param(value = "type") Integer type);
}
