package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.DistributionStatus;

import java.util.List;

public interface DistributionService {
    int insDistribution(Distribution distribution);

    Distribution selByResId(Distribution distribution);

    //分页
    List<Distribution> selDistributionAll( Integer pageCurrentNo,  Integer pageSize,Integer statusId);

    /**
     * 总记录数
     * @return
     */
    int selDistributionTotal(Integer statusId);

    int updDistribution(Distribution distribution);
    Distribution selDistributionById(Integer id);

    List<DistributionStatus> selAllDisName();
    int delDetailById( Integer id);
    DistributionStatus selByDisId(Integer id);
    List<Distribution> selByDistribution(Distribution distribution);
}
