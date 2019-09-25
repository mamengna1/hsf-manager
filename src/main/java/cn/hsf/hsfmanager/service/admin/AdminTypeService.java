package cn.hsf.hsfmanager.service.admin;

import cn.hsf.hsfmanager.pojo.AdminType;

import java.util.List;

public interface AdminTypeService {

    AdminType selAdminTypeById( Integer id);

    List<AdminType> selAdminTypeList();

    int delAdminTypeById(Integer id);
    int insAdminType(AdminType adminType);
    int updAdminType(AdminType adminType);
}
