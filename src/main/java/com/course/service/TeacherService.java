package com.course.service;

import com.course.vo.param.TeacherReq;
import com.course.vo.param.TeacherResp;

import java.util.List;

/**
 * Created by linxiao on 2018/8/14.
 */
public interface TeacherService {
    /**
     * 根据id获取老师信息
     * @param id
     * @return
     */
    TeacherResp teacherById(Integer id);
    /**
     * 查询所有
     * @return
     */
    List<TeacherResp> teacherAll();

    /**
     * 插入
     * @param teacherReq
     * @return
     */
    int insertTeacher(TeacherReq teacherReq);

    /**
     * 更新
     * @param teacherReq
     * @return
     */
    int updateTeacher(TeacherReq teacherReq, int id);

    /**
     *  根据登录名获取老师信息
     * @param loginName
     * @return
     */
    int teacherByLoginName(String loginName, String password);

}
