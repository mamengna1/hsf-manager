package cn.hsf.hsfmanager.controller.platform;

import cn.hsf.hsfmanager.pojo.platform.PlatformSlideshow;
import cn.hsf.hsfmanager.service.platform.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/slideshow")
@Controller
public class SlideShowController {

    @Autowired
    private PlatformService platformService;

    private static final String filePath = "images/sfsd/";

    @RequestMapping("/goSlide")
    public String goslideshow(){
        return "platform/slideshow";
    }

    @ResponseBody
    @RequestMapping("/insHomePageImg")
    public int insHomePageImg(PlatformSlideshow plat, @RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为：" + fileName);

            // 获取文件的后缀名,比如图片的jpeg,png
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("上传的后缀名为：" + suffixName);

            // 文件上传后的路径
            fileName = UUID.randomUUID() + suffixName;
            System.out.println("转换后的名称:" + fileName);

            File dest = new File(filePath + fileName);

            file.transferTo(dest);
            plat.setUrl("http://java.86blue.cn/" + dest.getPath());
            platformService.insSlideshow(plat);
        }
        return 1;
    }

    @ResponseBody
    @RequestMapping("/selAll")
    public List<PlatformSlideshow> selALl(Integer imgType){
        return platformService.selAll(imgType);
    }
}
