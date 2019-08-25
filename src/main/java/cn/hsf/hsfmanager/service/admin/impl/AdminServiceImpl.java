package cn.hsf.hsfmanager.service.admin.impl;

import cn.hsf.hsfmanager.mapper.AdminMapper;
import cn.hsf.hsfmanager.pojo.Admin;
import cn.hsf.hsfmanager.service.admin.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;


    @Override
    public Admin checkLogin(Admin admin) {
        return adminMapper.checkLogin(admin);
    }

    @Override
    public List<Admin> selListAdmin(Admin admin) {
        return adminMapper.selListAdmin(admin);
    }

    @Override
    public int saveAdmin(Admin admin) {
        return adminMapper.saveAdmin(admin);
    }

    @Override
    public Admin selAdmin(Admin admin) {
        return adminMapper.selAdmin(admin);
    }

    @Override
    public int updAdmin(Admin admin) {
        return adminMapper.updAdmin(admin);
    }

    @Override
    public int delAdmin(Integer id) {
        return adminMapper.delAdmin(id);
    }
}
