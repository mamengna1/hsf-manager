package cn.hsf.hsfmanager.mapper;
import cn.hsf.hsfmanager.pojo.user.UserSkill;
import cn.hsf.hsfmanager.pojo.user.UserSkills;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserSkillMapper {

    //根据id查询技能名称
    UserSkill selNameById(@Param("id") Integer id);

    List<UserSkill> selAllSkill(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize);
    int selSkillTotal();

    List<UserSkill> selAll();

    //新增
    int insSkill(UserSkill userSkill);
    int updSkill(UserSkill userSkill);
    int delSkill(@Param("id") Integer id);


    // 技能详细信息
    //分页
    List<UserSkills> selByParent(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize,@Param("parentId")Integer parentId);
    int selSkillsTotal(@Param("parentId")Integer parentId);

    int insertUserSkills(UserSkills userSkills);
    UserSkills selUserSkills(UserSkills userSkills);
    List<UserSkills> selSkillName(@Param("parentId")Integer parentId);
    int updSkills(UserSkills userSkills);
    int delSkillsById(@Param("id") Integer id);
    int delSkillsByParentId(@Param("parentId") Integer parentId);
}

