package cn.hsf.hsfmanager.controller.menu;

import cn.hsf.hsfmanager.pojo.appMenu.AppMenu;
import cn.hsf.hsfmanager.pojo.menu.*;
import cn.hsf.hsfmanager.service.app.AppService;
import cn.hsf.hsfmanager.service.appMenu.AppMenuService;
import cn.hsf.hsfmanager.service.wx.WxService;
import cn.hsf.hsfmanager.util.WxSend;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class CreateMenuController {

    @Autowired
    private AppMenuService appMenuService;
    @Resource
    private AppService appService;
    @Resource
    private WxService wxService;


    /**
     * 去到菜单界面
     * @param model
     * @return
     */
    @RequestMapping("/goCreateMenu")
    public String goCreateMenu(Model model){
        model.addAttribute("menuType", appMenuService.selAll());
        return "menu/createMenu";
    }

    /**
     * 显示菜单级别分类
     * @return
     */
    @RequestMapping("/showLevel")
    @ResponseBody
    public Object showLevel1(@RequestParam(value = "parentId",required = false) Integer parentId ){

        List<AppMenu> menus = null;
        if(parentId ==null || parentId ==0){
            menus     = appMenuService.selAllByParent(0);
        }else {
            menus     = appMenuService.selAllByParent(parentId);
        }
        return menus;
    }


    @RequestMapping("/showMenu")
    @ResponseBody
    public Map showMenu(@RequestParam(value = "parentId",required = false) Integer parentId ){
        Map map = new HashMap();
        List<AppMenu> menus = null;
        if(parentId ==null || parentId ==0){
            menus     = appMenuService.selAllByParent(0);
        }else {
            menus     = appMenuService.selAllByParent(parentId);
        }
        List<String> selKey = appMenuService.selAllKey();
        map.put("menus",menus);
        map.put("selKey",selKey);
        return map;
    }


    /**
     * 保存结果
     * @param menuName
     * @param menuTypeId
     * @param parentMenuId
     * @param message
     * @param key
     * @return
     */
    @RequestMapping("/saveMenu")
    @ResponseBody
    public boolean saveMenu(String menuName,Integer menuTypeId,Integer parentMenuId,String message,String key){
        Integer parentId;
        if(parentMenuId ==-1 ){
            parentId = 0;
        }else{
            parentId = parentMenuId;
        }
        AppMenu appMenu = new AppMenu(menuName,menuTypeId,parentId,message,key);
        System.out.println("appMenu : "+appMenu);
        int n = appMenuService.insertAppMenu(appMenu);
        boolean b = false;
        if(n >0){
            b = create();
        }
       return b;
    }
    /**
     * 创建菜单
     * @return
     */
    @RequestMapping("/create")
    public boolean create() {
        List<AppMenu> menus = appMenuService.selAllByParent(0);
        // 创建菜单对象
        Button btn = new Button();
        SubButton sb = null;
        for (AppMenu menu : menus) {
            List<AppMenu> subMenus = appMenuService.selAllByParent(menu.getId());
            if (subMenus != null && subMenus.size() > 0) {
                sb = new SubButton(menu.getMenuName());
                for (AppMenu subMenu : subMenus) {
                    sb.getSub_button().add(createMenu(subMenu));
                }
                btn.getButton().add(sb);
            } else {
                btn.getButton().add(createMenu(menu));
            }
        }
        // 转为JSON
        JSONObject jsonObject = JSONObject.fromObject(btn);

        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN".replace("ACCESS_TOKEN", wxService.getAccessToken());
       System.out.println(WxSend.post(url, jsonObject.toString()));
        return true;
    }

    private AbstractButton createMenu(AppMenu menu) {
        // 没有子菜单
        switch (menu.getMenuTypeId()) {
            // 关键字
            case 1:
                return new ClickButton(menu.getMenuName(), menu.getKey());
            // 链接
            case 2:
                return new ViewButton(menu.getMenuName(), menu.getMessage());
            case 3:
                //小程序
                return new ProgramButton(menu.getMenuName(), menu.getMenuName(), appService.selApp().getAppId(), "xx");
            default:
                break;
        }
        return null;
    }
}
