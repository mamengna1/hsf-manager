package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserSkillMapper;
import cn.hsf.hsfmanager.pojo.user.UserSkills;
import cn.hsf.hsfmanager.service.user.UserSkillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    @Resource
    private UserSkillMapper userSkillMapper;

    /**
     * 技能详细信息
     * @return
     */


    @Override
    public List<UserSkills> selByParent(Integer pageCurrentNo, Integer pageSize,Integer parentId) {
        return userSkillMapper.selByParent((pageCurrentNo-1)*pageSize,pageSize,parentId);
    }

    @Override
    public int selSkillsTotal(Integer parentId) {
        return userSkillMapper.selSkillsTotal(parentId);
    }

    @Override
    public int insertUserSkills(UserSkills userSkills) {
        return userSkillMapper.insertUserSkills(userSkills);
    }

    @Override
    public UserSkills selUserSkills(UserSkills userSkills) {
        return userSkillMapper.selUserSkills(userSkills);
    }

    @Override
    public List<UserSkills> selSkillName(Integer parentId) {
        return userSkillMapper.selSkillName(parentId);
    }

    @Override
    public int updSkills(UserSkills userSkills) {
        return userSkillMapper.updSkills(userSkills);
    }

    @Override
    public int delSkillsById(Integer id) {
        return userSkillMapper.delSkillsById(id);
    }

    @Override
    public int delSkillsByParentId(Integer parentId) {
        return userSkillMapper.delSkillsByParentId(parentId);
    }

    @Override
    public List<UserSkills> selAllSkills() {
        return userSkillMapper.selAllSkills();
    }
}

