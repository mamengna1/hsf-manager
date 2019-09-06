package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.BackStatus;

import java.util.List;

public interface BackStatusService {
    List<BackStatus> selAllStatus();
    BackStatus selById(Integer id);
}
