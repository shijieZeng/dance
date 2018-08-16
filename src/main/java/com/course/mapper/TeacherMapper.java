package com.course.mapper;

import com.course.entity.Student;
import com.course.entity.Teacher;
import com.course.vo.param.TeacherResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by linxiao on 2018/8/14.
 */
public interface TeacherMapper {
    /**
     * 根据id获取学员信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM teacher WHERE id=#{id}")
    TeacherResp teacherById(Integer id);

    /**
     * 查询所有
     * @return
     */
    @Select("SELECT * FROM teacher order by id")
    List<TeacherResp> teacherAll();

    /**
     * 插入
     * @param teacher
     * @return
     */
    @Insert({"insert into teacher(loginname, password, username, gender,mobile,address,remark,status,createtime) values(#{loginName}, #{password}, #{userName}, #{gender}, #{mobile}, #{address}, #{remark}, #{status}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTeacher(Teacher teacher);

    /**
     * 更新
     * @param teacher
     * @return
     */
    @Update("update teacher set loginname=#{loginName}, password=#{password}, username=#{userName}, gender=#{gender},mobile=#{mobile},address=#{address},remark=#{remark} WHERE id=#{id}")
    int updateTeacher(Teacher teacher);

    /**
     *  根据用户名获取学员信息
     * @param loginName
     * @return
     */
    @Select("SELECT * FROM teacher WHERE loginname=#{loginName}")
    Teacher teacherByLoginName(String loginName);
}
