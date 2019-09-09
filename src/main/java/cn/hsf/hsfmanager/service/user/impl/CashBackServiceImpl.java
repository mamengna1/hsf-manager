package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.CashBackMapper;
import cn.hsf.hsfmanager.pojo.user.CashBack;
import cn.hsf.hsfmanager.service.user.CashBackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CashBackServiceImpl implements CashBackService {

    @Resource
    private CashBackMapper cashBackMapper;

    @Override
    public List<CashBack> selAllByOpenId(String openId) {
        return cashBackMapper.selAllByOpenId(openId);
    }

    @Override
    public CashBack selAllById(Integer id) {
        return cashBackMapper.selAllById(id);
    }

    @Override
    public List<CashBack> selAll(Integer pageCurrentNo, Integer pageSize, Integer backStatusId, String openId) {
        return cashBackMapper.selAll((pageCurrentNo-1)*pageSize, pageSize, backStatusId, openId);
    }

    @Override
    public int selTotalCount(Integer backStatusId, String openId) {
        return cashBackMapper.selTotalCount(backStatusId,openId);
    }

    @Override
    public int updateCashBack(CashBack cashBack) {
        return cashBackMapper.updateCashBack(cashBack);
    }

    @Override
    public int delCashBack(Integer[] array) {
        return cashBackMapper.delCashBack(array);
    }
}
