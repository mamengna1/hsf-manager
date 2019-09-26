package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.SerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SerAddressMapper {
    List<SerAddress> selByParent(@Param("parentId")Integer parentId);

    String getName(Integer id);

    List<String> getNameList(Integer[] array);

    //新增省份
    int insProvinceName(@Param("addName") String addName);

    int deSerAddress(Integer id);
    int deSerAddressByParentId(Integer parentId);
    int updSerAddress(SerAddress serAddress);
    int insSerAddress(SerAddress serAddress);
}
