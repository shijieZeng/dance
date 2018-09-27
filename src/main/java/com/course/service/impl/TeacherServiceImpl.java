package com.course.service.impl;

import com.course.entity.Teacher;
import com.course.mapper.TeacherMapper;
import com.course.service.TeacherService;
import com.course.vo.param.TeacherReq;
import com.course.vo.param.TeacherResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by linxiao on 2018/8/14.
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public TeacherResp teacherById(Integer id) {
        return teacherMapper.teacherById(id);
    }

    @Override
    public List<TeacherResp> teacherAll() {
        return teacherMapper.teacherAll();
    }

    @Override
    public int insertTeacher(TeacherReq teacherReq) {
        Teacher teacher = new Teacher();

        teacher.setLoginName(teacherReq.getLoginName());
        teacher.setPassword(teacherReq.getPassword());
        teacher.setUserName(teacherReq.getUserName());
        teacher.setGender(teacherReq.getGender());
        teacher.setMobile(teacherReq.getMobile());
        teacher.setAddress(teacherReq.getAddress());
        teacher.setRemark(teacherReq.getRemark());

        teacher.setStatus(0);
        teacher.setCreateTime(new Date());
        teacherMapper.insertTeacher(teacher);

        return 0;
    }

    @Override
    public int updateTeacher(TeacherReq teacherReq, int id) {
        Teacher teacher = new Teacher();

        teacher.setLoginName(teacherReq.getLoginName());
        teacher.setPassword(teacherReq.getPassword());
        teacher.setUserName(teacherReq.getUserName());
        teacher.setGender(teacherReq.getGender());
        teacher.setMobile(teacherReq.getMobile());
        teacher.setAddress(teacherReq.getAddress());
        teacher.setRemark(teacherReq.getRemark());
        teacher.setId(id);
        teacherMapper.updateTeacher(teacher);
        return 0;
    }

    @Override
    public int teacherByLoginName(String loginName, String password) {
        Teacher teacher = teacherMapper.teacherByLoginName(loginName);
        if (teacher == null) return -1;

        if (!teacher.getPassword().equals(password)) {
            return -1;
        }
        return teacher.getId();
    }
	
	@Override
    public int changePwd(int teacherId, String oldPwd, String newPwd) {
        TeacherResp teacherResp = this.teacherById(teacherId);
		TeacherReq teacherReq = new TeacherReq();
        if (!teacherReq.getPassword().equals(oldPwd)) {
            return -1;
        }

//        TeacherReq teacherReq = new TeacherReq();
        teacherReq.setLoginName(teacherResp.getLoginName());
        teacherReq.setPassword(newPwd);
        teacherReq.setUserName(teacherResp.getUserName());
        teacherReq.setGender(teacherResp.getGender());
        teacherReq.setMobile(teacherResp.getMobile());
        teacherReq.setAddress(teacherResp.getAddress());
        teacherReq.setRemark(teacherResp.getRemark());
        this.updateTeacher(teacherReq, teacherResp.getId());

        return 0;
    }
}
