package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.UserOrder;

import java.util.List;

public interface UserOrderService {

    int insUserOrder(UserOrder userOrder);
    int updUserOrder(UserOrder userOrder);
    int delUserOrderById( Integer id);
    //分页
    List<UserOrder> selAllUserOrder(Integer pageCurrentNo,  Integer pageSize, Integer commentTypeId);
    int selUserOrderTotal(  Integer commentTypeId);

    UserOrder selById( Integer id);
    UserOrder selByUserOrder(UserOrder userOrder);

}
