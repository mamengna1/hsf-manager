package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.mapper.GraphicMapper;
import cn.hsf.hsfmanager.pojo.platform.Graphic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GraphicServiceImpl implements GraphicService {

    @Resource
    private GraphicMapper graphicMapper;

    @Override
    public List<Graphic> selAllGraphicList(Integer pageCurrentNo, Integer pageSize, String title) {
        return graphicMapper.selAllGraphicList((pageCurrentNo-1)*pageSize,pageSize,title);
    }

    @Override
    public int selAllListCount(String title) {
        return graphicMapper.selAllListCount(title);
    }

    @Override
    public Graphic selGraphicById(Integer id) {
        return graphicMapper.selGraphicById(id);
    }

    @Override
    public int insGraphic(Graphic graphic) {
        return graphicMapper.insGraphic(graphic);
    }

    @Override
    public int updGraphic(Graphic graphic) {
        return graphicMapper.updGraphic(graphic);
    }

    @Override
    public int delGraphic(Integer id) {
        return graphicMapper.delGraphic(id);
    }

    @Override
    public int delGraphicById(Integer[] array) {
        return graphicMapper.delGraphicById(array);
    }

    @Override
    public int delFile(Integer id) {
        return graphicMapper.delFile(id);
    }
}
