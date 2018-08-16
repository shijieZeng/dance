package com.course.service.impl;

import com.course.entity.Course;
import com.course.entity.CourseRecord;
import com.course.entity.Student;
import com.course.mapper.UserMapper;
import com.course.service.CourseService;
import com.course.service.StudentService;
import com.course.vo.param.CourseReq;
import com.course.vo.param.CourseResp;
import com.course.vo.param.StudentReq;
import com.course.vo.param.StudentResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by linxiao on 2018/8/13.
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourseService courseService;

    @Override
    public StudentResp studentById(Integer id) {
        return userMapper.getStudentById(id);
    }

    @Override
    public List<StudentResp> studentAll() {
        return userMapper.studentAll();
    }

    @Override
    public int insertStudent(StudentReq studentReq) {
        Student student = new Student();

        student.setLoginName(studentReq.getLoginName());
        student.setPassword(studentReq.getPassword());
        student.setUserName(studentReq.getUserName());
        student.setGender(studentReq.getGender());
        student.setMobile(studentReq.getMobile());
        student.setAddress(studentReq.getAddress());

        if (studentReq.getRemainNum() == null) {
            student.setRemainNum(0);
        }else {
            student.setRemainNum(studentReq.getRemainNum());
        }

        student.setStatus(0);
        student.setCreateTime(new Date());

        userMapper.insertStudent(student);
        return 0;
    }

    @Override
    public int updateStudent(StudentReq studentReq, int sid) {
        Student student = new Student();

        student.setLoginName(studentReq.getLoginName());
        student.setPassword(studentReq.getPassword());
        student.setUserName(studentReq.getUserName());
        student.setGender(studentReq.getGender());
        student.setMobile(studentReq.getMobile());
        student.setAddress(studentReq.getAddress());

        if (studentReq.getRemainNum() == null) {
            student.setRemainNum(0);
        }else {
            student.setRemainNum(studentReq.getRemainNum());
        }
        student.setId(sid);
        userMapper.updateStudent(student);

        return 0;
    }

    @Override
    public int studentByLoginName(String loginName,String password) {
        Student temp = userMapper.studentByLoginName(loginName);
        if (temp == null) return -1;

        if (!temp.getPassword().equals(password)) {
            return -1;
        }
        return temp.getId();
    }

    public int insertCourse(int studentId, int courseId) {
        CourseRecord courseRecord = new CourseRecord();
        courseRecord.setStudentId(studentId);
        courseRecord.setCourseId(courseId);
        courseRecord.setStatus(0);
        courseRecord.setCreateTime(new Date());
        userMapper.insertCourseRecord(courseRecord);
        return 0;
    }

    @Override
    public int reserveCourse(int studentId, int courseId) {
        StudentResp studentResp = this.studentById(studentId);
        if (studentResp == null || studentResp.getRemainNum().intValue() <= 0) {
            return -1;      //次数不够
        }
        CourseRecord courseRecord = userMapper.courseRecordById(studentId, courseId, 0);
        if (courseRecord != null && courseRecord.getStatus().intValue() == 0) {
            return -2;      //已经预约过了
        }
        Course course = courseService.getCourseById(courseId);
        if (course == null
                || course.getCurrNum().intValue() >= course.getNumLimit().intValue()) {
            return -3;  //已约满
        }

        StudentReq studentReq = new StudentReq();
        studentReq.setLoginName(studentResp.getLoginName());
        studentReq.setPassword(studentResp.getPassword());
        studentReq.setUserName(studentResp.getUserName());
        studentReq.setGender(studentResp.getGender());
        studentReq.setMobile(studentResp.getMobile());
        studentReq.setAddress(studentResp.getAddress());
        studentReq.setRemainNum(studentResp.getRemainNum()-1);  //次数减一
        this.updateStudent(studentReq, studentResp.getId());

        CourseReq courseReq = new CourseReq();
        courseReq.setCourseName(course.getCourseName());
        courseReq.setNote(course.getNote());
        courseReq.setBeginTime(course.getBeginTime());
        courseReq.setEndTime(course.getEndTime());
        courseReq.setTeacherId(course.getTeacherId());
        courseReq.setNumLimit(course.getNumLimit());
        courseReq.setCurrNum(course.getCurrNum()+1);//课程预约人数加一
        courseService.updateCourse(courseReq, course.getId());


        this.insertCourse(studentId, courseId);

        return 0;
    }

    @Override
    public int cancelCourse(int id) {
        CourseRecord courseRecord = userMapper.getCourseRecordById(id);
        if (courseRecord == null || courseRecord.getStatus().intValue() == 1) {
            return -1;  //记录不存在
        }
        StudentResp studentResp = this.studentById(courseRecord.getStudentId());
        StudentReq studentReq = new StudentReq();
        studentReq.setLoginName(studentResp.getLoginName());
        studentReq.setPassword(studentResp.getPassword());
        studentReq.setUserName(studentResp.getUserName());
        studentReq.setGender(studentResp.getGender());
        studentReq.setMobile(studentResp.getMobile());
        studentReq.setAddress(studentResp.getAddress());
        studentReq.setRemainNum(studentResp.getRemainNum()+1);  //次数加一
        this.updateStudent(studentReq, studentResp.getId());

        Course course = courseService.getCourseById(courseRecord.getCourseId());
        CourseReq courseReq = new CourseReq();
        courseReq.setCourseName(course.getCourseName());
        courseReq.setNote(course.getNote());
        courseReq.setBeginTime(course.getBeginTime());
        courseReq.setEndTime(course.getEndTime());
        courseReq.setTeacherId(course.getTeacherId());
        courseReq.setNumLimit(course.getNumLimit());
        courseReq.setCurrNum(course.getCurrNum()-1);//课程预约人数减一
        courseService.updateCourse(courseReq, course.getId());

        userMapper.cancelCourseRecord(1, id);

        return 0;
    }

    @Override
    public List<CourseResp> studentSuccessCourse(Integer studentId) {

        return userMapper.studentSuccessCourse(studentId);
    }
}
