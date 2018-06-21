package com.levin.dao;

import com.levin.Sender;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SenderDao {


    @Update("update sender_email set last_send_time=#{1} where email=#{0}")
    int updateLastTime(String email,long time);


    @Insert("insert into sender_email (email, password,send_server) values (#{email},#{password},#{send_server})")
    int saveSender(Sender sender);

    @Select("select * from sender_email order by last_send_time desc ")
    List<Sender> getSenderList();
}
