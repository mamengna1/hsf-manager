package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.AdminType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminTypeMapper {

    AdminType selAdminTypeById(@Param("id") Integer id);

    List<AdminType> selAdminTypeList();

}
