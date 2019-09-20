package cn.hsf.hsfmanager.service.wx;


import cn.hsf.hsfmanager.util.WxSend;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    @Resource
    private  WxService wxService;

    /**
     * 审核成功发送的模板
     * @param map
     */
    public  void sendAuditSuccess(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"SS824RmViicEZJBdbWDbFPpKdEaDoIPneO3btpHJ-NQ\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
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
     * 审核失败发送的模板
     *  亲爱的战友，你提交的金句：“失败是陈功之母”没有通过审核！
     * 审核结果：不通过
     * 审核时间：2017-11-01 12:12:12
     * 失败原因：文字错误
     * 请修改后重新提交申请。   MdZiGpJQZgPn28z-e1GplNbiN8_Z0FFsiW3-7jGE7O8
     * @param map
     */
    public  void sendAuditFail(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"MdZiGpJQZgPn28z-e1GplNbiN8_Z0FFsiW3-7jGE7O8\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"抱歉，您的帐号审核失败，请您重新申请。"+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("result")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+map.get("message")+"\",\n" +
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
     * 给推广人发送积分 分红  分销提成
     * @param map
     */
    public  void sendScore(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"QYTJ8zmM9HsnVS_mFHoIZpcbfNji4Inp6AWM_skZdyo\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
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

    /**
     * 通用模板
     *
     * 您的法国租车订单99999999已经确认！车辆由神州公司提供，取车确认号为：88888888（如需与租车公司联系，请提供此确认号），订单金额999元（已支付）。用车时间：2015-06-20 06:30至2015-06-21 22:00，共2天，取车点：广州，还车点：广州。请查收邮件并打印英文电子提车凭证，祝您用车愉快！Your 神州 Confirmation No:111111111(Prepaid).Have a nice trip.
     * 消息类型：订单确认
     * 跟进时间：2015年5月13日 18:36
     * 感谢关注租租车，由您的支持我们会做得更好。
     * @param map
     */
    public  void sendTongYong(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\""+map.get("template_id")+"\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\""+map.get("title")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("messageType")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\""+map.get("end")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }


    /**
     *服务状态提醒  模板
     * @param map
     */
    public  void serviceStatus(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\""+map.get("template_id")+"\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\""+map.get("title")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("serviceType")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+map.get("orderNo")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\":{\n" +
                "                       \"value\":\""+map.get("orderState")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\""+DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\""+map.get("end")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }

    /**
     * 提现到账通知
     * I-5tJSfKERAB40nZDOZfH9uX2OVfhFRo2BLJaOEgnqw
     * @param map
     */
    public   void sendTiXianMessage(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"WUX4fOEkuHRHey0zD9OqUE5WvE6b1Bg-qzKPxk27rUU\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"您申请的提现金额已到帐\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\": {\n" +
                "                       \"value\":\""+ DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+map.get("way")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+map.get("money")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\""+map.get("poundage")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword5\": {\n" +
                "                       \"value\":\""+map.get("daoZhang")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"感谢您的使用，如有疑问请咨询【师傅速达】客服！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }


    /**
     * 新订单通知  lsr4nM05CZxi8pumptyNfcmbHPkaWK7iTUqD7r7fr7A
     * 您收到了一条新的订单
     * 提交时间：2018年2月29日
     * 订单类型：微信订单
     * 客户信息：张三
     * 订单信息：一盒新鲜空气
     * 截止当前，您有X条订单未处理
     * @param map
     */
    public   void sendNewMessage(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"lsr4nM05CZxi8pumptyNfcmbHPkaWK7iTUqD7r7fr7A\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"您收到了一条新的订单\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\": {\n" +
                "                       \"value\":\""+ DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+map.get("orderType")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+map.get("userMessage")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\""+map.get("orderMessage")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"感谢您的使用，如有疑问请咨询【师傅速达】客服！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }

    /**
     * 反馈结果通知 eAWSsvo6Ag-POVzoI2JXHUmZb0JjsQNHnO_mRrLvYsM
     *
     * 您的反馈已经由管理人员处理！
     * 回复状态：已回复
     * 反馈内容：好啊
     * 回复内容：你好啊
     * 感谢您的反馈，希望我们的回复能解决您的问题！
     * @param map
     */
    public  void sendGuYong(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"eAWSsvo6Ag-POVzoI2JXHUmZb0JjsQNHnO_mRrLvYsM\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\""+map.get("title")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\""+map.get("stateMessage")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+map.get("fanKui")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\":{\n" +
                "                       \"value\":\""+map.get("replayMessage")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\""+map.get("end")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }


    /**
     * 余额变动提示 WMwHNoAhtDf6y-f2bdGsQ1y8RMrUp30PVeP_waTTcq8
     *
     * 尊敬的用户,你的账户发生变动
     * 变动时间：2016-03-25 09:36
     * 变动类型：消费扣减
     * 变动金额：111元
     * 当前余额：500元
     * 详情请点击此消息进入会员中心-余额变更记录进行查询!
     * @param map
     */
    public   void sendScoreChange(Map<String,String> map){
        String at = wxService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+at;
        String data = "{\n" +
                "           \"touser\":\""+map.get("openId")+"\",\n" +
                "           \"template_id\":\"WMwHNoAhtDf6y-f2bdGsQ1y8RMrUp30PVeP_waTTcq8\",\n" +
                "           \"url\":\""+map.get("url")+"\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\""+map.get("title")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\": {\n" +
                "                       \"value\":\""+ DateUtil.tranfDate(System.currentTimeMillis())+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\""+map.get("changeType")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\""+map.get("changeScore")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword4\": {\n" +
                "                       \"value\":\""+map.get("totalScore")+"\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"感谢您的使用，如有疑问请咨询【师傅速达】客服！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        String result = WxSend.post(url,data);
        System.out.println(result);
    }
}
