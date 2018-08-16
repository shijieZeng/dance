package com.course.mapper;

import com.course.entity.Course;
import com.course.entity.CourseRecord;
import com.course.entity.Student;
import com.course.vo.param.CourseResp;
import com.course.vo.param.StudentResp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by linxiao on 2018/8/10.
 */
public interface UserMapper {
    /**
     * 根据id获取学员信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM student WHERE id=#{id}")
    StudentResp getStudentById(Integer id);

    /**
     * 查询所有
     * @return
     */
    @Select("SELECT * FROM student order by id")
    List<StudentResp> studentAll();

    /**
     * 插入
     * @param student
     * @return
     */
    @Insert({"insert into student(loginname, password, username, gender,mobile,address,remainnum,status,createtime) values(#{loginName}, #{password}, #{userName}, #{gender}, #{mobile}, #{address}, #{remainNum}, #{status}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertStudent(Student student);

    /**
     * 更新
     * @param student
     * @return
     */
    @Update("update student set loginname=#{student.loginName}, password=#{student.password}, username=#{student.userName}, gender=#{student.gender},mobile=#{student.mobile},address=#{student.address},remainnum=#{student.remainNum} WHERE id=#{student.id}")
    int updateStudent(@Param("student") Student student);

    /**
     *  根据用户名获取学员信息
     * @param loginName
     * @return
     */
    @Select("SELECT * FROM student WHERE loginname=#{loginName}")
    Student studentByLoginName(String loginName);

    @Insert({"insert into course_record(studentId, courseId, status,createtime) values(#{studentId}, #{courseId}, #{status}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCourseRecord(CourseRecord courseRecord);

    @Select("SELECT * FROM course_record WHERE studentId=#{studentId} AND courseId=#{courseId} AND status=#{status}")
    CourseRecord courseRecordById(@Param("studentId")int studentId, @Param("courseId")int courseId,@Param("status")int status);

    @Update("update course_record set status=#{status} WHERE id=#{id}")
    int cancelCourseRecord(@Param("status") int status,@Param("id") int id);

    @Select("SELECT * FROM course_record WHERE id=#{id}")
    CourseRecord getCourseRecordById(Integer id);

    @Select("SELECT c.* from course c, course_record cr WHERE c.ID=cr.courseId and cr.`status`=0 AND cr.studentId=#{studentId}")
    List<CourseResp> studentSuccessCourse(Integer studentId);
}
