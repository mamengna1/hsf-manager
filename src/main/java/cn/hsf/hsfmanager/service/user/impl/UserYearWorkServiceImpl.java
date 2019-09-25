package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserYearWorkMapper;
import cn.hsf.hsfmanager.pojo.user.UserYearWork;
import cn.hsf.hsfmanager.service.user.UserYearWorkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserYearWorkServiceImpl implements UserYearWorkService {

    @Resource
    private UserYearWorkMapper userYearWorkMapper;


    @Override
    public UserYearWork selById(Integer id) {
        return userYearWorkMapper.selById(id);
    }

    @Override
    public List<UserYearWork> selUserYearWorkAll() {
        return userYearWorkMapper.selUserYearWorkAll();
    }

    @Override
    public int delUserYearWorkById(Integer id) {
        return userYearWorkMapper.delUserYearWorkById(id);
    }

    @Override
    public int insUserYearWork(UserYearWork userYearWork) {
        return userYearWorkMapper.insUserYearWork(userYearWork);
    }

    @Override
    public int updUserYearWork(UserYearWork userYearWork) {
        return userYearWorkMapper.updUserYearWork(userYearWork);
    }
}
