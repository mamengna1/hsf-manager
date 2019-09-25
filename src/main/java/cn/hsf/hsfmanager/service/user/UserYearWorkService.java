package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserYearWork;

import java.util.List;

public interface UserYearWorkService {

    UserYearWork selById(Integer id);
    List<UserYearWork> selUserYearWorkAll();

    int delUserYearWorkById(Integer id);
    int insUserYearWork(UserYearWork userYearWork);
    int updUserYearWork(UserYearWork userYearWork);
}
