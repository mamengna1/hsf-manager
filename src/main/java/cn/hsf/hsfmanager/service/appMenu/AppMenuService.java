package cn.hsf.hsfmanager.service.appMenu;

import cn.hsf.hsfmanager.pojo.appMenu.AppMenu;
import cn.hsf.hsfmanager.pojo.appMenu.AppMenuType;

import java.util.List;

public interface AppMenuService {

    List<AppMenuType> selAll();

    List<AppMenu> selAllByParent(Integer id);

    int insertAppMenu(AppMenu appMenu);
    //查询所有的key
    List<String> selAllKey();

    //分页
    List<AppMenu> selAllMenu( Integer pageCurrentNo,Integer pageSize);
    int selMenuTotal();


    //删除
    int delMenuById(Integer id);

    //根据id进行查询
    AppMenu selAppMenuById(Integer id);

    //修改
    int updateAppMenuById(AppMenu appMenu);
}
