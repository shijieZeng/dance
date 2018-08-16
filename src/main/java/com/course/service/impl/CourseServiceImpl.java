package com.course.service.impl;

import com.course.entity.Course;
import com.course.mapper.CourseMapper;
import com.course.service.CourseService;
import com.course.vo.param.CourseReq;
import com.course.vo.param.CourseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by linxiao on 2018/8/14.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public int insertCourse(CourseReq courseReq) {
        Course course = new Course();

        course.setCourseName(courseReq.getCourseName());
        course.setNote(courseReq.getNote());
        course.setBeginTime(courseReq.getBeginTime());
        course.setEndTime(courseReq.getEndTime());
        course.setTeacherId(courseReq.getTeacherId());

        //milliseconds
        long time = courseReq.getEndTime().getTime()-courseReq.getBeginTime().getTime();
        time = time / 60000;    //分钟
        if (time < 30 || time > 300) { //上课时间在30-300之间
            return -1;
        }
        course.setDuration((int)time);

        if (courseReq.getNumLimit() == null) {
            course.setNumLimit(15); //默认15
        } else {
            course.setNumLimit(courseReq.getNumLimit());
        }
        course.setStatus(0);
        course.setCreateTime(new Date());
        courseMapper.insertCourse(course);

        return 0;
    }

    @Override
    public int updateCourse(CourseReq courseReq, int id) {
        Course course = new Course();

        course.setCourseName(courseReq.getCourseName());
        course.setNote(courseReq.getNote());
        course.setBeginTime(courseReq.getBeginTime());
        course.setEndTime(courseReq.getEndTime());
        course.setTeacherId(courseReq.getTeacherId());

        //milliseconds
        long time = courseReq.getEndTime().getTime()-courseReq.getBeginTime().getTime();
        time = time / 60000;    //分钟
        if (time < 30 || time > 300) { //上课时间在30-300之间
            return -1;
        }
        course.setDuration((int)time);

        if (courseReq.getNumLimit() == null) {
            course.setNumLimit(15); //默认15
        } else {
            course.setNumLimit(courseReq.getNumLimit());
        }

        if (courseReq.getCurrNum() == null) {
            course.setCurrNum(0);
        } else {
            course.setCurrNum(courseReq.getCurrNum());
        }
        course.setId(id);
        courseMapper.updateCourse(course);

        return 0;
    }

    @Override
    public List<CourseResp> coureseAll() {
        LocalDate now = LocalDate.now();
        LocalDate end = now.plusDays(7);

        List<CourseResp> list = courseMapper.courseAll(now.toString(), end.toString());
        for (CourseResp resp : list) {
            if (resp.getCurrNum().intValue() >= resp.getNumLimit().intValue()) {
                resp.setStatus(1); //已约满
            }else {
                resp.setStatus(0); //可预约
            }
        }
        return list;
    }

    @Override
    public List<CourseResp> getcourseByTeacherId(Integer teacherId) {
        List<CourseResp> list = courseMapper.getcourseByTeacherId(teacherId);
        for (CourseResp resp : list) {
            if (resp.getCurrNum().intValue() >= resp.getNumLimit().intValue()) {
                resp.setStatus(1); //已约满
            }else {
                resp.setStatus(0); //可预约
            }
        }
        return list;
    }

    @Override
    public Course getCourseById(Integer id) {
        return courseMapper.getcourseById(id);
    }
}
