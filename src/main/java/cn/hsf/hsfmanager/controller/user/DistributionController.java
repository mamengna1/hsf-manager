package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.user.Distribution;
import cn.hsf.hsfmanager.service.user.DistributionService;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 派单详情
 */
@Controller
@RequestMapping("/distribution")
public class DistributionController {

    @Resource
    private DistributionService distributionService;
    /**
     * 进入派单页面
     * @return
     */
    @RequestMapping("/goDistribution")
    public String goDistribution(){
        return "user/distributionAll";
    }


    /**
     * 分页显示派单详情
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/userAll")
    @ResponseBody
    public Page userAll(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo){
        int total = distributionService.selDistributionTotal();
        Page page = new Page();
        page.setPageSize( Contents.PAGENO);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<Distribution> userDetails =distributionService.selDistributionAll(pageCurrentNo, Contents.PAGENO);
        page.setList(userDetails);
        return page;
    }
}
