package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;

import java.util.List;

public interface PlatformSlideshowMapper {

    int insSlideshow(PlatformSlideshow platformSlideshow);

    List<PlatformSlideshow> selAll(Integer imgType);

}
