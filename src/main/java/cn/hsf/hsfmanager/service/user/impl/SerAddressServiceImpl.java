package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.SerAddressMapper;
import cn.hsf.hsfmanager.pojo.user.SerAddress;
import cn.hsf.hsfmanager.service.user.SerAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SerAddressServiceImpl implements  SerAddressService {

    @Resource
    private SerAddressMapper serAddressMapper;
    @Override
    public List<SerAddress> selByParent(Integer parentId) {
        return serAddressMapper.selByParent(parentId);
    }

    @Override
    public String getName(Integer id) {
        return serAddressMapper.getName(id);
    }

    @Override
    public List<String> getNameList(Integer[] array) {
        return serAddressMapper.getNameList(array);
    }

    @Override
    public int insProvinceName(String addName) {
        return serAddressMapper.insProvinceName(addName);
    }

    @Override
    public int deSerAddress(int id) {
        return serAddressMapper.deSerAddress(id);
    }

    @Override
    public int updSerAddress(SerAddress serAddress) {
        return serAddressMapper.updSerAddress(serAddress);
    }

    @Override
    public int insSerAddress(SerAddress serAddress) {
        return serAddressMapper.insSerAddress(serAddress);
    }
}
