package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 用户表
 */
public class User {
    //id
    private Integer id;
    //昵称
    private String nickName;
    //用户唯一标识openId
    private String openId;
    //性别
    private Integer sex;
    //头像地址
    private String headPic;
    //国家
    private String country;
    //省份
    private String province;
    //地区
    private String city;
    //绑定手机号
    private String phone;
    //是否关注
    private Integer isSub;
    //会员类型
    private Integer memberType;
    //会员来源：1、关注 2、扫码
    private String sourceType;
    //累计充值
    private Double totalMoney;
    //账户余额
    private Double balanceMoney;
    //总积分
    private Double totalScore;
    //剩余积分
    private Double balanceScore;
    //上级OpenId
    private String userParent;
    //注册时间
    private Date createDate;
    //最后登录时间
    private Date lastLoginTime;
    //师傅的详细信息
    private Integer detailId;


    public User(Integer id, Integer memberType, Double balanceMoney, Double totalScore, Double balanceScore) {
        this.id = id;
        this.memberType = memberType;
        this.balanceMoney = balanceMoney;
        this.totalScore = totalScore;
        this.balanceScore = balanceScore;
    }

    public User(String openId, Double totalScore) {
        this.openId = openId;
        this.totalScore = totalScore;
    }

    public User() {
    }

    //get set 方法
    public void setId (Integer  id){
        this.id=id;
    }
    public  Integer getId(){
        return this.id;
    }
    public void setNickName (String  nickName){
        this.nickName=nickName;
    }
    public  String getNickName(){
        return this.nickName;
    }
    public void setOpenId (String  openId){
        this.openId=openId;
    }
    public  String getOpenId(){
        return this.openId;
    }
    public void setSex (Integer  sex){
        this.sex=sex;
    }
    public  Integer getSex(){
        return this.sex;
    }
    public void setHeadPic (String  headPic){
        this.headPic=headPic;
    }
    public  String getHeadPic(){
        return this.headPic;
    }
    public void setCountry (String  country){
        this.country=country;
    }
    public  String getCountry(){
        return this.country;
    }
    public void setProvince (String  province){
        this.province=province;
    }
    public  String getProvince(){
        return this.province;
    }
    public void setCity (String  city){
        this.city=city;
    }
    public  String getCity(){
        return this.city;
    }
    public void setPhone (String  phone){
        this.phone=phone;
    }
    public  String getPhone(){
        return this.phone;
    }
    public void setIsSub (Integer  isSub){
        this.isSub=isSub;
    }
    public  Integer getIsSub(){
        return this.isSub;
    }
    public void setMemberType (Integer  memberType){
        this.memberType=memberType;
    }
    public  Integer getMemberType(){
        return this.memberType;
    }
    public void setSourceType (String  sourceType){
        this.sourceType=sourceType;
    }
    public  String getSourceType(){
        return this.sourceType;
    }
    public void setTotalMoney (Double  totalMoney){
        this.totalMoney=totalMoney;
    }
    public  Double getTotalMoney(){
        return this.totalMoney;
    }
    public void setBalanceMoney (Double  balanceMoney){
        this.balanceMoney=balanceMoney;
    }
    public  Double getBalanceMoney(){
        return this.balanceMoney;
    }
    public void setTotalScore (Double  totalScore){
        this.totalScore=totalScore;
    }
    public  Double getTotalScore(){
        return this.totalScore;
    }
    public void setBalanceScore (Double  balanceScore){
        this.balanceScore=balanceScore;
    }
    public  Double getBalanceScore(){
        return this.balanceScore;
    }
    public void setUserParent (String  userParent){
        this.userParent=userParent;
    }
    public  String getUserParent(){
        return this.userParent;
    }
    public void setCreateDate (Date createDate){
        this.createDate=createDate;
    }
    public  Date getCreateDate(){
        return this.createDate;
    }
    public void setLastLoginTime (Date  lastLoginTime){
        this.lastLoginTime=lastLoginTime;
    }
    public  Date getLastLoginTime(){
        return this.lastLoginTime;
    }
    public void setDetailId (Integer  detailId){
        this.detailId=detailId;
    }
    public  Integer getDetailId(){
        return this.detailId;
    }
}
