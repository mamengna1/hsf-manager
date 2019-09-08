package cn.hsf.hsfmanager.service.wx;

import cn.hsf.hsfmanager.pojo.ResultData;
import cn.hsf.hsfmanager.pojo.user.CashBack;
import cn.hsf.hsfmanager.service.user.CashBackService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

@Service
public class CertHttpUtil {

    @Resource
    private CashBackService cashBackService;
    @Resource
    private TemplateService templateService;
    /**
     * 通过Https往API post xml数据
     *
     * @param url API地址
     * @param xmlObj 要提交的XML数据对象
     * @param mchId 商户ID
     * @param certPath 证书位置
     * @return
     */
    public ResultData postData(String url, String xmlObj, String mchId, String certPath, Integer id) {
        boolean flage = false;
        try {
            //指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            //windows系统
            FileInputStream instream = new FileInputStream(new File(certPath));

            try {
                //指定PKCS12的密码(商户ID)
                //keyStore.load(instream, accountUtil.getWxPartnerId().toCharArray());
                keyStore.load(instream, mchId.toCharArray());
            }finally {
                instream.close();
            }
            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
            //指定TLS版本, Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            //设置httpclient的SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            HttpPost httppost = new HttpPost(url);
            //这里要设置编码，不然xml中有中文的话会提示签名失败或者展示乱码
            httppost.addHeader("Content-Type", "text/xml");
            StringEntity se = new StringEntity(xmlObj,"UTF-8");
            httppost.setEntity(se);
            CloseableHttpResponse responseEntry = httpclient.execute(httppost);
            try {
                HttpEntity entity = responseEntry.getEntity();
                if (entity != null) {
                    System.out.println("响应内容长度 : "+ entity.getContentLength());
                    SAXReader saxReader = new SAXReader();
                    Document document = saxReader.read(entity.getContent());
                    Element rootElt = document.getRootElement();
                    String resultCode = rootElt.elementText("result_code");
                    if(resultCode.equals("SUCCESS")){
                        System.out.println("成功了！");

                        //保存红包信息到数据库 ,把错误信息在数据库中清空
                        CashBack cashBack = new CashBack(id,2,null);
                        cashBackService.updateCashBack(cashBack);
                       ;
                      /*  Map map2 = new HashMap();
                        map2.put("openId", cashBackService.selAllById(id).getOpenId()) ;
                        map2.put("template_id","TF2-OgTgYB6EYKzmno0NjbZobdCadK7U0d0E9O9ZogA") ;
                        map2.put("title","您的提现到账啦") ;
                        map2.put("serviceType","提现到账通知") ;
                        map2.put("orderNo","无") ;
                        map2.put("orderState","提现成功") ;
                        map2.put("end","提现【"+cashBackService.selAllById(id).getMoney()+"元】，已到账，请注意查收") ;
                        templateService.serviceStatus(map2);*/
                        flage = true;
                    }else{
                        String error = rootElt.elementText("err_code");
                        ResultData resultData = resData(error);
                        System.out.println("resultData.getErrorMsg() : "+ resultData.getErrorMsg());
                        // 把错误信息存入数据库
                        CashBack cashBack = new CashBack(id,resultData.getErrorMsg());
                        cashBackService.updateCashBack(cashBack);
                        return resultData;
                    }
                }
                EntityUtils.consume(entity);
                return new ResultData(flage);
            }catch(Exception e){
                System.out.println("错误信息 ： "+e.getMessage());
                System.out.println("请求失败");
            }
            finally {
                responseEntry.close();
            }
        }catch(Exception e){
            System.out.println("错误信息 ： "+e.getMessage());
        }

        return new ResultData(false);
    }


    /**
     *  企业付款到零钱  错误码返回
     * @param errorCode
     * @return
     */
    private static ResultData resData(String errorCode) {
        if (errorCode.equals("NO_AUTH")) {
            return new ResultData(false, "没有该接口权限");
        } else if (errorCode.equals("AMOUNT_LIMIT")) {
            return new ResultData(false, "金额超限");
        } else if (errorCode.equals("PARAM_ERROR")) {
            return new ResultData(false, "参数错误");
        } else if (errorCode.equals("OPENID_ERROR")) {
            return new ResultData(false, "Openid错误");
        } else if (errorCode.equals("SEND_FAILED")) {
            return new ResultData(false, "付款错误");
        } else if (errorCode.equals("NOTENOUGH")) {
            return new ResultData(false, "余额不足");
        } else if (errorCode.equals("SYSTEMERROR")) {
            return new ResultData(false, "系统繁忙，请稍后再试");
        } else if (errorCode.equals("NAME_MISMATCH")) {
            return new ResultData(false, "姓名校验出错");
        } else if (errorCode.equals("SIGN_ERROR")) {
            return new ResultData(false, "签名错误");
        } else if (errorCode.equals("XML_ERROR")) {
            return new ResultData(false, "Post内容出错");
        } else if (errorCode.equals("FATAL_ERROR")) {
            return new ResultData(false, "两次请求参数不一致");
        } else if (errorCode.equals("FREQ_LIMIT")) {
            return new ResultData(false, "超过频率限制，请稍后再试");
        } else if (errorCode.equals("MONEY_LIMIT")) {
            return new ResultData(false, "已经达到今日付款总额上限/已达到付款给此用户额度上限");
        } else if (errorCode.equals("CA_ERROR")) {
            return new ResultData(false, "商户API证书校验出错");
        } else if (errorCode.equals("V2_ACCOUNT_SIMPLE_BAN")) {
            return new ResultData(false, "无法给非实名用户付款");
        } else if (errorCode.equals("PARAM_IS_NOT_UTF8")) {
            return new ResultData(false, "请求参数中包含非utf8编码字符");
        } else if (errorCode.equals("SENDNUM_LIMIT")) {
            return new ResultData(false, "该用户今日付款次数超过限制,如有需要请登录微信支付商户平台更改API安全配置");
        } else if (errorCode.equals("RECV_ACCOUNT_NOT_ALLOWED")) {
            return new ResultData(false, "收款账户不在收款账户列表");
        } else if (errorCode.equals("PAY_CHANNEL_NOT_ALLOWED")) {
            return new ResultData(false, "本商户号未配置API发起能力");
        }
        return new ResultData(false);
    }

}
