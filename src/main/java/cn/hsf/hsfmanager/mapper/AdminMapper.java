package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {

    //验证用户名和密码
    Admin checkLogin(Admin admin);

    List<Admin> selListAdmin(Admin admin);

    int saveAdmin(Admin admin);

    Admin selAdmin(Admin admin);

    int updAdmin(Admin admin);

    int delAdmin(@Param("id") Integer id);

    List<String> selAccountOpenId();
}
