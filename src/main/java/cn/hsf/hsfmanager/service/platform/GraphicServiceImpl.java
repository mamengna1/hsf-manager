package cn.hsf.hsfmanager.service.platform;

import cn.hsf.hsfmanager.mapper.GraphicMapper;
import cn.hsf.hsfmanager.mapper.GraphicTypeMapper;
import cn.hsf.hsfmanager.pojo.platform.Graphic;
import cn.hsf.hsfmanager.pojo.platform.GraphicType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GraphicServiceImpl implements GraphicService {

    @Resource
    private GraphicMapper graphicMapper;
    @Resource
    private GraphicTypeMapper graphicTypeMapper;


    @Override
    public List<Graphic> selAllGraphicList(Integer pageCurrentNo, Integer pageSize, String title,Integer graTypeId ) {
        return graphicMapper.selAllGraphicList((pageCurrentNo-1)*pageSize,pageSize,title,graTypeId);
    }

    @Override
    public int selAllListCount(String title,Integer graTypeId ) {
        return graphicMapper.selAllListCount(title,graTypeId);
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

    @Override
    public List<GraphicType> selAllGraName() {
        return graphicTypeMapper.selAllGraName();
    }

    @Override
    public GraphicType selByGraId(Integer id) {
        return graphicTypeMapper.selByGraId(id);
    }

    @Override
    public int delGraphicTypeById(Integer id) {
        return graphicTypeMapper.delGraphicTypeById(id);
    }

    @Override
    public int insGraphicType(GraphicType graphicType) {
        return graphicTypeMapper.insGraphicType(graphicType);
    }

    @Override
    public int updGraphicType(GraphicType graphicType) {
        return graphicTypeMapper.updGraphicType(graphicType);
    }


}
