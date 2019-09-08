package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserInformation;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserInformationService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kaituozhe
 */
@Controller
public class UserInformationController {

    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService userDetailService;


    @RequestMapping("/goUserInfo")
    public String goUserInfo() {
        return "user/userInformation";
    }

    @ResponseBody
    @RequestMapping("/selAll")
    public Page selAll(Integer pageCurrentNo, Integer isDelete) {
        Page page = new Page();
        page.setPageSize(50);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(userInformationService.selTotal(isDelete));
        page.setTotalPages(page.getTotalPages());
        List<UserInformation> userInformations = userInformationService.selAll(pageCurrentNo, page.getPageSize(),isDelete);
        for (UserInformation user : userInformations) {
            System.out.println("111"+user);
            user.setName(userDetailService.selUserDetailById(userService.selUserByOpenId(user.getOpenId()).getDetailId()).getName());
        }
        page.setList(userInformations);
        System.out.println(page);
        return page;
    }

    @ResponseBody
    @RequestMapping("/selByOpenId")
    public Map selByOpenId(Integer id) {
        Map map = new HashMap();
        UserInformation userInformation = userInformationService.selById(id);
        User user = userService.selUserByOpenId(userInformation.getOpenId());

        map.put("information", userInformation);
        map.put("user", userDetailService.selUserDetailById(user.getDetailId()));

        System.out.println(map);
        return map;
    }

    @ResponseBody
    @RequestMapping("/delUserInfo")
    public boolean delUserInfo(UserInformation userInformation) {
        return userInformationService.updInfor(userInformation) > 0;
    }
}
