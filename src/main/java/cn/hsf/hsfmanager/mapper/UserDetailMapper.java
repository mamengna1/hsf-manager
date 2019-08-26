package cn.hsf.hsfmanager.mapper;

import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserYearWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDetailMapper {

    /**
     * 查看所有师父 详细信息
     * @param pageCurrentNo
     * @param pageSize
     * @param name       真实姓名
     * @return
     */
    List<UserDetail> selUserDetailAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("name") String name, @Param("status") Integer status);

    /**
     * 总记录数
     * @param name       真实姓名
     * @return
     */
    int selUserDetailTotal( @Param("name") String name, @Param("status") Integer status);

    UserDetail selUserDetailById(@Param("id") Integer id);

    int updateUserDetail(UserDetail userDetail);

    List<UserYearWork> selYearAll();

    List<ScoreSourceType> selSourceType();

    UserYearWork selYearById(@Param("id") Integer id);


    // 派单显示信息

    /**
     *
     * @param pageCurrentNo
     * @param pageSize
     * @param skillId   技能
     * @param yearWorkId  工作年限
     * @param areas   地区
     * @return
     */
    List<UserDetail> selPaiDanAll(@Param("pageCurrentNo") Integer pageCurrentNo, @Param("pageSize") Integer pageSize, @Param("skillId") Integer skillId, @Param("yearWorkId") Integer yearWorkId,@Param("areas") String areas);
    //总数量
    int selPaiDanTotal(@Param("skillId") Integer skillId, @Param("yearWorkId") Integer yearWorkId,@Param("areas") String areas);
}

