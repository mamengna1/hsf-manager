package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.DistributionStatus;

import java.util.List;

public interface DistributionService {
    int insDistribution(Distribution distribution);

    Distribution selByResId(Distribution distribution);



    int updDistribution(Distribution distribution);
    Distribution selDistributionById(Integer id);

    List<DistributionStatus> selAllDisName();
    int delDetailById( Integer id);
    DistributionStatus selByDisId(Integer id);
    List<Distribution> selByDistribution(Distribution distribution);

    //接单记录分页
    List<Distribution> selDisList( Integer pageCurrentNo,  Integer pageSize,Integer statusId,Integer sfId);
    int selDisTotal(Integer statusId,Integer sfId);
}
