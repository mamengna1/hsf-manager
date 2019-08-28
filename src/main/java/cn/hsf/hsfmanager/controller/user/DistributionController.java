package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.DistributionService;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserReleaseService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 派单详情
 */
@Controller
@RequestMapping("/distribution")
public class DistributionController {

    @Resource
    private DistributionService distributionService;
    @Resource
    private UserReleaseService userReleaseService;
    @Resource
    private UserService userService;
    @Resource
    private TemplateService templateService;
    @Resource
    private UserDetailService userDetailService;
    /**
     * 进入派单页面
     * @return
     */
    @RequestMapping("/goDistribution")
    public String goDistribution(){
        return "user/distributionAll";
    }


    /**
     * 分页显示派单详情
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo){
        int total = distributionService.selDistributionTotal();
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<Distribution> userDetails =distributionService.selDistributionAll(pageCurrentNo, Contents.PAGENO);
        page.setList(userDetails);
        return page;
    }

    /**
     * 确认完工
     * @param id
     * @return
     */
    @RequestMapping("/confirmAll")
    @ResponseBody
    public boolean confirmAll(Integer id){
        Distribution distribution = new Distribution();
        distribution.setId(id);
        distribution.setStatusId(6);
        int n = distributionService.updDistribution(distribution);

        //给用户以及师傅发送模板通知
        Distribution distribution1 = distributionService.selByResId(new Distribution(id));
        UserRelease userRelease = userReleaseService.selUserReleaseById(distribution1.getReleaseId());
        String userOpenId =   userService.selUserById( userRelease.getUserId()).getOpenId();   // 用户userOpenId
        String sfOpenId = userService.selUserByDetailId( userRelease.getReceiveId()).getOpenId();   // 接单师傅id
        Map map = new HashMap();
        map.put("openId",userOpenId);
        map.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
        map.put("title",userService.selUserByOpenId(userOpenId).getNickName()+"您好，您此次的雇佣信息已经完工，平台已为您确认完工");
        map.put("messageType","雇佣完工信息通知");
        map.put("end","感谢您的使用，如有疑问请致电000000");
        templateService.sendTongYong(map);

        Map map1 = new HashMap();
        map1.put("openId",sfOpenId);
        map1.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
        map1.put("title",userDetailService.selUserDetailById(userRelease.getReceiveId()).getName()+"您好，您提交的信息平台已确认核实，已为您更改");
        map1.put("messageType","雇佣完工信息通知");
        map1.put("end","感谢您的使用，如有疑问请致电000000");
        templateService.sendTongYong(map1);

        //此处先省略默认给师父打分的环节

        return  n > 0 ? true : false;
    }
}
