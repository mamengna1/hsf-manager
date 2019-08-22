package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userMaster")
public class UserMasterController {

    @Resource
    private UserDetailService userDetailService;
    @Resource
    private UserService userService;
    @Resource
    private UserScoreSourceService scoreSourceService;

    /**
     * 进入师父信息界面
     * @return
     */
    @RequestMapping("/selMaster")
    public String selMaster(){
        return "user/userMasterAll";
    }

    /**
     * 进入师傅已审核界面
     * @return
     */
    @RequestMapping("/goUserMasterAudit")
    public String goUserMasterAudit(){
        return "user/userMasterAudit";
    }

    /**
     * 进入师傅待审核界面
     * @return
     */
    @RequestMapping("/goAuditWait")
    public String goAuditWait(){
        return "user/userMasterWait";
    }

    /**
     * 进入审核不通过页面
     * @return
     */
    @RequestMapping("/goAuditNot")
    public String goAuditNot(){
        return "user/userMasterNot";
    }
    /**
     * 分页显示数据   审核
     * @param pageCurrentNo
     * @param name
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "names",required = false,defaultValue = "") String name,
                        @RequestParam(value = "statusId",required = false,defaultValue = "") Integer status){
        int total = userDetailService.selUserDetailTotal(name,status);
        Page page = new Page();
        page.setPageSize(10);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserDetail> userDetails = userDetailService.selUserDetailAll(pageCurrentNo,10,name,status);
        page.setList(userDetails);
        return page;
    }

    /**
     * 查询用户的真实信息
     * @param id
     * @return
     */

    @RequestMapping("/selRealMessage")
    @ResponseBody
    public Map selRealMessage(Integer id){
        Map map = new HashMap();
        UserDetail userDetail = userDetailService.selUserDetailById(id);
        map.put("userDetail",userDetail);
        User user = userService.selUserByDetailId(userDetail.getId());
        map.put("user",user);
        return map;
    }

    /**
     * 审核
     * @param id
     * @param status
     * @param statusMessage
     * @return
     */
    @RequestMapping("/updateUserDetail")
    @ResponseBody
    public boolean updateUserDetail(Integer id,Integer status,@RequestParam(value = "statusMessage",required = false,defaultValue = "") String statusMessage){
        Integer lineStatus = 0;
        if(status ==1 ){
            String openId = userService.selUserByDetailId(id).getOpenId();
            String o = userService.selUserByDetailId(id).getOpenId();
            double[] pre = new double[]{5,2};
            userService.updateUserByOpenId(new User(openId,pre[0]));    //修改提交审核人的积分
            UserScoreSource userScoreSource = new UserScoreSource(openId,pre[0],5);
            scoreSourceService.insScoreSource(userScoreSource);   //记录成为师父的积分来源记录
            //修改推广人的积分
            for (int i = 0; i < 2; i++) {
                User user = userService.selUserByOpenId(openId);
                if(user !=null){
                    String userParent = user.getUserParent();
                    if(userParent !=null && !userParent.equals("")){
                        User parent = new User(userParent,pre[i]);
                        userService.updateUserByOpenId(parent);
                        UserScoreSource ScoreSource = new UserScoreSource(userParent,pre[i],2,o);
                        scoreSourceService.insScoreSource(ScoreSource);   //记录分红的积分来源记录
                        openId = userParent;
                    }
                }
            }
            lineStatus = 1;
        }
        UserDetail userDetail = new UserDetail(id,status,statusMessage,lineStatus);
        return  userDetailService.updateUserDetail(userDetail) > 0 ? true : false;
    }
}
