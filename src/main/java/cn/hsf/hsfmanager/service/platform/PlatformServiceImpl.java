package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.mapper.ImgTypeMapper;
import cn.hsf.hsfmanager.mapper.PlatformSlideshowMapper;
import cn.hsf.hsfmanager.pojo.platform.ImgType;
import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PlatformServiceImpl implements PlatformService {

    @Autowired
    private PlatformSlideshowMapper platformSlideshowMapper;
    @Resource
    private ImgTypeMapper imgTypeMapper;

    @Override
    public List<ImgType> selImgTypeAll() {
        return imgTypeMapper.selImgTypeAll();
    }

    @Override
    public List<PlatformSlideshow> selAll(Integer imgType) {
        return platformSlideshowMapper.selAll(imgType);
    }

    @Override
    public int insSlideshow(PlatformSlideshow platformSlideshow) {
        return platformSlideshowMapper.insSlideshow(platformSlideshow);
    }

    @Override
    public List<PlatformSlideshow> selSlideShowAll(Integer pageCurrentNo, Integer pageSize, Integer imgType) {
        return platformSlideshowMapper.selSlideShowAll((pageCurrentNo-1)*pageSize,pageSize,imgType);
    }

    @Override
    public int selSlideShowTotal(Integer imgType) {
        return platformSlideshowMapper.selSlideShowTotal(imgType);
    }

    @Override
    public int delSlideById(Integer[] array) {
        return platformSlideshowMapper.delSlideById(array);
    }

    @Override
    public int updSlide(PlatformSlideshow platformSlideshow) {
        return platformSlideshowMapper.updSlide(platformSlideshow);
    }

    @Override
    public int delSlideSingleById(Integer id) {
        return platformSlideshowMapper.delSlideSingleById(id);
    }

    @Override
    public PlatformSlideshow selSlideById(Integer id) {
        return platformSlideshowMapper.selSlideById(id);
    }

    @Override
    public int delFile(Integer id) {
        return platformSlideshowMapper.delFile(id);
    }
}
