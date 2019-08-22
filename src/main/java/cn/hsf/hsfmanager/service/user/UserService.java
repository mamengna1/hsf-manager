package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.User;

import java.util.List;

public interface UserService {

    /**
     * 查看所有师父 和 用户
     * @param pageCurrentNo
     * @param pageSize
     * @param isSub       是否关注
     * @param detailId    0用户  1  师父
     * @return
     */
    List<User> selUserAll(Integer pageCurrentNo, Integer pageSize, Integer isSub ,  Integer detailId);

    /**
     * 总记录数
     * @param isSub
     * @param detailId
     * @return
     */
    int selUserTotal( Integer isSub ,Integer detailId);

    User selUserByOpenId( String openId);
    User selUserById( Integer id);
    User selUserByDetailId( Integer detailId);

    //修改
    int updateUser(User user);
    int updateUserByOpenId(User user);
    int delUser(Integer id);
}
