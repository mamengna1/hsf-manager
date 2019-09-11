package cn.hsf.hsfmanager.controller.user;


import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.User;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import cn.hsf.hsfmanager.service.user.UserService;
import cn.hsf.hsfmanager.service.wx.TemplateService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
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
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private UserScoreSourceService userScoreSourceService;
    @Resource
    private TemplateService templateService;

    /**
     * 进入用户信息详情页面
     *
     * @return
     */
    @RequestMapping("/goUserIndex")
    public String goUserIndex(Integer isSub, Integer detailId, Model model) {
        model.addAttribute("isSub", isSub);
        model.addAttribute("detailId", detailId);
        model.addAttribute("scoreType",userScoreSourceService.selScoreType());
        return "user/userAll";
    }


    /**
     * 进入新用户页面
     *
     * @return
     */
    @RequestMapping("/goUserNew")
    public String goUserNew(Model model) {
        model.addAttribute("scoreType",userScoreSourceService.selScoreType());
        return "user/userNew";
    }


    /**
     * 分页显示数据 全部用户
     *
     * @param pageCurrentNo
     * @param isSub
     * @param detailId
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo", required = false, defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam("isSub") Integer isSub, @RequestParam("detailId") Integer detailId,
                        @RequestParam(value = "userName",required = false,defaultValue = "") String userName) {
        Integer a = isSub == null ? 0 : isSub;
        Integer d = detailId == null ? 0 : detailId;
        int total = userService.selUserTotal(a, d,userName);
        Page page = new Page();
        page.setPageSize(10);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<User> orderList = userService.selUserAll(pageCurrentNo, 10, a, d,userName);
        page.setList(orderList);
        return page;
    }

    /**
     * 分页显示数据  新用户
     *
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAllNew")
    @ResponseBody
    public Page userAllNew(@RequestParam(value = "pageCurrentNo", required = false, defaultValue = "1") Integer pageCurrentNo) {
        int total = userService.userTotal();
        Page page = new Page();
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<User> orderList = userService.UserAll(pageCurrentNo, Contents.PAGENO);
        page.setList(orderList);
        return page;
    }

    /**
     * 根据openId  查询信息
     *
     * @param openId
     * @return
     */
    @RequestMapping("/selUserByOpenId")
    @ResponseBody
    public User selUserByOpenId(String openId) {
        return userService.selUserByOpenId(openId);
    }

    /**
     * 根据id 查询信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/selUserById")
    @ResponseBody
    public User selUserById(Integer id) {
        return userService.selUserById(id);
    }

    /**
     * 保存修改结果
     *  score ：奖励积分
     *  sources :积分来源
     *  source ：是否发送模板
     * @return
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(Integer id, Integer userType,@RequestParam(value = "phone",required = false,defaultValue = "") String phone,
                              @RequestParam(value = "score",required = false,defaultValue = "") Integer score,  @RequestParam(value = "sources",required = false,defaultValue = "")Integer sources,
                              @RequestParam(value = "source",required = false,defaultValue = "")Integer source,@RequestParam(value = "note",required = false,defaultValue = "")String note) {
        User user1 = userService.selUserById(id);

        if(score != 0){  //奖励积分不为0时插入积分记录表
            UserScoreSource userScoreSource = new UserScoreSource(user1.getOpenId(),score,sources);
            userScoreSource.setNote(note);
            userScoreSourceService.insScoreSource(userScoreSource);
        }
        if(userType == null || userType == 0){
            userType = 2;
        }
        //更新user表中的总积分剩余积分
        int n =  userService.updateUser(new User(user1.getId(),phone,userType,Double.valueOf(score),Double.valueOf(score)));
        ScoreSourceType scoreSourceType = userScoreSourceService.selById(sources);
        if(source == 1){  //发送模板
            if(score >0){
                Map map = new HashMap();
                map.put("openId",user1.getOpenId());
                map.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
                map.put("title",user1.getNickName()+"您好，恭喜您获得【"+scoreSourceType.getSourceName()+"】积分，本次奖励积分："+score+"分");
                map.put("messageType","积分增加提醒");
                map.put("end","感谢您的使用，如有疑问请联系客服");
                templateService.sendTongYong(map);
            }else if(score==0){

            }else{
                Integer score2 = Math.abs(score);
                Map map = new HashMap();
                map.put("openId",user1.getOpenId());
                map.put("template_id","vIE5CFOjUbodaOaa4nHaz36cAJJWeesRTqTkugKX7nc");
                map.put("title",user1.getNickName()+"您好，由于您违规操作，本次扣除积分："+score2+"分");
                map.put("messageType","积分扣除提醒");
                map.put("end","感谢您的使用，如有疑问请联系客服");
                templateService.sendTongYong(map);
            }
        }

        return n > 0 ? true : false;
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delUserById")
    @ResponseBody
    public boolean delUserById(@RequestParam String ids) {
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        int res = userService.delUser(array);
        return res > 0 ? true : false;
    }
}
