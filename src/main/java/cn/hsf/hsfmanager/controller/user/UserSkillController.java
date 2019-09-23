package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.controller.platform.SlideShowController;
import cn.hsf.hsfmanager.pojo.StateMessage;
import cn.hsf.hsfmanager.pojo.user.UserSkills;
import cn.hsf.hsfmanager.service.user.UserSkillService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import cn.hsf.hsfmanager.util.URLS;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/userSkill")
public class UserSkillController {

    @Resource
    private UserSkillService userSkillService;
    private SlideShowController slideShowController = new SlideShowController();

    /**
     * 去到技能管理页面
     * @return
     */
    @RequestMapping("/goAllSkills")
    public String goAllSkills(Model model){
        List<UserSkills> userSkills =  userSkillService.selSkillName(null);
        model.addAttribute("userSkills",userSkills);
        return "user/userSkills";
    }

    /**
     * 显示技能级别分类
     * @return
     */
    @RequestMapping("/showSkills")
    @ResponseBody
    public Object showLevel1(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                             @RequestParam(value = "parentId",required = false) Integer parentId ){

        Page page = new Page();
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);

        int total =0;
        List<UserSkills> userSkillList = null;
        if(parentId ==null || parentId == -1 ){
            total = userSkillService.selSkillsTotal(null);
            userSkillList     = userSkillService.selByParent(pageCurrentNo, Contents.PAGENO,null);
        }else {
            total = userSkillService.selSkillsTotal(Integer.valueOf(parentId));
            userSkillList     = userSkillService.selByParent(pageCurrentNo, Contents.PAGENO,Integer.valueOf(parentId));
        }
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        page.setList(userSkillList);
        return page;
    }


    /**
     * 新增父类 子类
     * @param skillName
     * @return
     */
    @RequestMapping("/saveSkills")
    @ResponseBody
    public boolean saveSkills(String skillName,Integer parentId ,@RequestParam(value = "describes",required = false,defaultValue = "") String describes,String url,Integer isRecommend){
        UserSkills userSkills = null ;
        if(parentId == null || parentId == -1){
            userSkills = new UserSkills(skillName,null,null,url,isRecommend);

        }else{
            userSkills = new UserSkills(skillName,parentId,describes,url,isRecommend);
        }
        if(isRecommend == 1){
            userSkills.setUpdDate(new Date());
        }else{
            userSkills.setUpdDate(null);
        }
        return userSkillService.insertUserSkills(userSkills) > 0 ? true : false;
    }

    /**
     * 上传图片  保存技能图标
     * @param multipartFile
     * @return
     */
    @RequestMapping("/userSkillSaveImageUrl")
    @ResponseBody
    public StateMessage userSkillSaveImageUrl(@RequestParam("attach") MultipartFile multipartFile){
        return slideShowController.showImageUrl(URLS.SKILL_ICON,multipartFile,"skill");
    }

    /**
     * 验证是否已经存在相同的技能名称
     * @param skillName
     * @return
     */
    @RequestMapping("/checkName")
    @ResponseBody
    public boolean checkName(String skillName){
        UserSkills userSkills = new UserSkills(skillName);
        return  userSkillService.selUserSkills(userSkills) == null ? true : false;
    }

    /**
     * 根据id信息
     * @param id
     * @return
     */
    @RequestMapping("/selSkillsById")
    @ResponseBody
    public UserSkills selSkillsById(Integer id){
       return userSkillService.selUserSkills(new UserSkills(id));
    }


    /**
     * 保存修改结果
     * @return
     */
    @RequestMapping("/saveUpdSkill")
    @ResponseBody
    public boolean saveUpdSkill(Integer id,String skillName,Integer parentId ,@RequestParam(value = "describes",required = false,defaultValue = "") String describes,String url,Integer isRecommend){
        UserSkills userSkills = null;
        if(parentId == null || parentId == -1){
            userSkills = new UserSkills(id,skillName,null,null);
        }else{
            userSkills = new UserSkills(id,skillName,parentId,describes);
        }
        userSkills.setImgUrl(url);
        userSkills.setIsRecommend(isRecommend);
        if(isRecommend == 1){
            userSkills.setMark(1);
        }else if(isRecommend == 2){
            userSkills.setMark(2);
        }
        return userSkillService.updSkills(userSkills) > 0 ? true : false;
    }

    /**
     * 直接删除子类
     * @param id
     * @return
     */
    @RequestMapping("/delSkillsById")
    @ResponseBody
    public boolean delSkillsById(Integer id){
       return userSkillService.delSkillsById(id) > 0 ? true : false;
    }

    /**
     * 删除父类
     * @param id
     * @return
     */
    @RequestMapping("/delSkillsByFu")
    @ResponseBody
    public boolean delSkillsByFu(Integer id){
        //先删除子类parentId = id的
        int i = userSkillService.delSkillsByParentId(id);
        //再删除父类
        int res = userSkillService.delSkillsById(id);
        return res > 0 ? true : false;
    }


    /**
     * 显示级别分类
     * @return
     */
    @RequestMapping("/showLevel")
    @ResponseBody
    public Object showLevel1(@RequestParam(value = "parentId",required = false) String parentId ){

        List<UserSkills> UserSkills = null;
        if(parentId ==null || parentId ==""){
            UserSkills     = userSkillService.selSkillName(null);
        }else {
            UserSkills     = userSkillService.selSkillName(Integer.valueOf(parentId));
        }
        return UserSkills;
    }

    /**
     * 推荐  取消推荐
     * @param id
     * @param isRecommend
     * @return
     */
    @RequestMapping("/goRecommend")
    @ResponseBody
    public boolean goRecommend(Integer id,Integer isRecommend){
        UserSkills userSkills = new UserSkills(id, isRecommend);
        if(isRecommend == 1){
            userSkills.setMark(1);
        }else if(isRecommend == 2){
            userSkills.setMark(2);
        }
        System.out.println("userSkills ； "+userSkills);
        return  userSkillService.updSkills(userSkills) > 0 ? true : false;
    }
}
