package cn.hsf.hsfmanager.service.user;


import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;

import java.util.List;

public interface UserScoreSourceService {

    int insScoreSource(UserScoreSource userScoreSource);


    ScoreSourceType selById(Integer id);
    List<ScoreSourceType> selScoreType();
    /*分页查询积分记录信息*/
    List<UserScoreSource> selAllScore(Integer pageCurrentNo,  Integer pageSize,  String openId,Integer scoreSourceId);
    int scoreTotal( String openId,Integer scoreSourceId);

    UserScoreSource selScoreById( Integer id);
    int updScore(UserScoreSource userScoreSource);
    int delScoreById(Integer id);
    UserScoreSource selUserScore(UserScoreSource userScoreSource);
}
