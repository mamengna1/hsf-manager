package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserInformation;

import java.util.List;

public interface UserInformationService {

    int selTotal(Integer isDelete);

    List<UserInformation> selAll(Integer pageNo, Integer pageSize, Integer isDelete);

    UserInformation selById(Integer id);

    int updInfor(UserInformation userInformation);
}
