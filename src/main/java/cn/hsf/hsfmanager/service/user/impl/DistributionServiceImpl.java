package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.DistributionMapper;
import cn.hsf.hsfmanager.mapper.DistributionStatusMapper;
import cn.hsf.hsfmanager.mapper.UserOrderMapper;
import cn.hsf.hsfmanager.mapper.UserReleaseMapper;
import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.DistributionStatus;
import cn.hsf.hsfmanager.pojo.user.UserOrder;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.DistributionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DistributionServiceImpl implements DistributionService {

    @Resource
    private DistributionMapper distributionMapper;
    @Resource
    private DistributionStatusMapper distributionStatusMapper;
    @Resource
    private UserReleaseMapper userReleaseMapper;
    @Resource
    private UserOrderMapper userOrderMapper;

    @Override
    public int insDistribution(Distribution distribution) {
        return distributionMapper.insDistribution(distribution);
    }

    @Override
    public Distribution selByResId(Distribution distribution) {
        return distributionMapper.selByResId(distribution);
    }

    @Override
    public int updDistribution(Distribution distribution) {
        return distributionMapper.updDistribution(distribution);
    }

    @Override
    public Distribution selDistributionById(Integer id) {
        return distributionMapper.selDistributionById(id);
    }

    @Override
    public List<DistributionStatus> selAllDisName() {
        return distributionStatusMapper.selAllDisName();
    }

    @Override
    public int delDetailById(Integer id) {
        return distributionMapper.delDetailById(id);
    }

    @Override
    public DistributionStatus selByDisId(Integer id) {
        return distributionStatusMapper.selByDisId(id);
    }

    @Override
    public List<Distribution> selByDistribution(Distribution distribution) {
        return distributionMapper.selByDistribution(distribution);
    }

    @Override
    public List<Distribution> selDisList(Integer pageCurrentNo, Integer pageSize, Integer statusId, Integer sfId) {
        return distributionMapper.selDisList((pageCurrentNo-1)*pageSize,pageSize,statusId,sfId);
    }

    @Override
    public int selDisTotal(Integer statusId, Integer sfId) {
        return distributionMapper.selDisTotal(statusId, sfId);
    }

    /**
     * 更新状态
     * @param id
     * @param statusId
     * @param refusedMessage
     */
    @Override
    public void updStatus(Integer id, Integer statusId, String refusedMessage) {
        Distribution distribution = distributionMapper.selDistributionById(id);   //根据id得到派单表实体对象
        UserRelease userRelease = userReleaseMapper.selUserReleaseById(distribution.getReleaseId());//得到下单表对象
        if(statusId == 1 || statusId == 8){  //新订单  订单失效
            //修改派单表
            Distribution dis = new Distribution();
            dis.setId(id);
            dis.setStatusId(statusId);
            distributionMapper.updStatus(dis);
            //修改下单表
            userReleaseMapper.updStatus(new UserRelease(distribution.getReleaseId(),statusId));
            //修改订单表
            if(distribution.getOrderId() != null && !distribution.getOrderId().equals("")){
                userOrderMapper.delUserOrderById(distribution.getOrderId());
            }
        }else if(statusId == 2){  //服务进行中
            //新增订单表
            UserOrder userOrder = new UserOrder();
            userOrderMapper.insUserOrder2(userOrder);
            //修改派单表
            distributionMapper.updDistribution(new Distribution(id,statusId, userOrder.getId(),1));
            //修改下单表
            userReleaseMapper.updateUserRelease(new UserRelease(distribution.getReleaseId(),statusId,distribution.getSfId()));
        }else if(statusId == 3 || statusId == 5){  //已拒单  已取消
            //修改派单表
            distributionMapper.updDistribution(new Distribution(id,statusId,refusedMessage,1));
            //修改下单表 仍为新订单
             userReleaseMapper.updStatus(new UserRelease(distribution.getReleaseId(),1));
        }else if(statusId == 6 || statusId == 7){  // 已完成 申请完工
            //修改派单表
            distributionMapper.updDistribution(new Distribution(id,statusId,1));
            //修改下单表
            if(userRelease.getId() !=null){
                userReleaseMapper.updateUserRelease(new UserRelease(distribution.getReleaseId(),statusId));
            }else{
                UserOrder userOrder = new UserOrder();
                userOrderMapper.insUserOrder2(userOrder);
                userReleaseMapper.updateUserRelease(new UserRelease(distribution.getReleaseId(),statusId,distribution.getSfId()));
            }
        }
    }
}
