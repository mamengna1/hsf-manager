package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.pojo.user.DistributionStatus;
import cn.hsf.hsfmanager.pojo.user.UserOrder;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.*;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
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
    @Resource
    private UserOrderService userOrderService;

    /**
     * 进入派单页面  接单记录
     * @return
     */
    @RequestMapping("/goDistributionIndex")
    public String goDistributionIndex(@RequestParam(value = "sfId",required = false,defaultValue = "-1") Integer sfId,Model model){
        List<DistributionStatus> distributionStatuses = distributionService.selAllDisName();
        model.addAttribute("distributionStatuses",distributionStatuses);
        model.addAttribute("sfId",sfId);
        return "user/distributionIndex";
    }

    /**
     * 分页显示派单详情   单个师傅接单记录
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAllIndex")
    @ResponseBody
    public Page userAllIndex(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "statusId",required = false,defaultValue = "-1") Integer statusId,
                             @RequestParam(value = "sfId",required = false,defaultValue = "-1") Integer sfId){
        int total = distributionService.selDisTotal(statusId,sfId);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<Distribution> userDetails =distributionService.selDisList(pageCurrentNo, Contents.PAGENO,statusId,sfId);
        page.setList(userDetails);
        return page;
    }




    /**
     * 确认完工
     * @param id  派单表id
     * @return
     */
    @RequestMapping("/confirmAll")
    @ResponseBody
    public boolean confirmAll(Integer id){
        Distribution distribution1 = distributionService.selByResId(new Distribution(id));
        UserRelease userRelease = userReleaseService.selUserReleaseById(distribution1.getReleaseId());

        //修改派单表
        Distribution distribution = new Distribution();
        distribution.setId(id);
        distribution.setStatusId(6);
        int n = distributionService.updDistribution(distribution);
        //修改下单表
        UserRelease userRelease1 = new UserRelease(distribution1.getReleaseId(),6);
        userReleaseService.updateUserRelease(userRelease1);
        //修改默认给师父5星好评
        UserOrder userOrder = new UserOrder(distributionService.selByResId(new Distribution(id)).getOrderId(),"平台默认好评",5,2, userRelease.getUserId(),userRelease.getReceiveId());
        int i = userOrderService.updUserOrder(userOrder);
        System.out.println("默认给师父5份好评结果 ： "+ i);


        //给用户以及师傅发送模板通知

        String userOpenId =   userService.selUserById( userRelease.getUserId()).getOpenId();   // 用户userOpenId
        String sfOpenId = userService.selUserByDetailId( userRelease.getReceiveId()).getOpenId();   // 接单师傅id

        String sfName = userDetailService.selUserDetailById(userRelease.getReceiveId()).getName();
        String sfPhone = userService.selUserByDetailId( userRelease.getReceiveId()).getPhone();
        String orderNo = distributionService.selByResId(new Distribution(id)).getOrderId()+"";

        System.out.println("给用户发送的id ： "+ distribution1.getReleaseId() +"\t给师傅发送的id :"+id);
        //给用户发送模板信息
        Map map = new HashMap();
        map.put("openId",userOpenId) ;
        map.put("url","http://java.86blue.cn/_api/goUserOrderDetail?id="+distribution1.getReleaseId());
        map.put("template_id","TF2-OgTgYB6EYKzmno0NjbZobdCadK7U0d0E9O9ZogA") ;
        map.put("title","服务已经顺利完工") ;
        map.put("serviceType",userRelease.getTitle()) ;
        map.put("orderNo",orderNo) ;
        map.put("orderState","已完工") ;
        map.put("end","师傅信息："+sfName+sfPhone) ;
        templateService.serviceStatus(map);

        //给师傅发送模板信息
        Map map1 = new HashMap();
        map1.put("openId",sfOpenId) ;
        map1.put("url","http://java.86blue.cn/_api/goOrderShow?id="+id);
        map1.put("template_id","TF2-OgTgYB6EYKzmno0NjbZobdCadK7U0d0E9O9ZogA") ;
        map1.put("title","服务已经顺利完工") ;
        map1.put("serviceType",userRelease.getTitle()) ;
        map1.put("orderNo",orderNo) ;
        map1.put("orderState","已完工") ;
        map1.put("end","用户信息："+userRelease.getNickName()+userRelease.getPhone()) ;
        templateService.serviceStatus(map1);

        String[] managerOpenId = Contents.MANAGER_OPENID;
        //给管理员发送模板信息
        for (int j = 0; j <managerOpenId.length ; j++) {
            Map map2 = new HashMap();
            map2.put("openId",managerOpenId[j]) ;
            map2.put("template_id","TF2-OgTgYB6EYKzmno0NjbZobdCadK7U0d0E9O9ZogA") ;
            map2.put("title","服务已经顺利完工") ;
            map2.put("serviceType",userRelease.getTitle()) ;
            map2.put("orderNo",orderNo) ;
            map2.put("orderState","已完工") ;
            map2.put("end","用户信息："+userRelease.getNickName()+userRelease.getPhone()+"\\n师傅信息 ："+sfName+sfPhone) ;
            templateService.serviceStatus(map2);
        }
        return  n > 0 ? true : false;
    }

    /**
     * 渲染修改数据
     * @param id
     * @return
     */
    @RequestMapping("/updDelById")
    @ResponseBody
    public Distribution updDelById(Integer id){
        return distributionService.selDistributionById(id) ;
    }

    /**
     * 保存修改状态的结果 "id":id,"statusId":statusId,"refusedMessage":refusedMessage
     * @return
     */
    @RequestMapping("/saveDis")
    @ResponseBody
    public boolean saveDis(Integer id,Integer statusId,String refusedMessage){
        Distribution distribution = new Distribution(id,statusId,refusedMessage);
        return  distributionService.updDistribution(distribution) > 0 ? true : false;
    }

    /**
     * 删除
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delDetailById")
    @ResponseBody
    public boolean delDetailById(Integer id){
        return distributionService.delDetailById(id) > 0 ? true : false;
    }
}
