package cn.hsf.hsfmanager.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class WxSend {
    /**
     * 向指定的地址发送get请求
     * @param url
     * @return
     */
    public static String get(String url){
        try {
            URL urlObj = new URL(url);
            // 开连接
            URLConnection connection = urlObj.openConnection();
            InputStreamReader is = new InputStreamReader(connection.getInputStream(),"UTF-8");
            char[] c = new char[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(c)) != -1) {
                sb.append(new String(c,0, len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 像指定的地址发送一个post，并携带数据
     * @param url
     * @param data
     * @return
     */
    public static String post(String url,String data){
        try {
            URL urlObj = new URL(url);
            URLConnection connection = urlObj.openConnection();
            // 要发送数据出去，必须要设置为可发送数据状态
            connection.setDoOutput(true);
            // 获取输出流
            OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            // 写出数据
            os.write(data);
            os.close();
            // 获取输入流
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while ((len = is.read(b)) != -1) {
                sb.append(new String(b, 0, len));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


/*    @Test
    public void contextLoads() {
        Long a = Long.valueOf(1);
        Long b = Long.valueOf(-1);
        System.out.println(b>0);
    }*/
}
