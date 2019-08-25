package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserDetailMapper;
import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserYearWork;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Resource
    private UserDetailMapper userDetailMapper;


    @Override
    public List<UserDetail> selUserDetailAll(Integer pageCurrentNo, Integer pageSize, String name,Integer status) {
        return userDetailMapper.selUserDetailAll((pageCurrentNo-1)*pageSize,pageSize,name,status);
    }

    @Override
    public int selUserDetailTotal(String name,Integer status) {
        return userDetailMapper.selUserDetailTotal(name,status);
    }

    @Override
    public UserDetail selUserDetailById(Integer id) {
        return userDetailMapper.selUserDetailById(id);
    }

    @Override
    public int updateUserDetail(UserDetail userDetail) {
        return userDetailMapper.updateUserDetail(userDetail);
    }

    @Override
    public List<UserYearWork> selYearAll() {
        return userDetailMapper.selYearAll();
    }

    @Override
    public List<ScoreSourceType> selSourceType() {
        return userDetailMapper.selSourceType();
    }
}
