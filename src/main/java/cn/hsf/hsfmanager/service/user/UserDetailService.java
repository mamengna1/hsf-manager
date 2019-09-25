package cn.hsf.hsfmanager.service.user;

import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserYearWork;

import java.util.List;

public interface UserDetailService {

    /**
     * 查看所有师父 详细信息
     * @param pageCurrentNo
     * @param pageSize
     * @param name       真实姓名
     * @return
     */
    List<UserDetail> selUserDetailAll( Integer pageCurrentNo, Integer pageSize,  String name,Integer status,Integer lineStatus);

    /**
     * 总记录数
     * @param name       真实姓名
     * @return
     */
    int selUserDetailTotal(  String name,Integer status,Integer lineStatus);


    UserDetail selUserDetailById( Integer id);

    int updateUserDetail(UserDetail userDetail);

    List<UserYearWork> selYearAll();
    List<ScoreSourceType> selSourceType();

    UserYearWork selYearById( Integer id);


    // 派单显示信息

    /**
     *
     * @param pageCurrentNo
     * @param pageSize
     * @param skillId   技能
     * @return
     */
    List<UserDetail> selPaiDanAll(Integer pageCurrentNo,Integer pageSize,String skillId, Integer workProvince, Integer workCity, String workArea);
    //总数量
    int selPaiDanTotal( String skillId, Integer workProvince, Integer workCity,String workArea);

    int delMasterById(Integer[] array);
}

