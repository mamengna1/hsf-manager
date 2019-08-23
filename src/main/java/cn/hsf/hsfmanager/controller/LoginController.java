package cn.hsf.hsfmanager.controller;

import cn.hsf.hsfmanager.pojo.Admin;
import cn.hsf.hsfmanager.service.admin.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Resource
    private AdminService adminService;


    /**
     * 登录验证
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/checkAdmin")
    public String selByAdmin(String userName, String password, HttpServletRequest request){
        Admin admin = new Admin(userName,password);

        Admin checkLogin = adminService.checkLogin(admin);
        if(checkLogin !=null ){
            request.getSession().setAttribute("admin",admin);
            return "index";
        }else{
            return "login";
        }
    }

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping("/login")
    public String goAdmin(){
        return "login";
    }


    /**
     * 登录成功后 进入主页面index
     * @return
     */
    @RequestMapping("/index")
    public String goIndex(){
        return "index";
    }

    /**
     * 用户注销
     */
    @RequestMapping("/loginOut")
    public String loginOut(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "login";
    }

    @RequestMapping("/goHome")
    public String goHome(){
        return "home";
    }
}
