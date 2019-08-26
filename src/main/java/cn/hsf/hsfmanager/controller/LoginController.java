package cn.hsf.hsfmanager.controller;

import cn.hsf.hsfmanager.pojo.Admin;
import cn.hsf.hsfmanager.service.admin.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
    public String selByAdmin(String userName, String password, HttpServletRequest request, HttpServletResponse response){
        Admin admin = new Admin(userName,password);
        Admin checkLogin = adminService.checkLogin(admin);
        if(checkLogin !=null ){
            request.getSession().setAttribute("admin",checkLogin);
            return "redirect:/index";
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
    public String goIndex(HttpServletRequest request, Model model){
       Admin admin = (Admin) request.getSession().getAttribute("admin");
       model.addAttribute("admins",admin);
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

    @RequestMapping("/updPassword")
    @ResponseBody
    public Map updPassword(String passwordOld,String passwordNew,HttpServletRequest request){
        Map map = new HashMap();
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if(admin.getPassword().equals(passwordOld)){
            Admin admin1 = new Admin();
            admin1.setId(admin.getId());
            admin1.setPassword(passwordNew);
            int i = adminService.updAdmin(admin1);
            if(i > 0 ){
                map.put("yes","修改成功,请重新登录");
                request.getSession().removeAttribute("admin");
            }else{
                map.put("error","未知异常，修改失败！");
            }
        }else {
            map.put("errorPwd","原密码错误,修改失败！");
        }
        return  map;
    }
}
