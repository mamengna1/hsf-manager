package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserScoreSourceMapper;
import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserScoreSourceServiceImpl implements UserScoreSourceService {

    @Resource
    private UserScoreSourceMapper scoreSourceMapper;


    @Override
    public int insScoreSource(UserScoreSource userScoreSource) {
        return scoreSourceMapper.insScoreSource(userScoreSource);
    }

    @Override
    public ScoreSourceType selById(Integer id) {
        return scoreSourceMapper.selById(id);
    }

    @Override
    public List<ScoreSourceType> selScoreType() {
        return scoreSourceMapper.selScoreType();
    }

    @Override
    public List<UserScoreSource> selAllScore(Integer pageCurrentNo, Integer pageSize, String openId,Integer scoreSourceId,String userName) {
        return scoreSourceMapper.selAllScore((pageCurrentNo-1)*pageSize, pageSize, openId,scoreSourceId,userName);
    }

    @Override
    public int scoreTotal(String openId,Integer scoreSourceId,String userName) {
        return scoreSourceMapper.scoreTotal(openId,scoreSourceId,userName);
    }

    @Override
    public UserScoreSource selScoreById(Integer id) {
        return scoreSourceMapper.selScoreById(id);
    }

    @Override
    public int updScore(UserScoreSource userScoreSource) {
        return scoreSourceMapper.updScore(userScoreSource);
    }

    @Override
    public int delScoreById(Integer id) {
        return scoreSourceMapper.delScoreById(id);
    }

    @Override
    public UserScoreSource selUserScore(UserScoreSource userScoreSource) {
        return scoreSourceMapper.selUserScore(userScoreSource);
    }

    @Override
    public int delUserScoreAll(Integer[] array) {
        return scoreSourceMapper.delUserScoreAll(array);
    }

    @Override
    public List<UserScoreSource> selScoreByArray(Integer[] array) {
        return scoreSourceMapper.selScoreByArray(array);
    }
}
