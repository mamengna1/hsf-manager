package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.platform.Graphic;
import cn.hsf.hsfmanager.pojo.platform.GraphicType;

import java.util.List;

public interface GraphicTypeMapper {

    List<GraphicType> selAllGraName();

    GraphicType selByGraId(Integer id);

    int delGraphicTypeById(Integer id);
    int insGraphicType(GraphicType graphicType);
    int updGraphicType(GraphicType graphicType);
}
