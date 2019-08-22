package cn.hsf.hsfmanager.mapper;


import cn.hsf.hsfmanager.pojo.appMenu.AppMenuType;

import java.util.List;

public interface AppMenuTypeMapper {

    AppMenuType selById(Integer id);

    List<AppMenuType> selAll();

}
