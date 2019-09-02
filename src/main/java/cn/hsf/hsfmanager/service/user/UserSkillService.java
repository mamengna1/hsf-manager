package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserSkills;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserSkillService {

    // 技能详细信息
    List<UserSkills> selByParent(Integer pageCurrentNo, Integer pageSize,Integer parentId);
    int selSkillsTotal(Integer parentId);
    int insertUserSkills(UserSkills userSkills);
    UserSkills selUserSkills(UserSkills userSkills);
    List<UserSkills> selSkillName(Integer parentId);
    int updSkills(UserSkills userSkills);
    int delSkillsById(Integer id);
    int delSkillsByParentId( Integer parentId);
    List<UserSkills> selAllSkills();
}
