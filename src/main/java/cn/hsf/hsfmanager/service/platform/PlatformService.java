package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;

import java.util.List;

public interface PlatformService {

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

}
