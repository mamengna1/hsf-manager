package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.pojo.user.UserDetail;
import cn.hsf.hsfmanager.pojo.user.UserInformation;
import cn.hsf.hsfmanager.service.user.UserDetailService;
import cn.hsf.hsfmanager.service.user.UserInformationService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(userInformationService.selTotal(isDelete));
        page.setTotalPages(page.getTotalPages());
        List<UserInformation> userInformations = userInformationService.selAll(pageCurrentNo, page.getPageSize(),isDelete);
        for (UserInformation user : userInformations) {
            String userName = userDetailService.selUserDetailById(userService.selUserByOpenId(user.getOpenId()).getDetailId()).getName() == null ? "未知" :userDetailService.selUserDetailById(userService.selUserByOpenId(user.getOpenId()).getDetailId()).getName();
            user.setName(userName);
        }
        page.setList(userInformations);
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

        return map;
    }

    /**
     * 删除
     * @param userInformation
     * @return
     */
    @ResponseBody
    @RequestMapping("/delUserInfo")
    public boolean delUserInfo(UserInformation userInformation) {
        //此处发送模板
        return userInformationService.updInfor(userInformation) > 0;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/userInformation/batchDel")
    @ResponseBody
    public boolean batchDel(@RequestParam String ids,String delCause){
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for(int i=0;i<str.length;i++){
            array[i]=Integer.parseInt(str[i]);
        }
        int res = userInformationService.delUserInfoByIds(array,delCause);
        return  res >0 ? true :false;
    }
}
