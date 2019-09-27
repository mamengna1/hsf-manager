package cn.hsf.hsfmanager.service.admin;

import cn.hsf.hsfmanager.pojo.Admin;

import java.util.List;

public interface AdminService {

    //验证用户名和密码
    Admin checkLogin(Admin admin);

    List<Admin> selListAdmin(Admin admin);

    int saveAdmin(Admin admin);
    Admin selAdmin(Admin admin);
    int updAdmin(Admin admin);
    int delAdmin(Integer id);
    List<String> selAccountOpenId();
}
