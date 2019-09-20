package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.UserReleaseService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/userRelease")
public class UserReleaseController {
    @Resource
    private UserReleaseService userReleaseService;
    @Resource
    private UserService userService;

    /**
     * 进入雇佣信息列表
     * @return
     */
    @RequestMapping("/goUserReleaseAll")
    public String goUserReleaseAll(@RequestParam(value = "mark",required = false,defaultValue = "1") Integer mark,Model model){
        model.addAttribute("mark",mark);
        return "user/userReleaseAll";
    }


    /**
     * 分页显示数据   雇佣列表数据  全部订单
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "state",required = false,defaultValue = "") Integer state,
                        @RequestParam(value = "mark",required = false,defaultValue = "") Integer mark){
        int total = userReleaseService.selUserReleaseTotal(state,mark);
        Page page = new Page();
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserRelease> userReleases =userReleaseService.selUserReleaseAll(pageCurrentNo,Contents.PAGENO,state,mark);
        page.setList(userReleases);
        return page;
    }

    /**
     * 去到查看信息界面
     * @param id
     * @return
     */
    @RequestMapping("/goShowUserRelease")
    public String goUpdUserRelease(@RequestParam("id") Integer id,Model model){
        UserRelease userRelease = userReleaseService.selUserReleaseById(id);
        model.addAttribute("userRelease",userRelease);
        User user = userService.selUserById(userRelease.getUserId());
        model.addAttribute("user",user);
        return "user/userReleaseShow";
    }

    @RequestMapping("/delRelById")
    @ResponseBody
    public boolean delRelById(String ids){
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        int res = userReleaseService.delRelById(array);
        return res > 0 ? true : false;
    }

    @RequestMapping("/selReleaseById")
    @ResponseBody
    public UserRelease selReleaseById(Integer id){
        return  userReleaseService.selUserReleaseById(id);
    }
}
