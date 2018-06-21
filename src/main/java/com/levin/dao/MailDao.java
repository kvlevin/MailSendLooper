package com.levin.dao;

import com.levin.Email;
import com.levin.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import javax.swing.text.StyleContext;

public interface MailDao {



    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "contentFile", column = "content_file"),
            @Result(property = "lastModify", column = "last_modify"),
    })
    @Select("select * from email_content order by last_modify,id desc limit 1")
    Email selectLastOne();

    @Insert("insert into email_content (content_file, last_modify) values (#{contentFile}, #{lastModify})")
    int saveEmail(Email email);


    @Insert("insert into send_log (content_id,sender, receiver, response_content, moment)values (#{content_id},#{sender},#{receiver},#{response_content},#{moment})")
    int insertLog(Log log);
}
