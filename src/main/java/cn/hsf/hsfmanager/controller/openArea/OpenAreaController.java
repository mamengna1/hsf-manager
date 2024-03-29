package cn.hsf.hsfmanager.controller.openArea;

import cn.hsf.hsfmanager.pojo.user.SerAddress;
import cn.hsf.hsfmanager.service.user.SerAddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/openAreaController")
public class OpenAreaController {
    @Resource
    private SerAddressService serAddressService;

    /**
     * 去到开放地区界面
     * @return
     */
    @RequestMapping("/goOpenAreaIndex")
    public String goOpenAreaIndex(Model model){
        model.addAttribute("category1",serAddressService.selByParent(null));
        return "openArea/openAreaIndex";
    }

    /**
     * 显示级别分类
     * @return
     */
    @RequestMapping("/showOpenAreas")
    @ResponseBody
    public  List<SerAddress> showOpenAreas(@RequestParam(value = "parentId",required = false) String parentId ){

        List<SerAddress> categoryLevel = null;
        if(parentId ==null || parentId =="" || parentId == "-1" || parentId.equals("-1")){
            categoryLevel     = serAddressService.selByParent(null);
        }else {
            categoryLevel     = serAddressService.selByParent(Integer.valueOf(parentId));
        }
        return categoryLevel;
    }

    //新增省份
    @RequestMapping("/saveProvinceName")
    @ResponseBody
    public boolean saveProvinceName(String provinceName){
        return serAddressService.insProvinceName(provinceName) >0 ? true : false;
    }


    //新增子类
    @RequestMapping("/insCityModal")
    @ResponseBody
    public boolean insCityModal(String areaName,Integer parentId){
        return serAddressService.insSerAddress(new SerAddress(areaName,parentId)) >0 ? true : false;
    }


    //删除三级单独
    @RequestMapping("/delCity")
    @ResponseBody
    public boolean delCity(Integer id){
        return serAddressService.deSerAddress(id) > 0 ? true : false;
    }

    //删除三级
    @RequestMapping("/delCityFu")
    @ResponseBody
    public boolean delCityFu(Integer parentId){
        serAddressService.deSerAddressByParentId(parentId);
        return serAddressService.deSerAddress(parentId) > 0 ? true : false;  //此时传入的是id
    }

    @RequestMapping("/selById")
    @ResponseBody
    public SerAddress selById(Integer id){
        return serAddressService.selById(id);
    }

    @RequestMapping("/updSerAddress")
    @ResponseBody
    public boolean updSerAddress(Integer id, String addName, @RequestParam(value = "parentId",required = false,defaultValue = "") Integer parentId){
        return serAddressService.updSerAddress(new SerAddress(id,addName,parentId)) > 0 ? true : false;
    }

    //去到省份管理界面
    @RequestMapping("/goProvincesManager")
    public String goProvincesManager(Model model){
        List<SerAddress> serAddresses = serAddressService.selByParent(null);
        model.addAttribute("serAddresses",serAddresses);
        return "openArea/provincesManager";
    }

    //删除最顶级的父类
    @RequestMapping("/delCityLeavl1")
    @ResponseBody
    public boolean delCityLeavl1(Integer id){
        List<SerAddress> serAddresses = serAddressService.selByParent(id);  //查询出是否有二级
        if(serAddresses.size() > 0 ){   //存在二级
            //判断是否存在3级
            for (int i = 0; i <serAddresses.size() ; i++) {
                Integer level2Id = serAddresses.get(i).getId();
                List<SerAddress> serAddresses3 = serAddressService.selByParent(level2Id);  //查询出三级
                if(serAddresses3.size() > 0 ){  //存在3级
                    serAddressService.deSerAddressByParentId(serAddresses.get(i).getId());   //删除3级
                }
                serAddressService.deSerAddressByParentId(id);   //删除2级
            }
        }
        //最后删除1级
        Integer n =  serAddressService.deSerAddress(id);
        return n > 0 ? true : false;
    }
}
