package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.UserSkill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserSkillMapper {

    //根据id查询技能名称
     UserSkill selNameById(@Param("id") Integer id);

     List<UserSkill> selAllSkill(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize);
    int selSkillTotal();
}
