package cn.hsf.hsfmanager.controller.user;

import cn.hsf.hsfmanager.pojo.Admin;
import cn.hsf.hsfmanager.pojo.App;
import cn.hsf.hsfmanager.pojo.ResultData;
import cn.hsf.hsfmanager.pojo.user.CashBack;
import cn.hsf.hsfmanager.service.app.AppService;
import cn.hsf.hsfmanager.service.user.BackStatusService;
import cn.hsf.hsfmanager.service.user.CashBackService;
import cn.hsf.hsfmanager.service.wx.CertHttpUtil;
import cn.hsf.hsfmanager.util.Contents;
import cn.hsf.hsfmanager.util.Page;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.hsf.hsfmanager.util.URLS;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/commission")
public class CashBackController {

    @Resource
    private CashBackService cashBackService;
    @Resource
    private BackStatusService backStatusService;
    @Resource
    private AppService appService;
    @Resource
    CertHttpUtil certHttpUtil;
    /**
     * 进入提现页面
     * @return
     */
    @RequestMapping("/goCashBack")
    public String goCashBack(){
        return "user/cashBack";
    }

    /**
     * 分页
     * @param pageCurrentNo
     * @return
     */
    @RequestMapping("/show")
    @ResponseBody
    public Page show(@RequestParam(value = "pageCurrentNo",required = false,defaultValue = "1") Integer pageCurrentNo ,
                     @RequestParam(value = "backStatusId",required = false,defaultValue = "-1") Integer backStatusId,
                     @RequestParam(value = "openId",required = false,defaultValue = "") String openId,
                     @RequestParam(value = "userName",required = false,defaultValue = "") String userName, HttpServletRequest request){
        String o;
        if(openId == "-1" || openId.equals("-1")|| openId == null){
            o = null;
        }else{
            o = openId;
        }
        int total = cashBackService.selTotalCount(backStatusId,o,userName);
        Page page = new Page();
        page.setPageSize(10);
        page.setPageCurrentNo(pageCurrentNo);
        page.setTotalCount(total);
        page.setTotalPages(page.getTotalPages());
        List<CashBack> cashBacks = cashBackService.selAll(pageCurrentNo, Contents.PAGENO,backStatusId,o,userName);
        for (int i = 0; i <cashBacks.size() ; i++) {
            String a =(cashBacks.get(i).getUserName() == null || "".equals( cashBacks.get(i).getUserName())) ? "无名氏" : cashBacks.get(i).getUserName();
            String b = (cashBacks.get(i).getUser().getNickName() == null || "".equals(cashBacks.get(i).getUser().getNickName())) ? "无名氏" : cashBacks.get(i).getUser().getNickName();
            if(a == "无名氏" || (!b.equals(a))){
                CashBack cashBack = new CashBack();
                cashBack.setId( cashBacks.get(i).getId());
                cashBack.setUserName(b);
                cashBackService.updateCashBack(cashBack);
            }
        }

        page.setList(cashBacks);
       Admin admin = (Admin) request.getSession().getAttribute("admin");
       page.setLevel(admin.getLevel());
        return page;
    }


    /**
     *根据id进行修改数据的渲染
     * @param id
     * @return
     */
    @RequestMapping("/getCommissionById")
    @ResponseBody
    public Map getCommissionById(@RequestParam("id") Integer id){
        Map map = new HashMap();
        map.put("commission",cashBackService.selAllById(id));
        map.put("listCommission",  backStatusService.selAllStatus());
        return map;
    }


    /**
     * 保存修改结果
     * @return
     */
    @RequestMapping("/saveCommission")
    @ResponseBody
    public boolean saveCashBack(@RequestParam("backStatusId") Integer backStatusId, @RequestParam("id") Integer id){
        int count = cashBackService.updateCashBack(new CashBack(id,backStatusId));
        return count >0 ? true : false;
    }

    /**
     * 确认支付
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping("/payCommission")
    @ResponseBody
    public ResultData payCashBack(String openId, Integer id, String money,String suiMoney) throws Exception {
        double moneys = Double.valueOf(money);
        double suiMoney1 = Double.valueOf(suiMoney);
        Map map = createMap(openId,moneys*100);
        String xml = WXPayUtil.mapToXml(map);
      //  System.out.println("发送前的xml为 ："+ xml);
        // ,向微信发送请求转账请求
/*        App app = appService.selApp();
        ResultData resultData = certHttpUtil.postData(URLS.COMPANY_PAY_URL, xml, app.getMchId(), URLS.CERT_PATH, id,suiMoney1,moneys);*/
        ResultData resultData = certHttpUtil.postData(URLS.COMPANY_PAY_URL, xml, URLS.MCHID, URLS.CERT_PATH2, id,suiMoney1,moneys);
        return resultData;
    }

    /**
     * 确认支付参数接口
     * @param openId
     * @param money
     * @return
     * @throws Exception
     */
    public Map createMap(String openId,double money) throws Exception {
        String m = String.valueOf((int) money);
        Map params = new HashMap<>();
       /* App app = appService.selApp();
        params.put("mch_appid", app.getAppId());
        params.put("mchid", app.getMchId());*/
        params.put("mch_appid", URLS.APPID);
        params.put("mchid", URLS.MCHID);
        params.put("nonce_str", WXPayUtil.generateNonceStr());
        params.put("partner_trade_no", System.currentTimeMillis() + "");
        params.put("openid", openId);
        params.put("check_name", "NO_CHECK");  // 不验证真实姓名
        params.put("amount",m);
        params.put("desc", "企业付款到零钱");
        params.put("spbill_create_ip", "112.124.200.41");
        params.put("re_user_name", "kolo");// 收款用户姓名
   /*     params.put("sign", WXPayUtil.generateSignature(params, app.getKey()));*/
        params.put("sign", WXPayUtil.generateSignature(params, URLS.KEY));
        return params;
    }

    @RequestMapping("/delCashBack")
    @ResponseBody
    public boolean delCashBack(String ids){
        String str[] = ids.split(",");
        Integer array[] = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        int res = cashBackService.delCashBack(array);
        return res > 0 ? true : false;
    }
}
