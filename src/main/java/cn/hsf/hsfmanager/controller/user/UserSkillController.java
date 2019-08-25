package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.UserSkill;
import cn.hsf.hsfmanager.service.user.UserSkillService;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/userSkill")
public class UserSkillController {

    @Resource
    private UserSkillService userSkillService;

    /**
     * 根据id技能名称
     * @param id
     * @return
     */
    @RequestMapping("/getUserSkill")
    @ResponseBody
    public UserSkill getUserSkill(@RequestParam(value = "id",required = false,defaultValue = "-1") Integer id){
        System.out.println("id : "+ id);
        UserSkill userSkill = userSkillService.selNameById(id);
        System.out.println(userSkill);
        return userSkillService.selNameById(id);
    }

    /**
     * 去到技能管理页面
     * @return
     */
    @RequestMapping("/goAllSkill")
    public String goAllSkill(){
        return "user/userSkill";
    }

    /**
     * 分页显示数据   技能
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/skillAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo){
        int total = userSkillService.selSkillTotal();
        Page page = new Page();
        page.setPageSize(10);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserSkill> userSkillList = userSkillService.selAllSkill(pageCurrentNo,10);
        page.setList(userSkillList);
        return page;
    }

}
