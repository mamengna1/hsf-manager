package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserMapper;
import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> selUserAll(Integer pageCurrentNo, Integer pageSize, Integer isSub, Integer detailId) {
        return userMapper.selUserAll((pageCurrentNo-1)*pageSize,pageSize,isSub,detailId);
    }

    @Override
    public int selUserTotal(Integer isSub, Integer detailId) {
        return userMapper.selUserTotal(isSub, detailId);
    }

    @Override
    public User selUserByOpenId(String openId) {
        return userMapper.selUserByOpenId(openId);
    }

    @Override
    public User selUserById(Integer id) {
        return userMapper.selUserById(id);
    }

    @Override
    public User selUserByDetailId(Integer detailId) {
        return userMapper.selUserByDetailId(detailId);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int updateUserByOpenId(User user) {
        return userMapper.updateUserByOpenId(user);
    }

    @Override
    public int delUser(Integer id) {
        return userMapper.delUser(id);
    }

    @Override
    public List<User> UserAll(Integer pageCurrentNo, Integer pageSize) {
        return userMapper.UserAll((pageCurrentNo-1)*pageSize,pageSize);
    }

    @Override
    public int userTotal() {
        return userMapper.userTotal();
    }
}
