package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.DistributionMapper;
import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.service.user.DistributionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DistributionServiceImpl implements DistributionService {

    @Resource
    private DistributionMapper distributionMapper;

    @Override
    public int insDistribution(Distribution distribution) {
        return distributionMapper.insDistribution(distribution);
    }

    @Override
    public Distribution selByResId(Distribution distribution) {
        return distributionMapper.selByResId(distribution);
    }

    @Override
    public List<Distribution> selDistributionAll(Integer pageCurrentNo, Integer pageSize) {
        return distributionMapper.selDistributionAll((pageCurrentNo-1)*pageSize, pageSize);
    }

    @Override
    public int selDistributionTotal() {
        return distributionMapper.selDistributionTotal();
    }

    @Override
    public int updDistribution(Distribution distribution) {
        return distributionMapper.updDistribution(distribution);
    }
}
