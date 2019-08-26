package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.UserRelease;
import cn.hsf.hsfmanager.service.user.UserReleaseService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
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

    /**
     * 进入雇佣信息列表
     * @return
     */
    @RequestMapping("/goUserReleaseAll")
    public String goUserReleaseAll(){
        return "user/userReleaseAll";
    }


    /**
     * 分页显示数据   雇佣列表数据
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "state",required = false,defaultValue = "") Integer state){
        int total = userReleaseService.selUserReleaseTotal(state);
        Page page = new Page();
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserRelease> userReleases =userReleaseService.selUserReleaseAll(pageCurrentNo,Contents.PAGENO,state);
        System.out.println("发布信息 ; "+ userReleases);
        page.setList(userReleases);
        return page;
    }


}
