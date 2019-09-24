package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatformSlideshowMapper {

    int insSlideshow(PlatformSlideshow platformSlideshow);

    List<PlatformSlideshow> selAll(Integer imgType);
    // 分页
    List<PlatformSlideshow> selSlideShowAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("imgType") Integer imgType);
    int selSlideShowTotal(@Param("imgType") Integer imgType);

    int delSlideById(Integer[] array);

    int updSlide(PlatformSlideshow platformSlideshow);
    int delSlideSingleById(@Param("id") Integer id);  //单个删除
    PlatformSlideshow selSlideById(@Param("id") Integer id);

    int delFile(Integer id);

    //根据类型查询所有的优先级
    List<Integer> selPriority(Integer imgType);
}
