package com.levin.dao;

import com.levin.TargetEmail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TargetEmailDao {

    @Insert("insert into target_mail (email, is_valid, refuse_count, last_send_time) values (#{email},0,0,0)")
    int saveTargetEmail(TargetEmail targetEmail);

    @Select("select * from target_mail where not exists (select 1 from send_log where content_id=#{id} and send_log.receiver=target_mail.email) order by last_send_time limit 1 ")
    TargetEmail getLastEmail(long id);

    @Update("update target_mail set last_send_time=#{lastSendTime},is_valid=#{isValid},refuse_count=#{refuseCount} where email=#{email}")
    int updateTargetEmail(TargetEmail lastEmail);
}
