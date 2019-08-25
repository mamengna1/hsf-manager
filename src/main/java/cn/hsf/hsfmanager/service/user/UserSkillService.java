package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserSkill;

import java.util.List;

public interface UserSkillService {
    //根据id查询技能名称
    UserSkill selNameById( Integer id);

    List<UserSkill> selAllSkill( Integer pageCurrentNo, Integer pageSize);
    int selSkillTotal();
    List<UserSkill> selAll();

    int insSkill(UserSkill userSkill);
    int updSkill(UserSkill userSkill);
    int delSkill(Integer id);
}
