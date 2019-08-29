package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.DistributionStatus;

import java.util.List;

public interface DistributionStatusMapper {

    List<DistributionStatus> selAllDisName();

    DistributionStatus selByDisId(Integer id);
}
