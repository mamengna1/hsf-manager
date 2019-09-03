package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.*;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.service.user.UserSkillService;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Resource
    private TemplateService templateService;

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
        System.out.println("审核信息" + userDetails);
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
     * @param id   师傅详细信息id
     * @param status
     * @param statusMessage
     * @return
     */
    @RequestMapping("/updateUserDetail")
    @ResponseBody
    public boolean updateUserDetail(Integer id,Integer status,@RequestParam(value = "statusMessage",required = false,defaultValue = "") String statusMessage){
        Integer lineStatus = 0;
        User u =  userService.selUserByDetailId(id);
        UserDetail userDetail = userDetailService.selUserDetailById(id);
        String o = u.getOpenId();;
        if(status ==1 ){
            String openId =u.getOpenId();
            double[] pre = new double[]{5,2};
            userService.updateUserByOpenId(new User(openId,pre[0],pre[0]));    //修改提交审核人的积分
            UserScoreSource userScoreSource = new UserScoreSource(openId,pre[0],5);
            scoreSourceService.insScoreSource(userScoreSource);   //记录成为师父的积分来源记录

            // 发送审核成功的模板
            Map map = new HashMap();
            map.put("openId",o);
            map.put("name",userDetail.getName());
            templateService.sendAuditSuccess(map);

            //修改推广人的积分
            for (int i = 0; i < 2; i++) {
                User user = userService.selUserByOpenId(openId);
                if(user !=null){
                    String userParent = user.getUserParent();
                    if(userParent !=null && !userParent.equals("")){
                        User parent = new User(userParent,pre[i],pre[i]);
                        userService.updateUserByOpenId(parent);
                        UserScoreSource ScoreSource = new UserScoreSource(userParent,pre[i],2,o);
                        scoreSourceService.insScoreSource(ScoreSource);   //记录分红的积分来源记录
                        Map map2 = new HashMap();
                        map2.put("openId",userParent);
                        map2.put("title","恭喜，您获得了一笔新的分销积分啦。来自："+user.getNickName());
                        map2.put("fenHong",String.valueOf(pre[i]));
                        map2.put("total",String.valueOf(pre[0]));
                        templateService.sendScore(map2);
                        openId = userParent;
                    }
                }
            }
            lineStatus = 1;
            UserDetail detail = new UserDetail(id,status,statusMessage,lineStatus);
            userDetailService.updateUserDetail(detail);

            //给管理员发送模板信息
            String[] managerOpenId = Contents.MANAGER_OPENID;
            for (int j = 0; j <managerOpenId.length ; j++) {
                Map map2 = new HashMap();
                map2.put("openId",managerOpenId[j]) ;
                map2.put("template_id","TF2-OgTgYB6EYKzmno0NjbZobdCadK7U0d0E9O9ZogA") ;
                map2.put("title","又有一位新的师傅诞生啦") ;
                map2.put("serviceType","师傅审核成功通知") ;
                map2.put("orderNo","无") ;
                map2.put("orderState","新师傅暂无接单") ;
                map2.put("end","师傅信息："+userDetail.getName()+u.getPhone()) ;
                templateService.serviceStatus(map2);
            }
            return  true;
        }else if(status ==2){   //审核失败
            Map map = new HashMap();
            map.put("openId",o);
            map.put("name",userDetail.getName());
            map.put("message",statusMessage);
            map.put("url","http://java.86blue.cn/_api/goRegister");
            templateService.sendAuditFail(map);
            UserDetail detail = new UserDetail(id,status,statusMessage,lineStatus);
            userDetailService.updateUserDetail(detail);
            return false;
        }
        return false;
    }

    @Autowired
    private UserSkillService userSkillService;
    @Autowired
    private UserScoreSourceService userScoreSourceService;

    /**
     * 进入用户修改信息界面
     * @return
     */
    @RequestMapping("/goUserUpd")
    public String goUserUpd(Integer id , Model model){
        model.addAttribute("yearWorks", userDetailService.selYearAll());
        model.addAttribute("parentSkills", userSkillService.selSkillName(null));
        model.addAttribute("userSkills", userSkillService.selAllSkills());
        model.addAttribute("sources", userDetailService.selSourceType());
        model.addAttribute("user", userService.selUserByDetailId(id));
        return "user/userUpd";
    }

    @ResponseBody
    @RequestMapping("/selById")
    public UserDetail selById(Integer id){
        return userDetailService.selUserDetailById(id);
    }

    /**
     * 保存修改结果（师傅信息）
     * @param userScoreSource
     * @param userDetail
     * @param phone
     * @param source
     * @return
     */
    @RequestMapping("/insUserDetail")
    public String insUserDetail(UserScoreSource userScoreSource, UserDetail userDetail, String phone, Integer source,Integer score){
        userScoreSourceService.insScoreSource(userScoreSource);
        userService.updateUserByOpenId(new User(userScoreSource.getOpenId(), phone,Double.valueOf(score),Double.valueOf(score) ));
        System.out.println(userDetail);
        userDetailService.updateUserDetail(userDetail);
        ScoreSourceType scoreSourceType = userScoreSourceService.selById(userScoreSource.getScoreSourceId());
        if (source == 1) {   // 发送模板
            Map map = new HashMap();
            map.put("openId",userScoreSource.getOpenId());
            map.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
            map.put("title",userDetail.getName()+"您好，恭喜您获得【"+scoreSourceType.getSourceName()+"】积分，本次奖励积分："+score+"分");
            map.put("messageType","积分增加提醒");
            map.put("end","感谢您的使用，如有疑问请致电000000");
            templateService.sendTongYong(map);
        } else {

        }
        return "user/userMasterAudit";
    }

    @RequestMapping("/selYearWorkById")
    @ResponseBody
    public UserYearWork selYearWorkById(@RequestParam("id") Integer id){
        return userDetailService.selYearById(id);
    }

}

