package cn.hsf.hsfmanager.pojo;

public class App {
    //
    private Integer id;
    //
    private String appId;
    //商户号
    private String mchId;
    //开发者密码
    private String appSecret;
    //
    private String token;
    //支付秘钥
    private String key;
    private String accessToken;  //token
    private Long expireTime;  //过期时间

    public App() {
    }

    public App(String accessToken,String expireIn) {
        this.accessToken = accessToken;
        //系统时间 + 有效期 =过期时间
        this.expireTime = System.currentTimeMillis()+Integer.parseInt(expireIn)*1000;
        ;
    }
    /**
     * 判断token是否过期
     * @return
     */
    public boolean isExpired(){
        return System.currentTimeMillis()>expireTime;
    }

    //get set 方法
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setAppId (String  appId){
        this.appId=appId;
    }
    public  String getAppId(){
        return this.appId;
    }
    public void setMchId (String  mchId){
        this.mchId=mchId;
    }
    public  String getMchId(){
        return this.mchId;
    }
    public void setAppSecret (String  appSecret){
        this.appSecret=appSecret;
    }
    public  String getAppSecret(){
        return this.appSecret;
    }
    public void setToken (String  token){
        this.token=token;
    }
    public  String getToken(){
        return this.token;
    }
    public void setKey (String  key){
        this.key=key;
    }
    public  String getKey(){
        return this.key;
    }
}
