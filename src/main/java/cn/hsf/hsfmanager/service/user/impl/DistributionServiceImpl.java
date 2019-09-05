package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.DistributionMapper;
import cn.hsf.hsfmanager.mapper.DistributionStatusMapper;
import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.DistributionStatus;
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

    @Override
    public int insDistribution(Distribution distribution) {
        return distributionMapper.insDistribution(distribution);
    }

    @Override
    public Distribution selByResId(Distribution distribution) {
        return distributionMapper.selByResId(distribution);
    }

    @Override
    public List<Distribution> selDistributionAll(Integer pageCurrentNo, Integer pageSize,Integer statusId) {
        return distributionMapper.selDistributionAll((pageCurrentNo-1)*pageSize, pageSize,statusId);
    }

    @Override
    public int selDistributionTotal(Integer statusId) {
        return distributionMapper.selDistributionTotal(statusId);
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
}
