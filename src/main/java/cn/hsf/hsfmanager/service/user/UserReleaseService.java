package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserRelease;

import java.util.List;

public interface UserReleaseService {
    //分页
    List<UserRelease> selUserReleaseAll(Integer pageCurrentNo, Integer pageSize,Integer state,Integer mark);
    int selUserReleaseTotal(  Integer state,Integer mark);

    UserRelease selUserReleaseById( Integer id);
    int updateUserRelease(UserRelease userRelease);

    int updateUserRelease2(UserRelease userRelease,String date);

    int delRelById(Integer[] array);
}
