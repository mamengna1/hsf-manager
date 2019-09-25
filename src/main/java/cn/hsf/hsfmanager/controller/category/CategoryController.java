package cn.hsf.hsfmanager.controller.category;

import cn.hsf.hsfmanager.pojo.AdminType;
import cn.hsf.hsfmanager.pojo.platform.GraphicType;
import cn.hsf.hsfmanager.pojo.platform.ImgType;
import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserYearWork;
import cn.hsf.hsfmanager.service.admin.AdminTypeService;
import cn.hsf.hsfmanager.service.platform.GraphicService;
import cn.hsf.hsfmanager.service.platform.PlatformService;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import cn.hsf.hsfmanager.service.user.UserYearWorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/CategoryController")
public class CategoryController {

    //**********************图文类别开始**********************
    @Resource
    private GraphicService graphicService;
    /**
     * 进入图文类别 cate_graphic_all
     * @return
     */
    @RequestMapping("/goGraphicCategory")
    public String goGraphicCategory(Model model){
        model.addAttribute("graphicType", graphicService.selAllGraName());
        return "category/cate_graphic_all";
    }

    /**
     * 图文类别新增
     * @param graName
     * @return
     */
    @RequestMapping("/insGraphicType")
    @ResponseBody
    public boolean insGraphicType(String graName){
        return graphicService.insGraphicType(new GraphicType(graName)) > 0 ? true : false;
    }

    /**
     * 图文类别删除
     * @param id
     * @return
     */
    @RequestMapping("/delGraphicType")
    @ResponseBody
    public boolean delGraphicType(Integer id){
        return graphicService.delGraphicTypeById(id) > 0 ? true : false;
    }

    /**
     * 图文类别修改
     * @param graName
     * @return
     */
    @RequestMapping("/updGraphicType")
    @ResponseBody
    public boolean updGraphicType(Integer id,String graName){
        return graphicService.updGraphicType(new GraphicType(id,graName)) > 0 ? true : false;
    }

    /**
     * 根据图新类别id查询数据
     * @param id
     * @return
     */
    @RequestMapping("/selCategoryByGraTypeId")
    @ResponseBody
    public GraphicType selCategoryByGraTypeId(Integer id){
        return graphicService.selByGraId(id);
    }

    //**********************图文类别结束**********************





    //**********************轮播图类别开始**********************
    @Resource
    private PlatformService platformService;
    /**
     * 进入轮播图类别 cate_graphic_all
     * @return
     */
    @RequestMapping("/goImageTypeCategory")
    public String goImageTypeCategory(Model model){
        model.addAttribute("imageType", platformService.selImgTypeAll());
        return "category/cate_imageType_all";
    }

    /**
     * 轮播图类别新增
     * @param imageTypeName
     * @return
     */
    @RequestMapping("/insImageType")
    @ResponseBody
    public boolean insImageType(String imageTypeName){
        return platformService.insImgType(new ImgType(imageTypeName)) > 0 ? true : false;
    }

    /**
     * 轮播图类别删除
     * @param id
     * @return
     */
    @RequestMapping("/delImageType")
    @ResponseBody
    public boolean delImageType(Integer id){
        return platformService.delImgTypeById(id) > 0 ? true : false;
    }

    /**
     * 轮播图类别修改
     * @param imageName
     * @return
     */
    @RequestMapping("/updImageType")
    @ResponseBody
    public boolean updImageType(Integer id,String imageName){
        return platformService.updImgType(new ImgType(id,imageName)) > 0 ? true : false;
    }

    /**
     * 根据轮播图类别id查询数据
     * @param id
     * @return
     */
    @RequestMapping("/selCategoryByImageTypeId")
    @ResponseBody
    public ImgType selCategoryByImageTypeId(Integer id){
        return platformService.selById(id);
    }

    //**********************轮播图类别结束**********************







    //**********************积分来源类别开始**********************
    @Resource
    private UserScoreSourceService userScoreSourceService;

    /**
     * 进入积分来源类别 cate_graphic_all
     * @return
     */
    @RequestMapping("/goScoreTypeCategory")
    public String goScoreTypeCategory(Model model){
        model.addAttribute("scoreType", userScoreSourceService.selScoreType());
        return "category/cate_scoreType_all";
    }

    /**
     * 积分来源类别新增
     * @param sourceName
     * @return
     */
    @RequestMapping("/insScoreType")
    @ResponseBody
    public boolean insScoreType(String sourceName){
        return userScoreSourceService.insScoreSourceType(new ScoreSourceType(sourceName)) > 0 ? true : false;
    }

    /**
     * 积分来源类别删除
     * @param id
     * @return
     */
    @RequestMapping("/delScoreType")
    @ResponseBody
    public boolean delScoreType(Integer id){
        return userScoreSourceService.delScoreSourceTypeById(id) > 0 ? true : false;
    }

    /**
     * 积分来源类别修改
     * @param sourceName
     * @return
     */
    @RequestMapping("/updScoreType")
    @ResponseBody
    public boolean updScoreType(Integer id,String sourceName){
        return userScoreSourceService.updScoreSourceType(new ScoreSourceType(id,sourceName)) > 0 ? true : false;
    }

    /**
     * 根据积分来源类别id查询数据
     * @param id
     * @return
     */
    @RequestMapping("/selCategoryByScoreTypeId")
    @ResponseBody
    public ScoreSourceType selCategoryByScoreTypeId(Integer id){
        return userScoreSourceService.selById(id);
    }

    //**********************积分来源类别结束**********************







    //**********************工作年限类别开始**********************
    @Resource
    private UserYearWorkService userYearWorkService;

    /**
     * 进入工作年限类别 cate_graphic_all
     * @return
     */
    @RequestMapping("/goYearWorkCategory")
    public String goYearWorkCategory(Model model){
        model.addAttribute("yearWorks", userYearWorkService.selUserYearWorkAll());
        return "category/cate_yearWork_all";
    }

    /**
     * 工作年限类别新增
     * @param yearName
     * @return
     */
    @RequestMapping("/insYearWork")
    @ResponseBody
    public boolean insYearWork(String yearName){
        return userYearWorkService.insUserYearWork(new UserYearWork(yearName)) > 0 ? true : false;
    }

    /**
     * 工作年限类别删除
     * @param id
     * @return
     */
    @RequestMapping("/delYearWork")
    @ResponseBody
    public boolean delYearWork(Integer id){
        return userYearWorkService.delUserYearWorkById(id) > 0 ? true : false;
    }

    /**
     * 工作年限类别修改
     * @param yearName
     * @return
     */
    @RequestMapping("/updYearWork")
    @ResponseBody
    public boolean updYearWork(Integer id,String yearName){
        return userYearWorkService.updUserYearWork(new UserYearWork(id,yearName)) > 0 ? true : false;
    }

    /**
     * 根据工作年限类别id查询数据
     * @param id
     * @return
     */
    @RequestMapping("/selCategoryByYearWorkId")
    @ResponseBody
    public UserYearWork selCategoryByYearWorkId(Integer id){
        return userYearWorkService.selById(id);
    }

    //**********************工作年限类别结束**********************




    //**********************角色管理开始**********************
    @Resource
    private AdminTypeService adminTypeService;

    /**
     * 进入角色管理 cate_graphic_all
     * @return
     */
    @RequestMapping("/goAdminTypCategory")
    public String goAdminTypeCategory(Model model){
        model.addAttribute("adminTypes", adminTypeService.selAdminTypeList());
        return "category/cate_adminType_all";
    }

    /**
     * 角色管理新增
     * @param typeName
     * @return
     */
    @RequestMapping("/insAdminTyp")
    @ResponseBody
    public boolean insAdminTyp(String typeName){
        return adminTypeService.insAdminType(new AdminType(typeName)) > 0 ? true : false;
    }

    /**
     * 角色管理删除
     * @param id
     * @return
     */
    @RequestMapping("/delAdminTyp")
    @ResponseBody
    public boolean delAdminTyp(Integer id){
        return adminTypeService.delAdminTypeById(id) > 0 ? true : false;
    }

    /**
     * 角色管理修改
     * @param typeName
     * @return
     */
    @RequestMapping("/updAdminTyp")
    @ResponseBody
    public boolean updAdminTyp(Integer id,String typeName){
        return adminTypeService.updAdminType(new AdminType(id,typeName)) > 0 ? true : false;
    }

    /**
     * 根据角色管理id查询数据
     * @param id
     * @return
     */
    @RequestMapping("/selCategoryByAdminTypId")
    @ResponseBody
    public AdminType selCategoryByAdminTypId(Integer id){
        return adminTypeService.selAdminTypeById(id);
    }

    //**********************角色管理结束**********************


}
