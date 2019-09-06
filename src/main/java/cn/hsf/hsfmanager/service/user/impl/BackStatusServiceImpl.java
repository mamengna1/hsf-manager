package cn.hsf.hsfmanager.service.user.impl;

import cn.hsf.hsfmanager.mapper.BackStatusMapper;
import cn.hsf.hsfmanager.pojo.user.BackStatus;
import cn.hsf.hsfmanager.service.user.BackStatusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackStatusServiceImpl implements BackStatusService {

    @Resource
    private BackStatusMapper backStatusMapper;

    @Override
    public List<BackStatus> selAllStatus() {
        return backStatusMapper.selAllStatus();
    }

    @Override
    public BackStatus selById(Integer id) {
        return backStatusMapper.selById(id);
    }
}
