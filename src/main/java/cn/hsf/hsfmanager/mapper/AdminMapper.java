package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.Admin;

public interface AdminMapper {

    //验证用户名和密码
    Admin checkLogin(Admin admin);

}
