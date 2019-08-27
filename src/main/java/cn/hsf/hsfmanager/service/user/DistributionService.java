package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;

import java.util.List;

public interface DistributionService {
    int insDistribution(Distribution distribution);

    Distribution selByResId(Distribution distribution);

    //分页
    List<Distribution> selDistributionAll( Integer pageCurrentNo,  Integer pageSize);

    /**
     * 总记录数
     * @return
     */
    int selDistributionTotal();
}
