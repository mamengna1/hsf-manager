package cn.hsf.hsfmanager.util;

/**
 * 路径合集
 *    1. 域名  (富文本里面的是写死的) MyEditorUploader
 *    2. 富文本图片上传的路径写死的  config.json
 */
public class URLS {

    public static final String COMPANY_PAY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";   // 支付到零钱的接口路径

    public static final String  CERT_PATH = "D:\\sfsdCertificate\\apiclient_cert.p12";   //安装证书的路径  D:\sfsdCertificate

    public static final String DOMAIN_NAME = "http://sfsd.cust.86blue.cn";   //域名
    public static final String IMAGE_ADDRESS="D:\\software\\Tomcat\\imageTomcat\\webapps\\images\\";   //实际存放的图片路径
    public static final Integer SUB_LENGTH = 34;  // 截取的长度 http://java.86blue.cn/images/  （从1开始共29个）

    public static final String SLIDE_SHOW ="/images/sfsd/manager/slideshow/";              //轮播图存放路径
    public static final String SLIDE_SHOW_TEMP ="/images/sfsd/manager/slideshowTemp/";     //轮播图存放的临时路径路径
    public static final String GRAPHIC ="/images/sfsd/manager/graphic/thumbnail/";    //图文管理缩略图存入的图片


    //以下是中蓝的支付商户，目前为代付，先不写入数据库
    public static final String MCHID ="1537153931";   //支付商户
    public static final String KEY = "9a997d868d8981f6aff4bc3f3f60e7d7";   //支付秘钥
    public static final String APPID = "wx8bf85bd98eaddb86";

}
