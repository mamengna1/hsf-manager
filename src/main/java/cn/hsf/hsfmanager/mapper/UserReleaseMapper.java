package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.UserRelease;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserReleaseMapper {

    //分页
    List<UserRelease> selUserReleaseAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize,@Param("state") Integer state);
    int selUserReleaseTotal( @Param("state") Integer state);

    UserRelease selUserReleaseById(@Param("id") Integer id);
    int updateUserRelease(UserRelease userRelease);

}
