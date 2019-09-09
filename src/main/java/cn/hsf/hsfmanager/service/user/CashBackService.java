package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.CashBack;

import java.util.List;

public interface CashBackService {
    List<CashBack> selAllByOpenId( String openId);

    CashBack selAllById(Integer id);

    //分页
    //查询所有提现记录并分页显示
    List<CashBack> selAll(Integer pageCurrentNo,Integer pageSize, Integer backStatusId,String openId);
    int selTotalCount( Integer backStatusId,String openId);

    //修改
    int updateCashBack(CashBack cashBack);

    int delCashBack(Integer[] array);
}
