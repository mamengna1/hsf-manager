package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.pojo.platform.ImgType;
import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;

import java.util.List;

public interface PlatformService {

    //类别
    List<ImgType> selImgTypeAll();


    

    /**
     * 查询所有图片
     * @return
     */
    List<PlatformSlideshow> selAll(Integer imgType);

    /**
     *  添加首页轮播图
     * @param platformSlideshow
     * @return
     */
    int insSlideshow(PlatformSlideshow platformSlideshow);

    List<PlatformSlideshow> selSlideShowAll(Integer pageCurrentNo,Integer pageSize,  Integer imgType);
    int selSlideShowTotal(Integer imgType);

    int delSlideById(Integer[] array);

    int updSlide(PlatformSlideshow platformSlideshow);
    int delSlideSingleById(Integer id);  //单个删除
    PlatformSlideshow selSlideById( Integer id);

    int delFile(Integer id);

    //根据类型查询所有的优先级
    List<Integer> selPriority(Integer imgType);
}
