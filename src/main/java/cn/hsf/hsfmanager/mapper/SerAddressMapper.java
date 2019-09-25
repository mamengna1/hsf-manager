package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.SerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SerAddressMapper {
    List<SerAddress> selByParent(@Param("parentId")Integer parentId);

    String getName(Integer id);

    List<String> getNameList(Integer[] array);
}
