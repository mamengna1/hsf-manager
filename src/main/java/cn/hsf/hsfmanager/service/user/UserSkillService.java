package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserSkill;
import cn.hsf.hsfmanager.pojo.user.UserSkills;
import org.apache.ibatis.annotations.Param;

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


    // 技能详细信息
    List<UserSkills> selByParent(Integer pageCurrentNo, Integer pageSize,Integer parentId);
    int selSkillsTotal(Integer parentId);
    int insertUserSkills(UserSkills userSkills);
    UserSkills selUserSkills(UserSkills userSkills);
    List<UserSkills> selSkillName(Integer parentId);
    int updSkills(UserSkills userSkills);
    int delSkillsById(Integer id);
}
