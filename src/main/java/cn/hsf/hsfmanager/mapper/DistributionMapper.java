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

    int updDistribution(Distribution distribution);
    int delDetailById(@Param("id") Integer id);


    Distribution selDistributionById(Integer id);
    List<Distribution> selByDistribution(Distribution distribution);

    //接单记录分页
    List<Distribution> selDisList(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize,@Param("statusId") Integer statusId,@Param("sfId") Integer sfId);
    int selDisTotal(@Param("statusId") Integer statusId,@Param("sfId") Integer sfId);

    //状态修改
    int updStatus(Distribution distribution);
}
