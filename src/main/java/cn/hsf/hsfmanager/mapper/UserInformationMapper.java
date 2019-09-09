package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.UserInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInformationMapper {

    int selTotal(Integer isDelete);

    List<UserInformation> selAll(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize, @Param("isDelete") Integer isDelete);

    UserInformation selById(Integer id);

    int updInfor(UserInformation userInformation);

    int delUserInfoByIds(@Param("array") Integer[] ids,@Param("delCause") String delCause);
}
