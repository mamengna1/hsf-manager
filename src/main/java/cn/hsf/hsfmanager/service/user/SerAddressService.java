package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.SerAddress;

import java.util.List;

public interface SerAddressService {

    List<SerAddress> selByParent(Integer parentId);
    String getName(Integer id);
    List<String> getNameList(Integer[] array);

    //新增省份
    int insProvinceName(String addName);

    int deSerAddress(Integer id);
    int deSerAddressByParentId(Integer parentId);
    int updSerAddress(SerAddress serAddress);
    int insSerAddress(SerAddress serAddress);

    SerAddress selById(Integer id);
}
