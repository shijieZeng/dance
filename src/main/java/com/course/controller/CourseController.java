package com.course.controller;

import com.course.service.CourseService;
import com.course.vo.ResResult;
import com.course.vo.param.CourseResp;
import com.course.vo.param.StudentResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by linxiao on 2018/8/15.
 */
@RestController()
@RequestMapping("course")
public class CourseController {
    private static Logger logger = LogManager.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @GetMapping("/courseAll")
    public ResResult courseAll() {
        logger.debug("GET /course/courseAll params {}");

        List<CourseResp> courseList = courseService.coureseAll();
        ResResult resResult = new ResResult(0, "success");
        if (courseList == null || courseList.size() <= 0) {
            resResult.setData("");
        } else {
            resResult.setData(courseList);
        }

        return resResult;
    }

    @GetMapping("/getcourseByTeacherId")
    public ResResult getcourseByTeacherId(@RequestParam("teacherId") int teacherId) {
        logger.debug("GET /course/getcourseByTeacherId params {teacherId={}}", teacherId);

        List<CourseResp> courseList = courseService.getcourseByTeacherId(teacherId);
        ResResult resResult = new ResResult(0, "success");
        if (courseList == null || courseList.size() <= 0) {
            resResult.setData("");
        } else {
            resResult.setData(courseList);
        }
        return resResult;
    }

    @GetMapping("/getCourseById")
    public ResResult getCourseById(@RequestParam("tid") int tid) {
        logger.debug("GET /course/getCourseById params {teacherId={}}", tid);
        CourseResp courseResp = courseService.getCourseRespById(tid);
        ResResult resResult = new ResResult(0, "success");
        if (courseResp == null) {
            resResult.setData("");
        } else {
            resResult.setData(courseResp);
        }

        return resResult;

    }
}
