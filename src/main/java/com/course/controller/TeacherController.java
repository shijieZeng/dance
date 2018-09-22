package com.course.controller;

import com.course.service.CourseService;
import com.course.service.TeacherService;
import com.course.vo.ResResult;
import com.course.vo.param.CourseResp;
import com.course.vo.param.TeacherReq;
import com.course.vo.param.TeacherResp;
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
 * Created by linxiao on 2018/8/14.
 */
@RestController()
@RequestMapping("teacher")
public class TeacherController {
    private static Logger logger = LogManager.getLogger(TeacherController.class);

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;
    /**
     * 添加学员
     * @param teacherReq
     * @return
     */
    @PostMapping("/saveTeacher")
    public ResResult saveTeacher(@Valid @RequestBody TeacherReq teacherReq) {
        logger.debug("POST /teacher/saveTeacher params {{}}", teacherReq);

        teacherService.insertTeacher(teacherReq);

        return ResResult.success();
    }

    /**
     * 更新学员
     * @param teacherReq
     * @param id
     * @return
     */
    @PostMapping("/updateTeacher")
    public ResResult updateTeacher(@Valid @RequestBody TeacherReq teacherReq, @RequestParam("id") int id) {
        logger.debug("POST /teacher/updateTeacher params {id={},{}}",
                id,teacherReq);

        teacherService.updateTeacher(teacherReq,id);
        return ResResult.success();
    }

    /**
     * 查询学员信息
     * @param id
     * @return
     */
    @GetMapping("/teacherById")
    public ResResult teacherById(@RequestParam("id") int id) {
        logger.debug("POST /teacher/teacherById params {id={}}", id);

        TeacherResp teacher = teacherService.teacherById(id);
        ResResult resResult = new ResResult(0, "success");
        if (teacher == null) {
            resResult.setData("");
        } else {
            resResult.setData(teacher);
        }

        return resResult;
    }

    /**
     * 所有学员
     * @return
     */
    @GetMapping("/teacherAll")
    public ResResult teacherAll() {
        logger.debug("GET /teacher/teacherAll params {}");

        List<TeacherResp> teacherList = teacherService.teacherAll();
        ResResult resResult = new ResResult(0, "success");
        if (teacherList == null || teacherList.size() <= 0) {
            resResult.setData("");
        } else {
            resResult.setData(teacherList);
        }

        return resResult;
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attrs =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    @PostMapping("/login")
    public ResResult login(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        logger.debug("POST /teacher/login params {loginName={},password={}}",
                loginName,password);

        int code = teacherService.teacherByLoginName(loginName,password);
        ResResult resResult = new ResResult();

        if (code == -1) {
            resResult.setCode(-1);
            resResult.setMsg("用户名或密码错误");
            resResult.setData("");
            return resResult;
        }
        HttpSession session = getRequest().getSession();
        session.setAttribute("teacherId", code);
        return ResResult.success();
    }

    @GetMapping("/successCourse")
    public ResResult successCourse() {
        logger.debug("POST /teacher/successCourse params {}");

        HttpSession session = getRequest().getSession();
        Object sid = session.getAttribute("teacherId");
        ResResult resResult = new ResResult(0, "success");
        if (sid == null) {
            resResult.setCode(-1);
            resResult.setMsg("请登录");
            resResult.setData("");
            return resResult;
        }
        int teacherId = (int)sid;

        List<CourseResp> courseList = courseService.getcourseByTeacherId(teacherId);
        if (courseList == null || courseList.size() <= 0) {
            resResult.setData("");
        } else {
            resResult.setData(courseList);
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
        logger.debug("POST /teacher/change_pwd params {oldPwd={},newPwd={}}",
                oldPwd,newPwd);

        HttpSession session = getRequest().getSession();
        Object tid = session.getAttribute("teacherId");
        ResResult resResult = new ResResult();
        if (tid == null) {
            resResult.setCode(-1);
            resResult.setMsg("请登录");
            resResult.setData("");
            return resResult;
        }
        int teacherId = (int)tid;

        int code = studentService.changePwd(teacherId, oldPwd,newPwd);

        if (code == -1) {
            resResult.setCode(-1);
            resResult.setMsg("原密码错误");
            resResult.setData("");
            return resResult;
        }

        return ResResult.success();
    }
}
