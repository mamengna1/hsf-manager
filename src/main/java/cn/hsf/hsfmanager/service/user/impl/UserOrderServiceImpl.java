package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.UserOrderMapper;
import cn.hsf.hsfmanager.pojo.user.UserOrder;
import cn.hsf.hsfmanager.service.user.UserOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Resource
    private UserOrderMapper userOrderMapper;
    @Override
    public int insUserOrder(UserOrder userOrder) {
        return userOrderMapper.insUserOrder(userOrder);
    }

    @Override
    public int updUserOrder(UserOrder userOrder) {
        return userOrderMapper.updUserOrder(userOrder);
    }

    @Override
    public int delUserOrderById(Integer id) {
        return userOrderMapper.delUserOrderById(id);
    }

    @Override
    public List<UserOrder> selAllUserOrder(Integer pageCurrentNo, Integer pageSize, Integer commentTypeId) {
        return userOrderMapper.selAllUserOrder((pageCurrentNo-1)*pageSize,pageSize,commentTypeId);
    }

    @Override
    public int selUserOrderTotal(Integer commentTypeId) {
        return userOrderMapper.selUserOrderTotal(commentTypeId);
    }

    @Override
    public UserOrder selById(Integer id) {
        return userOrderMapper.selById(id);
    }

    @Override
    public UserOrder selByUserOrder(UserOrder userOrder) {
        return userOrderMapper.selByUserOrder(userOrder);
    }
}
