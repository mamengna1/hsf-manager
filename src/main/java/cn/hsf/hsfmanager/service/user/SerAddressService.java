package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.SerAddress;

import java.util.List;

public interface SerAddressService {

    List<SerAddress> selByParent(Integer parentId);
    String getName(Integer id);
    List<String> getNameList(Integer[] array);
}
