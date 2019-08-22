package cn.hsf.hsfmanager.service.wx;

import cn.hsf.hsfmanager.mapper.AppMapper;
import cn.hsf.hsfmanager.pojo.AccessTocken;
import cn.hsf.hsfmanager.pojo.App;
import cn.hsf.hsfmanager.util.WxSend;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 签名  token
 */
@Service
public class WxService {
    //注入配置信息

    @Resource
    private AppMapper appMapper;
    //token登录入口
    private static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //用于存储token
    private static AccessTocken at;


    /**
     * 验证签名
     *
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     */
    public  boolean check(String timestamp, String nonce, String signature) {
        App app = appMapper.selApp();
        // 1）将token、timestamp、nonce三个参数进行字典序排序
        String[] strs = new String[]{app.getToken(), timestamp, nonce};
        Arrays.sort(strs);
        System.out.println("token:"+app.getToken());
        //2）将三个参数字符串拼接成一个字符串进行sha1加密
        String str = strs[0] + strs[1] + strs[2];
        String mysig = sha1(str);
        System.out.println(mysig);
        System.out.println(signature);

        //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return mysig.equalsIgnoreCase(signature);
    }
    /**
     * 对sha1加密
     *
     * @param str
     * @return
     */
    private  String sha1(String str) {

        try {
            //获取一个加密对象     【此处若想从、使用MD5加密，只需要传入MD5即可】
            MessageDigest md = MessageDigest.getInstance("sha1");
            //加密
            byte[] digest = md.digest(str.getBytes());
            char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};  //16进制
            //处理加密结果
            StringBuilder builder = new StringBuilder();
            for (byte b : digest) {
                builder.append(chars[(b >> 4) & 15]);  //  15 = 00001111     //  高4位位0
                builder.append(chars[b & 15]);   // 低4位为0
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取token
     */
    private void getToken() {
        App app = appMapper.selApp();
        String url = GET_TOKEN_URL.replace("APPID",app.getAppId()).replace("APPSECRET", app.getAppSecret());
        String tokenStr = WxSend.get(url);
        System.out.println("tokenStr 信息 ： "+tokenStr);
        JSONObject jsonObject = JSONObject.fromObject(tokenStr);
        String token = jsonObject.getString("access_token");
        String expireIn = jsonObject.getString("expires_in");
        //创建token对象,并存起来。
        at = new AccessTocken(token, expireIn);
    }

    /**
     * 向外暴露获取token的方法
     *
     * @return
     */
    public  String getAccessToken() {
        if (at == null || at.isExpired()) {   //如果为null 或过期
            getToken();
        }
        return at.getAccessTockem();
    }
    /**
     * 上传临时素材
     *
     * @param path 上传的文件的路径
     * @param type 上传的文件类型
     * @return
     */
    public  String upload(String path, String type) {
        File file = new File(path);
        //地址
        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        url = url.replace("ACCESS_TOKEN", getAccessToken()).replace("TYPE", type);
        try {
            URL urlObj = new URL(url);
            //强转为案例连接
            HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
            //设置连接的信息
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //设置请求头信息
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "utf8");
            //数据的边界
            String boundary = "-----" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            //获取输出流
            OutputStream out = conn.getOutputStream();
            //创建文件的输入流
            InputStream is = new FileInputStream(file);
            //第一部分：头部信息
            //准备头部信息
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition:form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            out.write(sb.toString().getBytes());
            System.out.println(sb.toString());
            //第二部分：文件内容
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                out.write(b, 0, len);
            }
            is.close();
            //第三部分：尾部信息
            String foot = "\r\n--" + boundary + "--\r\n";
            out.write(foot.getBytes());
            out.flush();
            out.close();
            //读取数据
            InputStream is2 = conn.getInputStream();
            StringBuilder resp = new StringBuilder();
            while ((len = is2.read(b)) != -1) {
                resp.append(new String(b, 0, len));
            }
            return resp.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





}
