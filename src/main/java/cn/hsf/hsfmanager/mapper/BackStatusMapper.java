package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.BackStatus;

import java.util.List;

public interface BackStatusMapper {

    List<BackStatus> selAllStatus();
    BackStatus selById(Integer id);
}
