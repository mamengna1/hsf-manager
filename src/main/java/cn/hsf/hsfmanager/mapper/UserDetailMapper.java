package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.UserDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDetailMapper {

    /**
     * 查看所有师父 详细信息
     * @param pageCurrentNo
     * @param pageSize
     * @param name       真实姓名
     * @return
     */
    List<UserDetail> selUserDetailAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("name") String name, @Param("status") Integer status);

    /**
     * 总记录数
     * @param name       真实姓名
     * @return
     */
    int selUserDetailTotal( @Param("name") String name, @Param("status") Integer status);

    UserDetail selUserDetailById(@Param("id") Integer id);

    int updateUserDetail(UserDetail userDetail);
}
