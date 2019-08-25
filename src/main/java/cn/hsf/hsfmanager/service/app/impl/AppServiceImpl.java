package cn.hsf.hsfmanager.service.app.impl;

import cn.hsf.hsfmanager.mapper.AppMapper;
import cn.hsf.hsfmanager.pojo.App;
import cn.hsf.hsfmanager.service.app.AppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppMapper appMapper;


    @Override
    public App selApp() {
        return appMapper.selApp();
    }
}
