package com.course.service.impl;

import com.course.entity.Administrator;
import com.course.mapper.AdminMapper;
import com.course.service.AdminService;
import com.course.vo.param.AdminReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by linxiao on 2018/8/14.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int adminByLoginName(String loginName, String password) {
        Administrator admin = adminMapper.adminByLoginName(loginName);
        if (admin == null) return -1;

        if (!admin.getPassword().equals(password)) {
            return -1;
        }
        return 0;
    }

    @Override
    public int insertAdmin(AdminReq adminReq) {
        Administrator admin = new Administrator();

        admin.setLoginName(adminReq.getLoginName());
        admin.setPassword(adminReq.getPassword());
        admin.setUserName(adminReq.getUserName());
        admin.setGender(adminReq.getGender());
        admin.setMobile(adminReq.getMobile());
        admin.setStatus(0);
        admin.setCreateTime(new Date());
        adminMapper.insertAdmin(admin);
        return 0;
    }

    @Override
    public int updateAdmin(AdminReq adminReq, int id) {
        Administrator admin = new Administrator();

        admin.setLoginName(adminReq.getLoginName());
        admin.setPassword(adminReq.getPassword());
        admin.setUserName(adminReq.getUserName());
        admin.setGender(adminReq.getGender());
        admin.setMobile(adminReq.getMobile());
        admin.setId(id);
        adminMapper.updateAdmin(admin);

        return 0;
    }
}
