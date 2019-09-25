package cn.hsf.hsfmanager.service.user;


import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;

import java.util.List;

public interface UserScoreSourceService {
    ScoreSourceType selById(Integer id);
    List<ScoreSourceType> selScoreType();
    int delScoreSourceTypeById(Integer id);
    int insScoreSourceType(ScoreSourceType scoreSourceType);
    int updScoreSourceType(ScoreSourceType scoreSourceType);







    int insScoreSource(UserScoreSource userScoreSource);
    /*分页查询积分记录信息*/
    List<UserScoreSource> selAllScore(Integer pageCurrentNo,  Integer pageSize,  String openId,Integer scoreSourceId,String userName);
    int scoreTotal( String openId,Integer scoreSourceId,String userName);

    UserScoreSource selScoreById( Integer id);
    int updScore(UserScoreSource userScoreSource);
    int delScoreById(Integer id);
    UserScoreSource selUserScore(UserScoreSource userScoreSource);

    int delUserScoreAll(Integer[] array);

    List<UserScoreSource> selScoreByArray(Integer[] array);
}
