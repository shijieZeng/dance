package com.course.controller;

import com.course.service.StudentService;
import com.course.vo.ResResult;
import com.course.vo.param.CourseRecordResp;
import com.course.vo.param.CourseResp;
import com.course.vo.param.StudentReq;
import com.course.vo.param.StudentResp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


/**
 * Created by linxiao on 2018/8/10.
 */
@RestController()
@RequestMapping("student")
public class StudentController {
    private static Logger logger = LogManager.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    /**
     * 添加学员
     * @param studentReq
     * @return
     */
    @PostMapping("/saveStudent")
    public ResResult insertStudent(@Valid @RequestBody StudentReq studentReq) {
        logger.debug("POST /student/saveStudent params {{}}", studentReq);

        studentService.insertStudent(studentReq);

        return ResResult.success();
    }

    /**
     * 更新学员
     * @param studentReq
     * @param id
     * @return
     */
    @PostMapping("/updaeStudent")
    public ResResult updaeStudent(@Valid @RequestBody StudentReq studentReq, @RequestParam("id") int id) {
        logger.debug("POST /student/updaeStudent params {id={},{}}",
            id,studentReq);

        studentService.updateStudent(studentReq,id);
        return ResResult.success();
    }

    /**
     * 查询学员信息
     * @param id
     * @return
     */
    @GetMapping("/studentById")
    public ResResult studentById(@RequestParam("id") int id) {
        logger.debug("GET /student/studentById params {id={}}", id);

        StudentResp student = studentService.studentById(id);
        ResResult resResult = new ResResult(0, "success");
        if (student == null) {
            resResult.setData("");
        } else {
            resResult.setData(student);
        }

        return resResult;
    }

    /**
     * 所有学员
     * @return
     */
    @GetMapping("/studentAll")
    public ResResult studentAll() {
        logger.debug("POST /student/studentAll params {}");

        List<StudentResp> studentList = studentService.studentAll();
        ResResult resResult = new ResResult(0, "success");
        if (studentList == null || studentList.size() <= 0) {
            resResult.setData("");
        } else {
            resResult.setData(studentList);
        }

        return resResult;
    }

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResResult login(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        logger.debug("POST /student/login params {loginName={},password={}}",
                loginName,password);

        int code = studentService.studentByLoginName(loginName,password);
        ResResult resResult = new ResResult();

        if (code == -1) {
            resResult.setCode(-1);
            resResult.setMsg("用户名或密码错误");
            resResult.setData("");
            return resResult;
        }
        HttpSession session = getRequest().getSession();
        session.setAttribute("studentId", code);

        return ResResult.success();
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    /**
     * 预定课程
     * @param courseId
     * @return
     */
    @PostMapping("/reserveCourse")
    public ResResult reserveCourse(@RequestParam("cid") int courseId) {
        logger.debug("POST /student/reserveCourse params {courseId={}}",
                courseId);
        HttpSession session = getRequest().getSession();
        Object sid = session.getAttribute("studentId");
        ResResult resResult = new ResResult();
        if (sid == null) {
            resResult.setCode(-1);
            resResult.setMsg("请登录");
            resResult.setData("");
            return resResult;
        }
        int studentId = (int)sid;

        int code = studentService.reserveCourse(studentId, courseId);
        if (code == -1) {
            resResult.setCode(-1);
            resResult.setMsg("次数不够，请充值");
        }else if (code == -2){
            resResult.setCode(-2);
            resResult.setMsg("已经预约过了，不要重复预约");
        }else if (code == -3) {
            resResult.setCode(-3);
            resResult.setMsg("人数已满");
        } else {
            resResult.setCode(0);
            resResult.setMsg("success");
        }
        resResult.setData("");

        return resResult;
    }

    /**
     * 取消课程
     * @param crId
     * @return
     */
    @PostMapping("/cancelCourse")
    public ResResult cancelCourse(@RequestParam("crId") int crId) {
        logger.debug("POST /student/cancelCourse params {id={}}", crId);

        int code = studentService.cancelCourse(crId);
        ResResult resResult = new ResResult();

        if (code == -1) {
            resResult.setCode(-1);
            resResult.setMsg("记录不存在");
            resResult.setData("");
            return resResult;
        }

        return ResResult.success();
    }

    /**
     * 预约成功的课程
     * @param
     * @return
     */
    @GetMapping("/successCourse")
    public ResResult studentSuccessCourse() {
        logger.debug("GET /student/successCourse params {}");

        HttpSession session = getRequest().getSession();
        Object sid = session.getAttribute("studentId");
        ResResult resResult = new ResResult(0, "success");
        if (sid == null) {
            resResult.setCode(-1);
            resResult.setMsg("请登录");
            resResult.setData("");
            return resResult;
        }
        int studentId = (int)sid;

        List<CourseRecordResp> studentList = studentService.studentSuccessCourse(studentId);

        if (studentList == null || studentList.size() <= 0) {
            resResult.setData("");
        } else {
            resResult.setData(studentList);
        }

        return resResult;
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping("/change_pwd")
    public ResResult change_pwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd) {
        logger.debug("POST /student/change_pwd params {oldPwd={},newPwd={}}",
                oldPwd,newPwd);

        HttpSession session = getRequest().getSession();
        Object sid = session.getAttribute("studentId");
        ResResult resResult = new ResResult();
        if (sid == null) {
            resResult.setCode(-1);
            resResult.setMsg("请登录");
            resResult.setData("");
            return resResult;
        }
        int studentId = (int)sid;

        int code = studentService.changePwd(studentId, oldPwd,newPwd);

        if (code == -1) {
            resResult.setCode(-1);
            resResult.setMsg("原密码错误");
            resResult.setData("");
            return resResult;
        }

        return ResResult.success();
    }

}
