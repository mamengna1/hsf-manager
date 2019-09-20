package cn.hsf.hsfmanager.controller.menu;

import cn.hsf.hsfmanager.pojo.appMenu.AppMenu;
import cn.hsf.hsfmanager.service.appMenu.AppMenuService;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/showMenu")
public class ShowMenuController {


     @Resource
    private AppMenuService appMenuService;
    /**
     * 去到详细类表页面
     * @return
     */
    @RequestMapping("/goMenuIndex")
    public String goMenu(Model model){
        model.addAttribute("menuType", appMenuService.selAll());
        return "menu/menuIndex";
    }

    /**
     * ajax渲染
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/showMenuIndex")
    @ResponseBody
    public Page show(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo ){

        int total = appMenuService.selMenuTotal();
        Page page = new Page();
        page.setPageSize(5);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<AppMenu> orderList = appMenuService.selAllMenu(pageCurrentNo,10);
        page.setList(orderList);
        return page;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delMenuById")
    @ResponseBody
    public boolean delMenuById(Integer id){
        return appMenuService.delMenuById(id) >0 ? true : false ;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/selAppMenuById")
    @ResponseBody
    public AppMenu selAppMenuById(Integer id){
        return appMenuService.selAppMenuById(id);
    }

    /**
     * 修改  "menuName":menuName,"menuTypeId":menuTypeId,"message":m,"key":k
     * @param id
     * @return
     */
    @RequestMapping("/updateAppMenu")
    @ResponseBody
    public boolean updateAppMenu(@RequestParam("id") Integer id,@RequestParam("menuName") String menuName,@RequestParam("menuTypeId") Integer menuTypeId,@RequestParam("message") String message,@RequestParam("key") String key){
        AppMenu appMenu = new AppMenu(id,menuName,menuTypeId,message,key);
        return appMenuService.updateAppMenuById(appMenu) > 0 ? true : false;
    }

}
