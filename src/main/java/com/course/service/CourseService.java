package com.course.service;

import com.course.entity.Course;
import com.course.vo.param.AdminReq;
import com.course.vo.param.CourseReq;
import com.course.vo.param.CourseResp;

import java.util.List;

/**
 * Created by linxiao on 2018/8/14.
 */
public interface CourseService {
    /**
     *
     * @param courseReq
     * @return
     */
    int insertCourse(CourseReq courseReq);
    /**
     * 更新
     * @param courseReq
     * @return
     */
    int updateCourse(CourseReq courseReq, int id);

    /**
     *
     * @return
     */
    List<CourseResp> coureseAll();

    /**
     *
     * @param teacherId
     * @return
     */
    List<CourseResp> getcourseByTeacherId(Integer teacherId);

    /**
     *
     * @param id
     * @return
     */
    Course getCourseById(Integer id);
}
