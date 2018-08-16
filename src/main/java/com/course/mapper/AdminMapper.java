package com.course.mapper;

import com.course.entity.Administrator;
import com.course.entity.Student;
import com.course.entity.Teacher;
import org.apache.ibatis.annotations.*;

/**
 * Created by linxiao on 2018/8/14.
 */
public interface AdminMapper {
    /**
     * 插入
     * @param admin
     * @return
     */
    @Insert({"insert into administrator(loginname, password, username, gender,mobile,status,createtime) values(#{loginName}, #{password}, #{userName}, #{gender}, #{mobile}, #{status}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAdmin(Administrator admin);

    /**
     * 更新
     * @param admin
     * @return
     */
    @Update("update administrator set loginname=#{loginName}, password=#{password}, username=#{userName}, gender=#{gender},mobile=#{mobile} WHERE id=#{id}")
    int updateAdmin(Administrator admin);

    @Select("SELECT * FROM administrator WHERE loginname=#{loginName}")
    Administrator adminByLoginName(String loginName);
}
