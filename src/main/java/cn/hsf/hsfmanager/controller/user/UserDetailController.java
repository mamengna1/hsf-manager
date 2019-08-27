package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.*;
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
import java.util.List;

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
        int total = userDetailService.selPaiDanTotal(skillId, serviceProvince, serviceCity, serviceArea);
        System.out.println("total :" +total);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserDetail> userDetails = userDetailService.selPaiDanAll(pageCurrentNo, Contents.PAGENO,skillId,serviceProvince,serviceCity,serviceArea);
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
        distributionService.insDistribution(distribution);

        //给师傅以及用户发送模板信息，师父确认接单状态改为2
       Integer userId =  userReleaseService.selUserReleaseById(id).getUserId();   //发布人的id
       String userOpenId =  userService.selUserById(userId).getOpenId();  // 发布人的openId
       String userDetailOpenId =  userService.selUserByDetailId(userDetailId).getOpenId();   // 师傅的openId

        return  n > 0 ? true : false;
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
