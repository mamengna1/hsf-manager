package cn.hsf.hsfmanager.controller.user;


import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 进入用户信息详情页面
     * @return
     */
    @RequestMapping("/goUserIndex")
    public String goUserIndex(){
        return "user/userAll";
    }

    /**
     * 进入取消关注页面
     * @return
     */
    @RequestMapping("/goUserCancle")
    public String goUserCancle(){
        return "user/userCancle";
    }


    /**
     * 进入关注页面
     * @return
     */
    @RequestMapping("/goUserFouce")
    public String goUserFouce(){
        return "user/userFouce";
    }

    /**
     * 分页显示数据
     * @param pageCurrentNo
     * @param isSub
     * @param detailId
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam("isSub") Integer isSub, @RequestParam("detailId") Integer detailId ){
        Integer a = isSub == null ? 0 : isSub;
        Integer d = detailId == null ? 0 : detailId;
        int total = userService.selUserTotal(a,d);
        Page page = new Page();
        page.setPageSize(10);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<User> orderList = userService.selUserAll(pageCurrentNo,10,a,d);
        page.setList(orderList);
        return page;
    }

    /**
     * 根据openId  查询信息
     * @param openId
     * @return
     */
    @RequestMapping("/selUserByOpenId")
    @ResponseBody
    public User selUserByOpenId(String openId){
       return userService.selUserByOpenId(openId);
    }

    /**
     * 根据id 查询信息
     * @param id
     * @return
     */
    @RequestMapping("/selUserById")
    @ResponseBody
    public User selUserById(Integer id){
        return userService.selUserById(id);
    }

    /**
     * 保存修改结果
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(Integer id,Integer userType,Double balanceMoney,Double totalScore,Double balanceScore){
        System.out.println("userType : "+userType);
        User user = new User(id,userType,balanceMoney,totalScore,balanceScore);
        System.out.println(user);
        int n = userService.updateUser(user);
        return n>0 ? true : false;
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delUserById")
    @ResponseBody
    public boolean delUserById(Integer id){
       return userService.delUser(id) > 0 ? true : false;
    }
}
