package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserReleaseService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    /**
     * 去到派单页面
     * @param model
     * @return
     */
    @RequestMapping("/goPaiDan")
    public String goPaiDan(@RequestParam("id") Integer id, Model model){
        UserRelease userRelease = userReleaseService.selUserReleaseById(id);
        model.addAttribute("userRelease",userRelease);
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
                        @RequestParam(value = "workProvince",required = false,defaultValue = "")  Integer workProvince,
                        @RequestParam(value = "workCity",required = false,defaultValue = "")  Integer workCity,
                        @RequestParam(value = "workArea",required = false,defaultValue = "")  Integer workArea){
        System.out.println(skillId+"- " +workProvince + "- "+ workCity +"- "+ workArea);
        int total = userDetailService.selPaiDanTotal(skillId, workProvince, workCity, workArea);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserDetail> userDetails = userDetailService.selPaiDanAll(pageCurrentNo, Contents.PAGENO,skillId,workProvince,workCity,workArea);
        System.out.println("派单师傅信息" + userDetails);
        page.setList(userDetails);
        return page;
    }

}
