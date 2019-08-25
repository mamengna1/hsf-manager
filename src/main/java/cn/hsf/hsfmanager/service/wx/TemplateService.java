package cn.hsf.hsfmanager.service.wx;


import cn.hsf.hsfmanager.util.WxSend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TemplateService {

    @Resource
    private  WxService wxService;

    /**
     * 给下单人的
     * @param map
     */
    public   void sendTemplateMessage(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openid")+"\",\n" +
                "           \"template_id\":\"Y3uz6my67nVn0Lj5RrbepL5ACMbzkYFtSCayjwVzWkI\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"您好，您有一笔订单已经支付成功\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("out_trade_no")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+ DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+map.get("money")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\"零钱发起通知\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"感谢您的惠顾！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }

    /**
     * 给推广人发送分红模板信息
     * @param map
     */
    public   void sendPromoterId(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openid")+"\",\n" +
                "           \"template_id\":\"YGq-noIOHzA1JQwHBETy4eB2CrPOA9SJ6vfE__at060\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"亲，您又成功分销出一笔积分订单了\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("out_trade_no")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+map.get("originMoney")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+map.get("score")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\""+ DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"感谢您的惠顾！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }


    /**
     * 给推广人发送有下线信息 模板
     * @param map
     */
    public  void sendToPromoterId(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("promoterId")+"\",\n" +
                "           \"template_id\":\"c58cj1d1Eh52NujEYv1-TrWyqu8SdujkVIFA4Act1BY\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜您，有新会员加入啦1\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("uid")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\""+map.get("ends")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }


    /**
     * 给扫码人发送模板
     * @param map
     */
    public  void sendToScavenger(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("scavenger")+"\",\n" +
                "           \"template_id\":\"c58cj1d1Eh52NujEYv1-TrWyqu8SdujkVIFA4Act1BY\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"您的昵称是："+map.get("nickName")+"  已于："+DateUtil.tranfDate(System.currentTimeMillis())+"成为【中蓝科创】第"+map.get("uid")+"位会员，感谢您的支持。"+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("uid")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"赠送您10积分以及2张抵用券已经到账，详情请进入会员中心查看\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }


    /**
     * 审核成功发送的模板
     * @param map
     */
    public  void sendAuditSuccess(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"xHGdCZ5_3_8-WHktcLRUuRU7sjt7WxsFkRnS8Wv-c9Y\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"亲爱的"+map.get("name")+"  您已经通过好师傅平台的审核"+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("name")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎您的加盟！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }
    /**
     * 审核成功发送的模板
     * @param map
     */
    public  void sendAuditFail(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"nH6k8Kl01S1gTsc15MUpV5e3gNuq6ZvIFggy3hdFCbE\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"抱歉，您的帐号审核失败，请您重新申请。"+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("name")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"您审核失败的原因："+map.get("message")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"您的帐号审核未通过，请您重新提交。\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }

    /**
     * 给推广人发送积分 分红
     * @param map
     */
    public  void sendScore(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"palZlIITZ5lB3n28qL4G_8FfUvZp4x20qFcFRG_DuBg\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\""+map.get("title")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("fenHong")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+map.get("total")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"感谢你的使用，再接再厉哦！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }
}
