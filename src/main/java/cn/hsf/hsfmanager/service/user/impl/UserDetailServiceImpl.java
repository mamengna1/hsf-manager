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
    public List<UserDetail> selUserDetailAll(Integer pageCurrentNo, Integer pageSize, String name,Integer status,Integer lineStatus) {
        return userDetailMapper.selUserDetailAll((pageCurrentNo-1)*pageSize,pageSize,name,status,lineStatus);
    }

    @Override
    public int selUserDetailTotal(String name,Integer status,Integer lineStatus) {
        return userDetailMapper.selUserDetailTotal(name,status,lineStatus);
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

    @Override
    public UserYearWork selYearById(Integer id) {
        return userDetailMapper.selYearById(id);
    }

    @Override
    public List<UserDetail> selPaiDanAll(Integer pageCurrentNo, Integer pageSize, String skillId, Integer workProvince, Integer workCity, Integer workArea) {
        return userDetailMapper.selPaiDanAll((pageCurrentNo-1)*pageSize, pageSize, skillId, workProvince, workCity, workArea);
    }

    @Override
    public int selPaiDanTotal(String skillId, Integer workProvince, Integer workCity, Integer workArea) {
        return userDetailMapper.selPaiDanTotal(skillId, workProvince, workCity, workArea);
    }

    @Override
    public int delMasterById(Integer[] array) {
        return userDetailMapper.delMasterById(array);
    }


}

