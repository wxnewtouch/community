package com.wallly.startboot.Mapper;

import com.wallly.startboot.Model.Notification;
import com.wallly.startboot.Model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outerid,type,gmt_create,status,notifier_name,outer_title) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver = #{id}")
    Integer countByAccountId(@Param(value = "id") Integer id);

    @Select("select * from notification where receiver = #{id} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> listProfile(@Param(value = "id") Integer id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from notification where id = #{id}")
    Notification query(@Param(value = "id") Integer id);

    @Update("update notification set status = #{status} where id = #{id}")
    void update(Notification notification);

    @Select("select count(1) from notification where receiver = #{id} and status = 0")
    Integer countUnread(Integer id);
}
