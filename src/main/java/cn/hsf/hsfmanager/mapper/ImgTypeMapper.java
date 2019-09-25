package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.platform.ImgType;

import java.util.List;

public interface ImgTypeMapper {

    ImgType selById(Integer id);
    List<ImgType> selImgTypeAll();

    int delImgTypeById(Integer id);
    int insImgType(ImgType imgType);
    int updImgType(ImgType imgType);
}
