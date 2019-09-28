package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.platform.Graphic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GraphicMapper {

    List<Graphic> selAllGraphicList(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("title") String title ,@Param("graTypeId") Integer graTypeId );

    int selAllListCount( @Param("title") String title,@Param("graTypeId") Integer graTypeId);

    Graphic selGraphicById(Integer id);

    int insGraphic(Graphic graphic);

    int updGraphic(Graphic graphic);

    int delGraphic(@Param("id") Integer id);

    int delGraphicById(Integer[] array);

    int delFile(@Param("id") Integer id);
}
