package cn.hsf.hsfmanager.service.admin;

import cn.hsf.hsfmanager.pojo.Admin;

public interface AdminService {

    //验证用户名和密码
    Admin checkLogin(Admin admin);
}
