package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Null;
import java.util.List;

public interface UserScoreSourceMapper {

    ScoreSourceType selById(@Param("id") Integer id);
    List<ScoreSourceType> selScoreType();
    int delScoreSourceTypeById(Integer id);
    int insScoreSourceType(ScoreSourceType scoreSourceType);
    int updScoreSourceType(ScoreSourceType scoreSourceType);





    int insScoreSource(UserScoreSource userScoreSource);
    /*分页查询积分记录信息*/
    List<UserScoreSource> selAllScore(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize,@Param("openId") String openId,@Param("scoreSourceId") Integer scoreSourceId,@Param("userName") String userName);
    int scoreTotal(@Param("openId") String openId,@Param("scoreSourceId") Integer scoreSourceId,@Param("userName") String userName);

    UserScoreSource selScoreById(@Param("id") Integer id);
    int updScore(UserScoreSource userScoreSource);
    int delScoreById(Integer id);

    UserScoreSource selUserScore(UserScoreSource userScoreSource);

    int delUserScoreAll(Integer[] array);
    List<UserScoreSource> selScoreByArray(Integer[] array);
}
