package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.CashBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashBackMapper {

    List<CashBack> selAllByOpenId(@Param("openId") String openId);

    CashBack selAllById(@Param("id") Integer id);

    //分页
    //查询所有提现记录并分页显示
    List<CashBack> selAll(@Param("pageCurrentNo") Integer pageCurrentNo,@Param("pageSize") Integer pageSize,@Param("backStatusId") Integer backStatusId,@Param("openId") String openId);
    int selTotalCount(@Param("backStatusId") Integer backStatusId,@Param("openId") String openId);

    //修改
    int updateCashBack(CashBack cashBack);

}
