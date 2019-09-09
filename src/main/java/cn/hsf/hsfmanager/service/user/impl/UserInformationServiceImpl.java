package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserInformationMapper;
import cn.hsf.hsfmanager.pojo.user.UserInformation;
import cn.hsf.hsfmanager.service.user.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    private UserInformationMapper userInformationMapper;

    @Override
    public int selTotal(Integer isDelete) {
        return userInformationMapper.selTotal(isDelete);
    }

    @Override
    public List<UserInformation> selAll(Integer pageNo, Integer pageSize, Integer isDelete) {
        return userInformationMapper.selAll((pageNo - 1) * pageSize, pageSize, isDelete);
    }

    @Override
    public UserInformation selById(Integer id) {
        return userInformationMapper.selById(id);
    }

    @Override
    public int updInfor(UserInformation userInformation) {
        return userInformationMapper.updInfor(userInformation);
    }

    @Override
    public int delUserInfoByIds(Integer[] array,String delCause) {
        return userInformationMapper.delUserInfoByIds(array,delCause);
    }
}
