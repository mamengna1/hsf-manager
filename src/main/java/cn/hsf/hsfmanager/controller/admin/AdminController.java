package cn.hsf.hsfmanager.controller.admin;

import cn.hsf.hsfmanager.pojo.Admin;
import cn.hsf.hsfmanager.pojo.AdminType;
import cn.hsf.hsfmanager.service.admin.AdminService;
import cn.hsf.hsfmanager.service.admin.AdminTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/adminController")
public class AdminController {


    @Resource
    private AdminService adminService;
    @Resource
    private AdminTypeService adminTypeService;

    /**
     * 进入所有管理员的信息类表
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/goAdminList")
    public String goAdminList(HttpServletRequest request, Model model){
       Admin admin = (Admin) request.getSession().getAttribute("admin");
        List<Admin> adminList = adminService.selListAdmin(new Admin(admin.getLevel()));
        model.addAttribute("adminList",adminList);
        List<AdminType> adminTypes = adminTypeService.selAdminTypeList();
        model.addAttribute("adminTypes",adminTypes);
        return "admin/AdminIndex";
    }

    /**
     * 进入管理员新增页面
     * @return
     */
    @RequestMapping("/goCreateAdmin")
    public String goCreateAdmin(Model model){
        List<AdminType> adminTypes = adminTypeService.selAdminTypeList();
        model.addAttribute("adminTypes",adminTypes);
        return "admin/createAdmin";
    }

    /**
     * 新增保存
     * @return
     */
    @RequestMapping("/saveAdmin")
    @ResponseBody
    public boolean saveAdmin(String account,String password,Integer level,Integer typeId){
        System.out.println("-----------我进入了新增---------------");
        Admin admin = new Admin(account,password,level,typeId);
        System.out.println(admin);
        int res = adminService.saveAdmin(admin);
        return res> 0 ?true : false;
    }

    /**
     * 数据渲染
     * @param id
     * @return
     */
    @RequestMapping("/selAdmin")
    @ResponseBody
    public Admin selAdmin(Integer id){

        Admin admin = new Admin();
        admin.setId(id);
       return adminService.selAdmin(admin);
    }

    /**
     * 修改 "id":id,"account":account,"level":level,"typeId":typeId
     * @return
     */
    @RequestMapping("/updAdmin")
    @ResponseBody
    public boolean updAdmin(Integer id,String account,Integer level,Integer typeId){
        System.out.println("-----------我进入了修改---------------");
        Admin admin = new Admin(id,account,level,typeId);
        System.out.println(admin);
        return adminService.updAdmin(admin) > 0 ? true : false;
    }

    @RequestMapping("/delAdminById")
    @ResponseBody
    public boolean delAdminById(Integer id){
        return adminService.delAdmin(id) > 0 ? true : false;
    }
}
