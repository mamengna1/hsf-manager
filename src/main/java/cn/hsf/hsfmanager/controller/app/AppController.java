package cn.hsf.hsfmanager.controller.app;

import cn.hsf.hsfmanager.pojo.App;
import cn.hsf.hsfmanager.service.app.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/appController")
public class AppController {

    @Resource
    private AppService appService;


    @RequestMapping("/goAppIndex")
    public String goAppIndex(Model model){
        model.addAttribute("app",appService.selApp());
        return "app/appIndex";
    }

    @RequestMapping("/saveApp")
    @ResponseBody
    public boolean saveApp(Integer id,String appId,String mchId,String appSecret,String token,String key){
       return appService.updApp(new App(id,appId,mchId,appSecret,token,key)) > 0 ? true : false ;
    }

}
