package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserScoreSourceMapper;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserScoreSourceServiceImpl implements UserScoreSourceService {

    @Resource
    private UserScoreSourceMapper scoreSourceMapper;


    @Override
    public int insScoreSource(UserScoreSource userScoreSource) {
        return scoreSourceMapper.insScoreSource(userScoreSource);
    }
}
