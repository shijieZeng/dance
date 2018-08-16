package com.course.service;

import com.course.vo.param.AdminReq;

/**
 * Created by linxiao on 2018/8/14.
 */
public interface AdminService {
    /**
     *
     * @param loginName
     * @param password
     * @return
     */
     int adminByLoginName(String loginName, String password);
    /**
     * 插入
     * @param adminReq
     * @return
     */
    int insertAdmin(AdminReq adminReq);

    /**
     * 更新
     * @param adminReq
     * @return
     */
    int updateAdmin(AdminReq adminReq, int id);
}
