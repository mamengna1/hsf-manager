package cn.hsf.hsfmanager.mapper;


import cn.hsf.hsfmanager.pojo.appMenu.AppMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppMenuMapper {

    List<AppMenu> selAllByParent(Integer id);
    int insertAppMenu(AppMenu appMenu);
    //查询所有的key
    List<String> selAllKey();

    //分页
    List<AppMenu> selAllMenu(@Param("pageCurrentNo") Integer pageCurrentNo,@Param("pageSize") Integer pageSize);
    int selMenuTotal();

    //删除
    int delMenuById(@Param("id") Integer id);

    //根据id进行查询
    AppMenu selAppMenuById(@Param("id") Integer id);

    //修改
    int updateAppMenuById(AppMenu appMenu);

}
