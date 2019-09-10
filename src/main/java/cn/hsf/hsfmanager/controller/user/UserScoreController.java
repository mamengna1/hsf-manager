package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.ScoreSourceType;
import cn.hsf.hsfmanager.pojo.user.UserScoreSource;
import cn.hsf.hsfmanager.service.user.UserScoreSourceService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userScore")
public class UserScoreController {

    @Resource
    private UserScoreSourceService userScoreSourceService;

    /**
     * 进入积分信息列表
     * @return
     */
    @RequestMapping("/goUserScoreAll")
    public String goUserReleaseAll(@RequestParam(value = "openId",required = false,defaultValue = "-1") String openId ,Model model){
        List<ScoreSourceType> scoreSourceTypes = userScoreSourceService.selScoreType();
        model.addAttribute("scoreSourceTypes",scoreSourceTypes);
        model.addAttribute("openId",openId);
        return "user/userScore";
    }


    /**
     * 分页显示数据  全部积分来源
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo,
                        @RequestParam(value = "openId",required = false,defaultValue = "") String openId,
                        @RequestParam(value = "scoreSourceId",required = false,defaultValue = "") Integer scoreSourceId,
                        @RequestParam(value = "userName",required = false,defaultValue = "") String userName){
        System.out.println("================进入积分界面===================");
        String openid;
        if(openId == "-1" || openId.equals("-1") || openId == null){
            openid = null;
        }else{
            openid = openId;
        }
        int total = userScoreSourceService.scoreTotal(openid,scoreSourceId,userName);
        Page page = new Page();
        page.setPageSize(Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<UserScoreSource> userScoreSources =userScoreSourceService.selAllScore(pageCurrentNo,Contents.PAGENO,openid,scoreSourceId,userName);
        for (int i = 0; i <userScoreSources.size() ; i++) {
            UserScoreSource userScore = userScoreSourceService.selScoreById(userScoreSources.get(i).getId());
            if(userScoreSources.get(i).getUserName() == null || (!userScoreSources.get(i).getUser().getNickName().equals(userScore.getUserName()))){
                userScoreSources.get(i).setUserName(userScoreSources.get(i).getUser().getNickName());
                UserScoreSource user1 = new UserScoreSource();
                user1.setId( userScoreSources.get(i).getId());
                user1.setUserName(userScoreSources.get(i).getUserName());
                userScoreSourceService.updScore(user1);
            }
        }
        page.setList(userScoreSources);
        return page;
    }

    /**
     * 修改数据的渲染
     * @param id
     * @return
     */
    @RequestMapping("/selScoreById")
    @ResponseBody
    public UserScoreSource selScoreById(Integer id){
        return userScoreSourceService.selScoreById(id);
    }

    /**
     * 保存修改结果
     * @param id
     * @param score
     * @param scoreSourceId
     * @return
     */
    @RequestMapping("/updScore")
    @ResponseBody
    public boolean updScore(Integer id,Integer score,Integer scoreSourceId){
        UserScoreSource userScoreSource = new UserScoreSource(id,Double.valueOf(score),scoreSourceId);
        return  userScoreSourceService.updScore(userScoreSource) > 0 ? true : false;
    }

    @RequestMapping("/delScoreById")
    @ResponseBody
    public boolean delScoreById(Integer id){
        return  userScoreSourceService.delScoreById(id) > 0 ? true : false;
    }
}
