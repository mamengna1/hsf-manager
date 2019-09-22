package cn.hsf.hsfmanager.controller.category;

import cn.hsf.hsfmanager.pojo.platform.GraphicType;
import cn.hsf.hsfmanager.service.platform.GraphicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/CategoryController")
public class CategoryController {

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
}
