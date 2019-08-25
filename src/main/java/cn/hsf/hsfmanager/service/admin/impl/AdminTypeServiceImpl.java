package cn.hsf.hsfmanager.service.admin.impl;

import cn.hsf.hsfmanager.mapper.AdminTypeMapper;
import cn.hsf.hsfmanager.pojo.AdminType;
import cn.hsf.hsfmanager.service.admin.AdminTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminTypeServiceImpl implements AdminTypeService {

    @Resource
    private AdminTypeMapper adminTypeMapper;

    @Override
    public AdminType selAdminTypeById(Integer id) {
        return adminTypeMapper.selAdminTypeById(id);
    }

    @Override
    public List<AdminType> selAdminTypeList() {
        return adminTypeMapper.selAdminTypeList();
    }
}
