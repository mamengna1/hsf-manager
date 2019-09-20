package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.*;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.service.user.UserSkillService;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import cn.hsf.hsfmanager.util.URLS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
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
    public String selMaster(@RequestParam(value = "statusId",required = false,defaultValue = "-1") Integer statusId,Model model){
        model.addAttribute("statusId",statusId);
        model.addAttribute("zaixian",userDetailService.selUserDetailTotal(null,statusId,1));
        model.addAttribute("lixian",userDetailService.selUserDetailTotal(null,statusId,0));
        return "user/userMasterAll";
    }


    /**
     * 分页显示数据
     * @param pageCurrentNo
     * @param name
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "names",required = false,defaultValue = "") String name,
                        @RequestParam(value = "statusId",required = false,defaultValue = "") Integer status,
                        @RequestParam(value = "lineStatus",required = false,defaultValue = "") Integer lineStatus){
        if(lineStatus == 2 || lineStatus == null){
            lineStatus =0;
        }
        int total = userDetailService.selUserDetailTotal(name,status,lineStatus);
        Page page = new Page();
        page.setPageSize(10);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserDetail> userDetails = userDetailService.selUserDetailAll(pageCurrentNo,10,name,status,lineStatus);
        page.setList(userDetails);
        page.setZaiXian(userDetailService.selUserDetailTotal(null,status,1));
        page.setLiXian(userDetailService.selUserDetailTotal(null,status,0));
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
        return updUserDetail2(id, status, statusMessage);
    }
    public boolean updUserDetail2(Integer id,Integer status,String statusMessage ){
        Integer lineStatus = 0;
        User u =  userService.selUserByDetailId(id);
        UserDetail userDetail = userDetailService.selUserDetailById(id);
        String o = u.getOpenId();;
        if(status ==1 ){

            String openId =u.getOpenId();
            double[] pre = new double[]{5,2};
            UserScoreSource userScoreSource1 = scoreSourceService.selUserScore(new UserScoreSource(openId, 5));
            if(userScoreSource1 ==null ){   //之前没有审核过的，没有为这位师傅增加过积分的
                userService.updateUserByOpenId(new User(openId,pre[0],pre[0]));    //修改提交审核人的积分
                UserScoreSource userScoreSource = new UserScoreSource(openId,pre[0],5);
                scoreSourceService.insScoreSource(userScoreSource);   //记录成为师父的积分来源记录

                //修改推广人的积分
                for (int i = 0; i < 2; i++) {
                    User user = userService.selUserByOpenId(openId);
                    if(user !=null){
                        String userParent = user.getUserParent();
                        if(userParent !=null && !userParent.equals("")){
                            User parent = new User(userParent,pre[i],pre[i]);
                            userService.updateUserByOpenId(parent);
                            UserScoreSource ScoreSource = new UserScoreSource(userParent,pre[i],4,o);
                            scoreSourceService.insScoreSource(ScoreSource);   //记录分红的积分来源记录
                            Map map2 = new HashMap();
                            map2.put("openId",userParent);
                            map2.put("title","恭喜，您获得了一笔新的分销积分啦。来自："+user.getNickName());
                            map2.put("fenHong",String.valueOf(pre[i]));
                            map2.put("total",String.valueOf(pre[0]));
                            map2.put("url",URLS.DOMAIN_NAME+"/_api/goYongJin");
                            templateService.sendScore(map2);
                            openId = userParent;
                        }
                    }
                }
                lineStatus = 1;

            }
            UserDetail detail = new UserDetail(id,status,null,1,1);
            userDetailService.updateUserDetail(detail);

            // 发送审核成功的模板
            Map map = new HashMap();
            map.put("openId",o);
            map.put("name",userDetail.getName());
            map.put("url",URLS.DOMAIN_NAME+"/_api/goSFHone?id="+id);
            templateService.sendAuditSuccess(map);

            //给管理员发送模板信息
            String[] managerOpenId = Contents.MANAGER_OPENID;
            for (int j = 0; j <managerOpenId.length ; j++) {
                Map map2 = new HashMap();
                map2.put("openId",managerOpenId[j]) ;
                map2.put("template_id","HI9ygOFtJ_rbPK1JT3KD8ujsfIcaRBeCJrhQqgRZ0Oc") ;
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
            map.put("result","未通过");
            map.put("message",statusMessage);
            map.put("url", URLS.DOMAIN_NAME+"/_api/goRegister");
            templateService.sendAuditFail(map);
            UserDetail detail = new UserDetail(id,status,statusMessage,2,1);
            userDetailService.updateUserDetail(detail);

            //给管理员发送模板信息
            String[] managerOpenId = Contents.MANAGER_OPENID;
            for (int j = 0; j <managerOpenId.length ; j++) {
                Map map2 = new HashMap();
                map2.put("openId",managerOpenId[j]) ;
                map2.put("template_id","HI9ygOFtJ_rbPK1JT3KD8ujsfIcaRBeCJrhQqgRZ0Oc") ;
                map2.put("title","师傅审核未通过提醒") ;
                map2.put("serviceType","师傅审核失败通知") ;
                map2.put("orderNo","无") ;
                map2.put("orderState","审核未通过，无接单状态") ;
                map2.put("end","师傅信息："+userDetail.getName()+u.getPhone()) ;
                templateService.serviceStatus(map2);
            }
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

    /**
     * 进入用户信息查看界面
     * @return
     */
    @RequestMapping("/goUserMasterShow")
    public String goUserMasterShow(Integer id , Model model){
        model.addAttribute("yearWorks", userDetailService.selYearAll());
        model.addAttribute("parentSkills", userSkillService.selSkillName(null));
        model.addAttribute("userSkills", userSkillService.selAllSkills());
        model.addAttribute("sources", userDetailService.selSourceType());
        model.addAttribute("user", userService.selUserByDetailId(id));
        return "user/userMasterShow";
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
        Integer status = userDetailService.selUserDetailById(userDetail.getId()).getStatus();
        if(userScoreSource.getScore() !=0 ){
            userScoreSourceService.insScoreSource(userScoreSource);
        }
        userService.updateUserByOpenId(new User(userScoreSource.getOpenId(), phone,Double.valueOf(score),Double.valueOf(score)));
        userDetail.setShijian(1);
        userDetailService.updateUserDetail(userDetail);
        ScoreSourceType scoreSourceType = userScoreSourceService.selById(userScoreSource.getScoreSourceId());
        User user = userService.selUserByOpenId(userScoreSource.getOpenId());
        if (source == 1) {   // 发送模板
            String balanceScore ="剩余"+ user.getBalanceScore() +"可用积分";
            if(score >0){
                String scoress = score +"积分";
                Map map = new HashMap();
                map.put("openId",userScoreSource.getOpenId());
                map.put("title","积分增加提醒");
                map.put("changeType",userDetail.getName()+"您好，恭喜您获得【"+scoreSourceType.getSourceName()+"】积分");
                map.put("changeScore",scoress);
                map.put("totalScore",balanceScore);
                map.put("url", URLS.DOMAIN_NAME+"/_api/goYongJin");
                templateService.sendScoreChange(map);
            }else if(score==0){

            }else{

                Integer score2 = Math.abs(score);
                String scoress =score2 +"积分";
                Map map = new HashMap();
                map.put("openId",userScoreSource.getOpenId());
                map.put("title","积分扣除提醒");
                map.put("changeScore",scoress);
                map.put("totalScore",balanceScore);
                map.put("changeType",userDetail.getName()+"您好，由于您违规操作，本次扣除积分："+score2+"分");
                map.put("url", URLS.DOMAIN_NAME+"/_api/goYongJin");
                templateService.sendScoreChange(map);
            }

        } else {

        }
        if(status != userDetail.getStatus()){
            System.out.println("状态值改变了，调用审核方法");
            // updateUserDetail(userDetail.getId(),userDetail.getStatus(),userDetail.getStatusMessage());
            updUserDetail2(userDetail.getId(),userDetail.getStatus(),userDetail.getStatusMessage());
        }else{
            System.out.println("状态值没变，不调用审核方法");
        }
        return "user/userMasterAll";
    }

    /**
     * 查询工作年限
     * @param id
     * @return
     */
    @RequestMapping("/selYearWorkById")
    @ResponseBody
    public UserYearWork selYearWorkById(@RequestParam("id") Integer id){
        return userDetailService.selYearById(id);
    }

    /**
     * 批量删除
     * @return
     */
    @RequestMapping("/delMasterById")
    @ResponseBody
    public boolean delMasterById(String ids){
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        int res = userDetailService.delMasterById(array);   //删除师傅之后，该师傅身份变为用户
        userService.updbyDetailId(array);  //师傅身份变为用户
        return res > 0 ? true : false;
    }

}

