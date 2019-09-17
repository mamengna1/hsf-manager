package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.pojo.platform.Graphic;

import java.util.List;

public interface GraphicService {

    List<Graphic> selAllGraphicList( Integer pageCurrentNo, Integer pageSize, String title );

    int selAllListCount(String title);

    Graphic selGraphicById(Integer id);

    int insGraphic(Graphic graphic);

    int updGraphic(Graphic graphic);
    //单个删除
    int delGraphic(Integer id);
    //批量删除
    int delGraphicById(Integer[] array);

    int delFile(Integer id);
}
