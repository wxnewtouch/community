package com.wallly.startboot.Mapper;

import com.wallly.startboot.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") String id);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},bio = #{bio},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
