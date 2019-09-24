package cn.hsf.hsfmanager.service.appMenu.impl;

import cn.hsf.hsfmanager.mapper.AppMenuMapper;
import cn.hsf.hsfmanager.mapper.AppMenuTypeMapper;
import cn.hsf.hsfmanager.pojo.appMenu.AppMenu;
import cn.hsf.hsfmanager.pojo.appMenu.AppMenuType;
import cn.hsf.hsfmanager.service.appMenu.AppMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppMenuServiceImpl implements AppMenuService {

    @Autowired
    private AppMenuTypeMapper appMenuTypeMapper;
    @Resource
    private AppMenuMapper appMenuMapper;
    @Override
    public List<AppMenuType> selAll() {
        return appMenuTypeMapper.selAll();
    }

    @Override
    public List<AppMenu> selAllByParent(Integer id) {
        return appMenuMapper.selAllByParent(id);
    }

    @Override
    public int insertAppMenu(AppMenu appMenu) {
        return appMenuMapper.insertAppMenu(appMenu);
    }

    @Override
    public List<String> selAllKey() {
        return appMenuMapper.selAllKey();
    }

    @Override
    public List<AppMenu> selAllMenu(Integer pageCurrentNo, Integer pageSize) {
        return appMenuMapper.selAllMenu((pageCurrentNo-1)*pageSize,pageSize);
    }

    @Override
    public int selMenuTotal() {
        return appMenuMapper.selMenuTotal();
    }

    @Override
    public int delMenuById(Integer id) {
        return appMenuMapper.delMenuById(id);
    }

    @Override
    public AppMenu selAppMenuById(Integer id) {
        return appMenuMapper.selAppMenuById(id);
    }

    @Override
    public int updateAppMenuById(AppMenu appMenu) {
        return appMenuMapper.updateAppMenuById(appMenu);
    }

    @Override
    public int delMenuByArray(Integer[] array) {
        return appMenuMapper.delMenuByArray(array);
    }

}
