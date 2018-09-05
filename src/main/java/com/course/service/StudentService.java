package com.course.service;

import com.course.entity.Student;
import com.course.vo.param.CourseRecordResp;
import com.course.vo.param.CourseResp;
import com.course.vo.param.StudentReq;
import com.course.vo.param.StudentResp;

import java.util.List;

/**
 * Created by linxiao on 2018/8/10.
 */
public interface StudentService {
    /**
     * 根据id获取学员信息
     * @param id
     * @return
     */
    StudentResp studentById(Integer id);

    /**
     * 查询所有
     * @return
     */
    List<StudentResp> studentAll();

    /**
     * 插入
     * @param studentReq
     * @return
     */
    int insertStudent(StudentReq studentReq);

    /**
     * 更新
     * @param studentReq
     * @return
     */
    int updateStudent(StudentReq studentReq, int id);

    /**
     *  根据登录名获取学员信息
     * @param loginName
     * @return
     */
    int studentByLoginName(String loginName, String password);

    /**
     * 预约课程
     * @param studentId
     * @param courseId
     * @return
     */
    int reserveCourse(int studentId, int courseId);

    /**
     *
     * @param id
     * @return
     */
    int cancelCourse(int id);

    /**
     *
     * @param studentId
     * @return
     */
    List<CourseRecordResp> studentSuccessCourse(Integer studentId);

}
