package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 查看所有师父 和 用户
     * @param pageCurrentNo
     * @param pageSize
     * @param isSub       是否关注
     * @param detailId    0用户  1  师父
     * @return
     */
    List<User> selUserAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("isSub") Integer isSub , @Param("detailId") Integer detailId);

    /**
     * 总记录数
     * @param isSub
     * @param detailId
     * @return
     */
    int selUserTotal(@Param("isSub") Integer isSub ,@Param("detailId") Integer detailId);

    User selUserByOpenId(@Param("openId") String openId);
    User selUserById(@Param("id") Integer id);
    User selUserByDetailId(@Param("detailId") Integer detailId);
    //修改
    int updateUser(User user);
    //修改
    int updateUserByOpenId(User user);
    int delUser(Integer id);
    //新用户
    List<User> UserAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize);
    int userTotal();
}
