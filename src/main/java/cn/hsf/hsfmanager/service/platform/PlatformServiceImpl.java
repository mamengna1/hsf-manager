package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.mapper.PlatformSlideshowMapper;
import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformSlideshowMapper platformSlideshowMapper;

    @Override
    public List<PlatformSlideshow> selAll(Integer imgType) {
        return platformSlideshowMapper.selAll(imgType);
    }

    @Override
    public int insSlideshow(PlatformSlideshow platformSlideshow) {
        return platformSlideshowMapper.insSlideshow(platformSlideshow);
    }
}
