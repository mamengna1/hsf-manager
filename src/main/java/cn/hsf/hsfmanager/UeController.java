package cn.hsf.hsfmanager;

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
    /**
     * 进入新增编辑框页面
     * @return
     */
    @RequestMapping("/goGraphic")
    public String goGraphic(){
        return "platform/graphic_edit";
    }

    /**
     * 保存新增编辑框内容
     * @param title
     * @param ueText
     * @return
     */
    @RequestMapping("/Saveue")
    public String saveUe(@RequestParam(value = "title",required = false) String title, @RequestParam(value = "subtitle",required = false) String subtitle,
                         @RequestParam(value = "neirong",required = false) String ueText,
                         @RequestParam(value = "attach", required = false) MultipartFile multipartFile,
                         @RequestParam(value = "viewCount" ,required = false,defaultValue = "0") Integer viewCount) {
        String fileName = null;
        Graphic graphic = new Graphic(title,subtitle,ueText,viewCount);
        try {
            System.out.println("multipartFile : "+multipartFile);
           if (! multipartFile.isEmpty() ) {
               //取得当前上传文件的文件名称
               String myFileName = multipartFile.getOriginalFilename();
               //重命名上传后的文件名后缀
               String suffixString = myFileName.substring(myFileName.indexOf("."));  //截取文件名
               //命名规则：用户id+当前时间+随机数
               fileName = "uploads" + "-" + System.currentTimeMillis() + "-" + ((int) (Math.random() * 10000000)) + suffixString;
               String path  = URLS.GRAPHIC+fileName;
               System.out.println("上传的路径 : " + path);
               File localFile = new File(path);
               if (!localFile.getParentFile().exists()) {
                   localFile.mkdirs();
               }
               multipartFile.transferTo(localFile);  //上传
               String savePath = URLS.DOMAIN_NAME +URLS.GRAPHIC+fileName;
                graphic.setImageUrl(savePath);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
        int res = graphicService.insGraphic(graphic);
        return  "redirect:/goGraphicAllView";

    }


    /**
     * 进入查询全部图文列表页面
     * @return
     */
    @RequestMapping("/goGraphicAllView")
    public String goGraphicAllView(){
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
                             @RequestParam(value = "titles",required = false,defaultValue = "") String title){
        System.out.println("====================我进入了图文管理界面===================");
        int total = graphicService.selAllListCount(title);
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<Graphic> graphicList =graphicService.selAllGraphicList(pageCurrentNo, Contents.PAGENO,title);
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
        return "platform/graphic_upd";
    }
   /* *//**
     * 显示编辑信息
     * @return
     *//*
    @RequestMapping("/showUe")
    public String showUe(Model model){
        System.out.println("进入");
        List<Advert> adverts = advertService.selAllAdvert();
        System.out.println(adverts);
        System.out.println(adverts.size());
        model.addAttribute("listAdverts",adverts);
        return "ueditor/showUe";
    }

    *//**
     * 携带数据进入修改内容
     * @param id
     * @param model
     * @return
     *//*
    @RequestMapping("/goUpdateUe")
    public String goUpdateUe(@RequestParam("id") Integer id, Model model){
        Advert advert = advertService.selAdvertById(id);

        model.addAttribute("advert",advert);
        model.addAttribute("category1",advertCategoryService.selByParent(null));
        model.addAttribute("category2",advertCategoryService.selByParent(advert.getCategory1()));
        model.addAttribute("category3",advertCategoryService.selByParent(advert.getCategory2()));
        return "ueditor/updateUe";
    }

    *//**
     * 保存修改结果
     *//*
    @RequestMapping("/updateUe")
    public String updateUe(@RequestParam(value = "title",required = false) String title, @RequestParam(value = "subtitle",required = false) String subtitle,
                           @RequestParam(value = "neirong",required = false) String ueText, @RequestParam(value = "ids",required = false)Integer id,
                           @RequestParam( value = "queryCategoryLevel1" ,required = false,defaultValue = "-1") Integer queryCategoryLevel1, @RequestParam(value = "queryCategoryLevel2",required = false,defaultValue = "-1") Integer queryCategoryLevel2,
                           @RequestParam(value = "queryCategoryLevel3",required = false,defaultValue = "-1") Integer queryCategoryLevel3, @RequestParam(value = "attach", required = false) MultipartFile multipartFile,
                           @RequestParam(value = "viewCount" ,required = false,defaultValue = "0") Integer viewCount, @RequestParam(value = "logoPicPath",required = false) String logoPicPath) {
        String fileName = null;
        Advert advert = new Advert(Long.valueOf(id),title,subtitle,ueText,queryCategoryLevel1,queryCategoryLevel2,queryCategoryLevel3,viewCount);
        try {
            System.out.println("multipartFile : "+multipartFile);
            if (! multipartFile.isEmpty() ) {
                delFile(id);
                //取得当前上传文件的文件名称
                String myFileName = multipartFile.getOriginalFilename();
                //重命名上传后的文件名后缀
                String suffixString = myFileName.substring(myFileName.indexOf("."));  //截取文件名
                //命名规则：用户id+当前时间+随机数
                fileName = "uploads" + "-" + System.currentTimeMillis() + "-" + ((int) (Math.random() * 10000000)) + suffixString;
                String path  = "/img/"+fileName;
                System.out.println("上传的路径 : " + path);
                File localFile = new File(path);
                if (!localFile.getParentFile().exists()) {
                    localFile.mkdirs();
                }
                multipartFile.transferTo(localFile);  //上传
                String savePath = "http://java.86blue.cn/img/"+fileName;
                advert.setImageUrl(savePath);
            }else if(logoPicPath !=null && logoPicPath !=""){
                advert.setImageUrl(logoPicPath);
            }else{
                delFile(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int res = advertService.updateAdvert(advert);
        return  "redirect:/showUe";
    }

    *//**
     * 删除图片
     * @param id
     *//*
    public void delFile(Integer id){
        System.out.println("进入删除图片 "+ id);
        Map<String,String> map = new HashMap<String, String>();
        String path1 = advertService.selAdvertById(id).getImageUrl();
        if(path1 !=null){
            String path = "D:\\software\\Tomcat\\tomcat\\webapps\\img\\"+path1.substring(26);
            System.out.println("path : "+path);
            File file = new File(path);

            if(file.exists()){
                System.out.println(111);
                if(file.delete()){ // 删除LOGO图片的服务器存储路径
                    int n = advertService.delFile(id);  //更新表
                    System.out.println("n : "+ n);

                }
            }
        }

    }


    *//**
     * 三级联查的回显
     * @param id
     * @return
     *//*
    @RequestMapping("/bingCategory")
    @ResponseBody
    public Advert bingCategory(Integer id){
      return  advertService.selAdvertById(id);
    }

    *//**
     * 删除
     * @param id
     * @return
     *//*
    @RequestMapping("/delAdvertById")
    @ResponseBody
    public boolean delOrderById(@RequestParam("id") Integer id){
        return advertService.delOrderById(Long.valueOf(id)) > 0 ? true : false;
    }*/
}
