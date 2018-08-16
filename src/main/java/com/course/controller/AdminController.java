package com.course.controller;

import com.course.service.AdminService;
import com.course.service.CourseService;
import com.course.vo.ResResult;
import com.course.vo.param.AdminReq;
import com.course.vo.param.CourseReq;
import com.course.vo.param.TeacherReq;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by linxiao on 2018/8/14.
 */
@RestController()
@RequestMapping("admin")
public class AdminController {
    private static Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveAdmin")
    public ResResult saveAdmin(@Valid @RequestBody AdminReq adminReq) {
        logger.debug("POST /admin/saveAdmin params {{}}", adminReq);

        adminService.insertAdmin(adminReq);

        return ResResult.success();
    }

    /**
     * 更新学员
     * @param adminReq
     * @param id
     * @return
     */
    @PostMapping("/updateAdmin")
    public ResResult updateAdmin(@Valid @RequestBody AdminReq adminReq, @RequestParam("id") int id) {
        logger.debug("POST /admin/updateAdmin params {id={},{}}",
                id,adminReq);

        adminService.updateAdmin(adminReq,id);
        return ResResult.success();
    }

    @PostMapping("/login")
    public ResResult login(@RequestParam("loginName") String loginName, @RequestParam("password") String password) {
        logger.debug("POST /admin/login params {loginName={},password={}}",
                loginName,password);

        int code = adminService.adminByLoginName(loginName,password);

        if (code == -1) {
            ResResult resResult = new ResResult();
            resResult.setCode(-1);
            resResult.setMsg("用户名或密码错误");
            resResult.setData("");
            return resResult;
        }

        return ResResult.success();
    }


    @PostMapping("/saveCourse")
    public ResResult saveCourse(@Valid @RequestBody CourseReq courseReq) {
        logger.debug("POST /admin/saveCourse params {{}}", courseReq);

        int code = courseService.insertCourse(courseReq);
        if (code == -1) {
            ResResult resResult = new ResResult();
            resResult.setCode(-1);
            resResult.setMsg("日期格式错误");
            resResult.setData("");
            return resResult;
        }
        return ResResult.success();
    }

    @PostMapping("/updateCourse")
    public ResResult updateCourse(@Valid @RequestBody CourseReq courseReq, @RequestParam("id") int id) {
        logger.debug("POST /admin/updateCourse params {id={},{}}",
                id,courseReq);

        int code = courseService.updateCourse(courseReq,id);
        if (code == -1) {
            ResResult resResult = new ResResult();
            resResult.setCode(-1);
            resResult.setMsg("日期格式错误");
            resResult.setData("");
            return resResult;
        }
        return ResResult.success();
    }

}
