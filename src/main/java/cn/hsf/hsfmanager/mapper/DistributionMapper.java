package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 派单
 */
public interface DistributionMapper {

    int insDistribution(Distribution distribution);

    Distribution selByResId(Distribution distribution);

    //分页
    List<Distribution> selDistributionAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize);

    /**
     * 总记录数
     * @return
     */
    int selDistributionTotal();
}
