package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserReleaseMapper;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.UserReleaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserReleaseServiceImpl implements UserReleaseService {

    @Resource
    private UserReleaseMapper userReleaseMapper;

    @Override
    public List<UserRelease> selUserReleaseAll(Integer pageCurrentNo, Integer pageSize, Integer state,Integer mark) {
        return userReleaseMapper.selUserReleaseAll((pageCurrentNo-1)*pageSize,pageSize,state,mark);
    }

    @Override
    public int selUserReleaseTotal(Integer state,Integer mark) {
        return userReleaseMapper.selUserReleaseTotal(state,mark);
    }

    @Override
    public UserRelease selUserReleaseById(Integer id) {
        return userReleaseMapper.selUserReleaseById(id);
    }

    @Override
    public int updateUserRelease(UserRelease userRelease) {
        return userReleaseMapper.updateUserRelease(userRelease);
    }

    @Override
    public int updateUserRelease2(UserRelease userRelease, String date) {
        return userReleaseMapper.updateUserRelease2(userRelease,date);
    }

    @Override
    public int delRelById(Integer[] array) {
        return userReleaseMapper.delRelById(array);
    }
}
