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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        model.addAttribute("skills", userSkillService.selSkillName(null));
        return "user/userPaiDan";
    }


    /**
     * 分页显示数据  派单师傅
     * @param pageCurrentNo
     * @param  skillId    小类id
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "skillId",required = false,defaultValue = "-1")  String skillId,
                        @RequestParam(value = "serviceProvince",required = false,defaultValue = "")  Integer serviceProvince,
                        @RequestParam(value = "serviceCity",required = false,defaultValue = "")  Integer serviceCity,
                        @RequestParam(value = "serviceArea",required = false,defaultValue = "")  Integer serviceArea,
                        @RequestParam(value = "recId",required = false,defaultValue = "")  Integer recId){

        int total;
        List<UserDetail> userDetails = null;
        if(skillId == null || skillId.equals("-1") || skillId =="-1"){
           total  = userDetailService.selPaiDanTotal(null, serviceProvince, serviceCity, -1);
            userDetails = userDetailService.selPaiDanAll(pageCurrentNo, Contents.PAGENO,null,serviceProvince,serviceCity,-1);
        }else{
            total  = userDetailService.selPaiDanTotal(skillId, serviceProvince, serviceCity, -1);
            userDetails = userDetailService.selPaiDanAll(pageCurrentNo, Contents.PAGENO,skillId,serviceProvince,serviceCity,-1);
        }
        //判断是否存在
        String isExist = null;
        for (int i = 0; i <userDetails.size() ; i++) {
            //判断派单表是否已经存在
            Distribution distribution =  distributionService.selByResId(new Distribution(recId,userDetails.get(i).getId()));
            if(distribution == null){   //不存在
                isExist = "true";
            }else{
                isExist = "false";
            }
            userDetails.get(i).setIsExist(isExist);
        }

        System.out.println("total :" +total);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
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

        int n = userReleaseService.updateUserRelease( new UserRelease(id,1));  //状态改为1  接单中
        UserRelease userRelease = userReleaseService.selUserReleaseById(id);
        //判断派单表是否已经存在
        Distribution distribution = new Distribution(id,userDetailId);
        Distribution distribution1 =  distributionService.selByResId(distribution);
        if(distribution1 == null ){
            //往派单表中添加数据
            distributionService.insDistribution(distribution);

            //判断这名师傅是否已经拒单超过三次
            Distribution distribution2 = new Distribution();
            distribution2.setStatusId(3);
            distribution2.setSfId(userDetailId);
            distribution.setTag(1);
            List<Distribution> list = distributionService.selByDistribution(distribution2);
            if(list.size() >=3){
                System.out.println("===============这名师傅今天的拒单数量已经超过了3次，请及时处理！========================");
            }


            //给师傅以及用户发送模板信息
            Integer userId =  userReleaseService.selUserReleaseById(id).getUserId();   //发布人的id
            String userOpenId =  userService.selUserById(userId).getOpenId();  // 发布人的openId
            String userDetailOpenId =  userService.selUserByDetailId(userDetailId).getOpenId();   // 师傅的openId
            String name =  userReleaseService.selUserReleaseById(id).getNickName();
            String sfName = userDetailService.selUserDetailById(userDetailId).getName();
            String sfPhone = userService.selUserByDetailId(userDetailId).getPhone();

            Integer pdId = distributionService.selByResId(distribution).getId();
            System.out.println("============================派单id  ： "  + pdId );


            //给用户发送推荐师傅的模板信息
            Map map = new HashMap();
            map.put("openId",userOpenId);
            map.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
            map.put("url","http://java.86blue.cn/_api/goUserOrderDetail?id="+id);
            map.put("title",name+"您好，你发布的信息我们已经接收到，并为您推荐【"+sfName+"】这位师傅为您服务");
            map.put("messageType","雇佣消息通知");
            map.put("end","师傅信息："+sfName+sfPhone);
            templateService.sendTongYong(map);

            //给师傅发送用户招聘信息
            Map map2 = new HashMap();
            map2.put("openId",userDetailOpenId);
            map2.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
            map2.put("url","http://java.86blue.cn/_api/goOrderShow?id="+pdId);
            map2.put("title",sfName+"您好，【"+name+"】顾客发送的雇佣信息与您符合，平台将为您接单，您可以选择 【接受或拒绝】");
            map2.put("messageType","新订单消息通知");
            map2.put("end","用户信息："+userRelease.getNickName()+userRelease.getPhone());
            templateService.sendTongYong(map2);

            String[] managerOpenId = Contents.MANAGER_OPENID;
            //给管理员发送模板信息
            for (int j = 0; j <managerOpenId.length ; j++) {
                Map map3 = new HashMap();
                map3.put("openId",managerOpenId[j]) ;
                map3.put("template_id","TF2-OgTgYB6EYKzmno0NjbZobdCadK7U0d0E9O9ZogA") ;
                map3.put("title","平台推荐师傅信息") ;
                map3.put("serviceType",userRelease.getTitle()) ;
                map3.put("orderNo","无") ;
                map3.put("orderState","新消息提醒") ;
                map3.put("end","用户信息："+userRelease.getNickName()+userRelease.getPhone()+"\\n推荐师傅信息 ："+sfName+sfPhone) ;
                templateService.serviceStatus(map3);
            }
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
    public String goUpdUserRelease(@RequestParam("id") Integer id,Integer mark,Model model){
        UserRelease userRelease = userReleaseService.selUserReleaseById(id);
        model.addAttribute("userRelease",userRelease);
        model.addAttribute("mark",mark);
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
    public String updUserRelease(UserRelease userRelease, @RequestParam("appointTimes") String appointTimes, @RequestParam("mark") Integer mark, HttpServletResponse response, HttpServletRequest request) throws IOException {
        int n = userReleaseService.updateUserRelease2(userRelease,appointTimes);
    /*    response.setHeader("Content-Type","text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('修改成功')</script>");
        response.sendRedirect("redirect:/userRelease/goUserReleaseAll?mark="+mark);
        out.close();*/
        return "redirect:/userRelease/goUserReleaseAll?mark="+mark;
    }
}
