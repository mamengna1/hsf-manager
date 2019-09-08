package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.UserOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserOrderMapper {
    int insUserOrder(UserOrder userOrder);
    int updUserOrder(UserOrder userOrder);
    int delUserOrderById(@Param("id") Integer id);
    int insUserOrder2(UserOrder userOrder);

    //分页
    List<UserOrder> selAllUserOrder(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("commentTypeId") Integer commentTypeId);
    int selUserOrderTotal( @Param("commentTypeId") Integer commentTypeId);

    UserOrder selById(@Param("id") Integer id);
    UserOrder selByUserOrder(UserOrder userOrder);

}
