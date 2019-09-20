package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.pojo.user.UserOrder;
import cn.hsf.hsfmanager.pojo.user.UserSkills;
import cn.hsf.hsfmanager.service.user.UserOrderService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userOrder")
public class UserOrderController {

    @Resource
    private UserOrderService userOrderService;
    @Resource
    private UserService userService;

    /**
     * 进入评论界面
     * @return
     */
    @RequestMapping("/goUserOrder")
    public String goUserOrder(){
        return "user/userOrderAll";
    }

    /**
     * 分页显示数据  评论信息
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "commentTypeId",required = false,defaultValue = "") Integer commentTypeId){
        int total = userOrderService.selUserOrderTotal(commentTypeId);
        Page page = new Page();
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserOrder> userOrders =userOrderService.selAllUserOrder(pageCurrentNo,Contents.PAGENO,commentTypeId);
        page.setList(userOrders);
        return page;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delUserOrder")
    @ResponseBody
    public boolean delUserOrder(Integer id){
      return   userOrderService.delUserOrderById(id) > 0 ? true : false;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping("/selUserOrderById")
    @ResponseBody
    public Map selUserOrderById(Integer id){
        Map map = new HashMap();
        UserOrder userOrder = userOrderService.selById(id);
        User user = userService.selUserById(userOrder.getUserId());
        map.put("userOrder",userOrder);
        map.put("user",user);
        return map;
    }

    @RequestMapping("/updUserOrder")
    @ResponseBody
    public boolean updUserOrder(Integer id,String comments,Integer starLevel){
        UserOrder userOrder = new UserOrder(id,comments,starLevel);
        return  userOrderService.updUserOrder(userOrder) > 0 ? true : false;
    }
}
