package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.*;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.DateUtil;
import cn.hsf.hsfmanager.util.Page;
import org.apache.ibatis.annotations.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 派单
 */
@Controller
@RequestMapping("/userDetail")
public class UserDetailController {

    @Resource
    private UserReleaseService userReleaseService;
    @Resource
    private UserDetailService userDetailService;
    @Resource
    private UserSkillService userSkillService;
    @Resource
    private UserService userService;
    @Resource
    private DistributionService distributionService;
    @Resource
    private TemplateService templateService;

    /**
     * 去到派单页面
     * @param model
     * @return
     */
    @RequestMapping("/goPaiDan")
    public String goPaiDan(@RequestParam("id") Integer id, Model model){
        UserRelease userRelease = userReleaseService.selUserReleaseById(id);
        model.addAttribute("userRelease",userRelease);
        model.addAttribute("skills", userSkillService.selAll());
        return "user/userPaiDan";
    }


    /**
     * 分页显示数据  派单师傅
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "skillId",required = false,defaultValue = "-1")  Integer skillId,
                        @RequestParam(value = "serviceProvince",required = false,defaultValue = "")  Integer serviceProvince,
                        @RequestParam(value = "serviceCity",required = false,defaultValue = "")  Integer serviceCity,
                        @RequestParam(value = "serviceArea",required = false,defaultValue = "")  Integer serviceArea){
        int total = userDetailService.selPaiDanTotal(skillId, serviceProvince, serviceCity, -1);
        System.out.println("total :" +total);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserDetail> userDetails = userDetailService.selPaiDanAll(pageCurrentNo, Contents.PAGENO,skillId,serviceProvince,serviceCity,-1);
        page.setList(userDetails);
        return page;
    }


    /**
     * 确认派单
     * @param id   发布信息id
     * @param userDetailId   接单师傅的id
     * @return
     */
    @RequestMapping("/updPaiDan")
    @ResponseBody
    public boolean updPaiDan(Integer id,Integer userDetailId){
        UserRelease userRelease = new UserRelease(id,1);   //状态改为1  接单中
        int n = userReleaseService.updateUserRelease(userRelease);

        //往派单表中添加数据
        Distribution distribution = new Distribution(id,userDetailId);
        Distribution distribution1 =  distributionService.selByResId(distribution);
        if(distribution1 == null ){
            distributionService.insDistribution(distribution);

            //给师傅以及用户发送模板信息，师父确认接单状态改为2
            Integer userId =  userReleaseService.selUserReleaseById(id).getUserId();   //发布人的id
            String userOpenId =  userService.selUserById(userId).getOpenId();  // 发布人的openId
            String userDetailOpenId =  userService.selUserByDetailId(userDetailId).getOpenId();   // 师傅的openId
            String name =  userReleaseService.selUserReleaseById(id).getNickName();
            String sfName = userDetailService.selUserDetailById(userDetailId).getName();

            Integer pdId = distributionService.selByResId(distribution).getId();
            System.out.println("============================派单id  ： "  + pdId );


            //给用户发送推荐师傅的模板信息
            Map map = new HashMap();
            map.put("openId",userOpenId);
            map.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
            map.put("url","http://java.86blue.cn/_api/goUserOrderDetail?id="+id);
            map.put("title",name+"您好，你发布的信息我们已经接收到，并为您推荐【"+sfName+"】这位师傅为您服务");
            map.put("messageType","雇佣消息通知");
            map.put("end","感谢您的使用，如有疑问请致电000000");
            templateService.sendTongYong(map);

            //给师傅发送用户招聘信息
            Map map2 = new HashMap();
            map2.put("openId",userDetailOpenId);
            map2.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
            map2.put("url","http://java.86blue.cn/_api/goOrderShow?id="+pdId);
            map2.put("title",sfName+"您好，【"+name+"】顾客发送的雇佣信息与您符合，平台将为您接单，您可以选择 【接受或拒绝】");
            map2.put("messageType","新订单消息通知");
            map2.put("end","感谢您的使用，如有疑问请致电000000");
            templateService.sendTongYong(map2);
            return true;
        }
        return false;

    }

    /**
     * 去到修改界面
     * @param id
     * @return
     */
    @RequestMapping("/goUpdUserRelease")
    public String goUpdUserRelease(@RequestParam("id") Integer id,Model model){
        UserRelease userRelease = userReleaseService.selUserReleaseById(id);
        model.addAttribute("userRelease",userRelease);
        return "user/userReleaseUpd";
    }

    /**
     * 渲染
     * @param id
     * @return
     */
    @RequestMapping("/selUserReleaseById")
    @ResponseBody
    public UserRelease selUserReleaseById(@RequestParam("id") Integer id){
        return userReleaseService.selUserReleaseById(id);
    }

    @RequestMapping("/updUserRelease")
    public String updUserRelease(UserRelease userRelease,@RequestParam("appointTimes") String appointTimes){
        int n = userReleaseService.updateUserRelease2(userRelease,appointTimes);
        return n > 0 ? "user/userReleaseAll" : "";
    }
}
