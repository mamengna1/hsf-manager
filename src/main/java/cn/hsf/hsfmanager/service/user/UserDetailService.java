package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserDetail;

import java.util.List;

public interface UserDetailService {

    /**
     * 查看所有师父 详细信息
     * @param pageCurrentNo
     * @param pageSize
     * @param name       真实姓名
     * @return
     */
    List<UserDetail> selUserDetailAll( Integer pageCurrentNo, Integer pageSize,  String name,Integer status);

    /**
     * 总记录数
     * @param name       真实姓名
     * @return
     */
    int selUserDetailTotal(  String name,Integer status);


    UserDetail selUserDetailById( Integer id);

    int updateUserDetail(UserDetail userDetail);
}
