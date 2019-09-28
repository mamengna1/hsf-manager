package cn.hsf.hsfmanager;

import cn.hsf.hsfmanager.controller.platform.SlideShowController;
import cn.hsf.hsfmanager.pojo.StateMessage;
import cn.hsf.hsfmanager.pojo.platform.Graphic;
import cn.hsf.hsfmanager.service.platform.GraphicService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import cn.hsf.hsfmanager.util.URLS;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UeController {

    @Resource
    private GraphicService graphicService;
    private SlideShowController slideShowController = new SlideShowController();
    /**
     * 进入新增编辑框页面
     * @return
     */
    @RequestMapping("/goGraphic")
    public String goGraphic(Model model){
        model.addAttribute("graName",graphicService.selAllGraName());
        return "platform/graphic_edit";
    }



    @RequestMapping("/insGraphic")
    @ResponseBody
    public boolean insGraphic(Graphic graphic){
        int res = graphicService.insGraphic(graphic);
        return res > 0 ? true : false;
    }
    /**
     * 进入查询全部图文列表页面
     * @return
     */
    @RequestMapping("/goGraphicAllView")
    public String goGraphicAllView(Model model){
        model.addAttribute("graName",graphicService.selAllGraName());
        return "platform/graphic_all";
    }

    /**
     * 分页显示图文管理
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/graphicAll")
    @ResponseBody
    public Page userAllIndex(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                             @RequestParam(value = "titles",required = false,defaultValue = "") String title,@RequestParam(value = "graTypeId",required = false,defaultValue = "-1") Integer graTypeId ){
        int total = graphicService.selAllListCount(title,graTypeId);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<Graphic> graphicList =graphicService.selAllGraphicList(pageCurrentNo, Contents.PAGENO,title,graTypeId);
        page.setList(graphicList);
        return page;
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delGraphicById")
    @ResponseBody
    public boolean delGraphicById(@RequestParam String ids) {
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        int res = graphicService.delGraphicById(array);
        return res > 0 ? true : false;
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @RequestMapping("/delGeaphicSingleById")
    @ResponseBody
    public boolean delById(Integer id){
        return  graphicService.delGraphic(id) > 0 ? true : false;
    }

    /**
     * 携带数据进入修改
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/goUpdateUe")
    public String goUpdateUe(@RequestParam("id") Integer id, Model model){
       model.addAttribute("graphic",graphicService.selGraphicById(id));
        model.addAttribute("graName",graphicService.selAllGraName());
        return "platform/graphic_upd";
    }

    /**
     * 上传图片  保存修改缩略图时
     * @param multipartFile
     * @return
     */
    @RequestMapping("/graphicSaveImageUrl")
    @ResponseBody
    public StateMessage saveImageUrl(@RequestParam("attach") MultipartFile multipartFile){
        return slideShowController.showImageUrl(URLS.GRAPHIC,multipartFile,"graphic");
    }

    /**
     * 保存修改结果
     */
    @RequestMapping("/updateGraphic")
    @ResponseBody
    public boolean updateSlide(Graphic graphic,@RequestParam(value = "attach",required = false) MultipartFile multipartFile,
                               @RequestParam(value = "urlHidden",required = false,defaultValue = "") String urlHidden) {
        try {

            if (multipartFile !=null &&(!multipartFile.isEmpty()) ) {
                delFile(graphic.getId());
                graphic.setImageUrl(urlHidden);
            }else if(graphic.getImageUrl() !=null && graphic.getImageUrl() !=""){
                graphic.setImageUrl(graphic.getImageUrl());
            }else{
                delFile(graphic.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        graphic.setMark(1);
        int res = graphicService.updGraphic(graphic);
        return  res > 0 ? true : false;
    }
    /**
     * 删除图片
     * @param id
     */
    public void delFile(Integer id){
        String path1 = graphicService.selGraphicById(id).getImageUrl();
        if(path1 !=null && (!path1.isEmpty()) && (! path1.equals(""))){
            String path = URLS.IMAGE_ADDRESS+path1.substring(URLS.SUB_LENGTH,path1.length());   // http://java.86blue.cn/images/
            File file = new File(path);

            if(file.exists()){
                if(file.delete()){ // 删除LOGO图片的服务器存储路径
                    graphicService.delFile(id);  //更新表
                }
            }
        }

    }

}
