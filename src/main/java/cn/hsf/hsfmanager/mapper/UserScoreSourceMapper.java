package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import org.apache.ibatis.annotations.Param;

public interface UserScoreSourceMapper {

    int insScoreSource(UserScoreSource userScoreSource);

    ScoreSourceType selById(@Param("id") Integer id);
}
