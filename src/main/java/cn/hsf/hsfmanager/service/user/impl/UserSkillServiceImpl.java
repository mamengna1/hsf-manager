package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserSkillMapper;
import cn.hsf.hsfmanager.pojo.user.UserSkill;
import cn.hsf.hsfmanager.service.user.UserSkillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    @Resource
    private UserSkillMapper userSkillMapper;


    @Override
    public UserSkill selNameById(Integer id) {
        return userSkillMapper.selNameById(id);
    }

    @Override
    public List<UserSkill> selAllSkill(Integer pageCurrentNo, Integer pageSize) {
        return userSkillMapper.selAllSkill((pageCurrentNo-1)*pageSize,pageSize);
    }

    @Override
    public int selSkillTotal() {
        return userSkillMapper.selSkillTotal();
    }

    @Override
    public List<UserSkill> selAll() {
        return userSkillMapper.selAll();
    }
}
