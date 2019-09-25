package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.App;

public interface AppMapper {

    App selApp();

    int updAppToken(App app);

    int updApp(App app);
}
