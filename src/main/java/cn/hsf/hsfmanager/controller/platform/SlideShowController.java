package cn.hsf.hsfmanager.controller.platform;

import antlr.StringUtils;
import cn.hsf.hsfmanager.pojo.StateMessage;
import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;
import cn.hsf.hsfmanager.service.platform.PlatformService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import cn.hsf.hsfmanager.util.URLS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/slideshow")
@Controller
public class SlideShowController {

    @Autowired
    private PlatformService platformService;


    /**
     * 进入幻灯片界面
     * @return
     */
    @RequestMapping("/goSlide")
    public String goslideshow(){
        return "platform/slideShowAll";
    }


    /**
     * 分页显示幻灯片管理
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/slideShowAll")
    @ResponseBody
    public Page userAllIndex(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                             @RequestParam(value = "imgType",required = false,defaultValue = "-1") Integer imgType){
        System.out.println("====================我进入了幻灯片界面===================");
        int total = platformService.selSlideShowTotal(imgType);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<PlatformSlideshow> platformSlideshows =platformService.selSlideShowAll(pageCurrentNo, Contents.PAGENO,imgType);
        page.setList(platformSlideshows);
        return page;
    }

    /**
     * 进入新增界面
     * @return
     */
    @RequestMapping("/goCreateSlide")
    public String goCreateSlide(Model model){
        model.addAttribute("imgTypes",platformService.selImgTypeAll());
        return "platform/createSlideShow";
    }
    /**
     * 保存新增结果
     * @return
     */
    @RequestMapping("/insSlideShow")
    @ResponseBody
    public boolean insSlideShow(PlatformSlideshow plat){
        int res = platformService.insSlideshow(plat);
        return res > 0 ? true : false;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delSlideById")
    @ResponseBody
    public boolean delSlideById(@RequestParam String ids) {
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        int res = platformService.delSlideById(array);
        return res > 0 ? true : false;
    }

    /**
     * 单个删除
     * @param id
     * @return
     */
    @RequestMapping("/delById")
    @ResponseBody
    public boolean delById(Integer id){
        return  platformService.delSlideSingleById(id) > 0 ? true : false;
    }
    /**
     * 上传图片  （新增时）
     * @param multipartFile
     * @return
     */
    @RequestMapping("/saveImageUrl")
    @ResponseBody
    public StateMessage saveImageUrl(@RequestParam("attach") MultipartFile multipartFile){
        return showImageUrl(URLS.SLIDE_SHOW,multipartFile,"slide");
    }

    public StateMessage showImageUrl(String temPath,MultipartFile multipartFile,String preName){
        String fileName = null;
        String savePath =  null;
        try {
            if (multipartFile !=null &&(!multipartFile.isEmpty())) {
                //取得当前上传文件的文件名称
                String myFileName = multipartFile.getOriginalFilename();
                //重命名上传后的文件名后缀
                String suffixString = myFileName.substring(myFileName.indexOf("."));  //截取文件名
                //命名规则：随机数
                String fileName1 =System.currentTimeMillis() + suffixString;
                fileName = preName+"-"+fileName1.substring(8,fileName1.length());  //截取随机数的后几位
                String path  = temPath+fileName;
                File localFile = new File(path);
                if (!localFile.getParentFile().exists()) {
                    localFile.mkdirs();
                }
                multipartFile.transferTo(localFile);  //上传
                savePath = URLS.DOMAIN_NAME + temPath+fileName;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new  StateMessage(savePath);
    }

    /**
     * 去到修改界面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/selSlideById")
    public String selSlideById(Integer id,Model model){
        model.addAttribute("slideShow",platformService.selSlideById(id));
        model.addAttribute("imgTypes",platformService.selImgTypeAll());
        return "platform/slideShowUpd";
    }

    /**
     * 保存修改结果
     */
    @RequestMapping("/updateSlide")
    @ResponseBody
    public boolean updateSlide(PlatformSlideshow plat,@RequestParam(value = "attach",required = false) MultipartFile multipartFile,
                               @RequestParam(value = "urlHidden",required = false,defaultValue = "") String urlHidden) {
        try {

            if (multipartFile !=null &&(!multipartFile.isEmpty()) ) {
                delFile(plat.getId());
                plat.setUrl(urlHidden);
            }else if(plat.getUrl() !=null && plat.getUrl() !=""){
                plat.setUrl(plat.getUrl());
            }else{
                delFile(plat.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int res = platformService.updSlide(plat);
        return  res > 0 ? true : false;
    }

    /**
     * 删除图片
     * @param id
     */
    public void delFile(Integer id){
        System.out.println("进入删除图片 "+ id);
        String path1 = platformService.selSlideById(id).getUrl();
        if(path1 !=null && (!path1.isEmpty()) && (! path1.equals(""))){
            String path = URLS.IMAGE_ADDRESS+path1.substring(URLS.SUB_LENGTH,path1.length());   // http://java.86blue.cn/images/
            System.out.println("path : "+path);
            File file = new File(path);

            if(file.exists()){
                if(file.delete()){ // 删除LOGO图片的服务器存储路径
                    platformService.delFile(id);  //更新表
                }
            }
        }

    }

    /**
     * 删除服务器图片
     * @param path
     */
    @RequestMapping("/delServerFile")
    @ResponseBody
    public int delServerFile(@RequestParam(value = "path",required = false,defaultValue = "") String path){
        int n = 1;
        if(path !=null && (!path.isEmpty()) && (! path.equals(""))){
            String path2 = URLS.IMAGE_ADDRESS+path.substring(URLS.SUB_LENGTH,path.length());
            File file = new File(path2);

            if(file.exists()){
                file.delete();// 删除LOGO图片的服务器存储路径
                n = 2;
            }
        }
        return  n;
    }


    /**
     * 上传图片  （修改时）
     * @param multipartFile
     * @return
     */
    @RequestMapping("/updImageUrl")
    @ResponseBody
    public StateMessage updImageUrl(@RequestParam("attach") MultipartFile multipartFile){
        return showImageUrl(URLS.SLIDE_SHOW,multipartFile,"slide");
    }
}
