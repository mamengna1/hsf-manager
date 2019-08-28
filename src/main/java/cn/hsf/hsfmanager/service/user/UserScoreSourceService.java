package cn.hsf.hsfmanager.service.user;


import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;

public interface UserScoreSourceService {

    int insScoreSource(UserScoreSource userScoreSource);


    ScoreSourceType selById(Integer id);
}
