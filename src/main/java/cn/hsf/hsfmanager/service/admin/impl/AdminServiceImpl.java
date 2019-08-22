package cn.hsf.hsfmanager.service.admin.impl;

import cn.hsf.hsfmanager.mapper.AdminMapper;
import cn.hsf.hsfmanager.pojo.Admin;
import cn.hsf.hsfmanager.service.admin.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;


    @Override
    public Admin checkLogin(Admin admin) {
        return adminMapper.checkLogin(admin);
    }
}
