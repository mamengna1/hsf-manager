package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 提现记录
 `id`,`openId`,`money`,`createDate`,`backStatusId`,`comment`
 */
public class CashBack {
    private Integer id;
    private String openId;
    private double money;  //提现金额
    private Date createDate;  // 提现时间
    private Integer backStatusId;  // 返现状态
    private String comment;  //错误码
    private String userName;  //提现人

    private BackStatus backStatus;
    private User user;
    private UserDetail userDetail;

    public CashBack() {
    }

    public CashBack(Integer id, Integer backStatusId, String comment) {
        this.id = id;
        this.backStatusId = backStatusId;
        this.comment = comment;
    }

    public CashBack(Integer id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public CashBack(Integer id, Integer backStatusId) {
        this.id = id;
        this.backStatusId = backStatusId;
    }

    //get set

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BackStatus getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(BackStatus backStatus) {
        this.backStatus = backStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getBackStatusId() {
        return backStatusId;
    }

    public void setBackStatusId(Integer backStatusId) {
        this.backStatusId = backStatusId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CashBack{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", money=" + money +
                ", createDate=" + createDate +
                ", backStatusId=" + backStatusId +
                ", comment='" + comment + '\'' +
                ", userName='" + userName + '\'' +
                ", backStatus=" + backStatus +
                ", user=" + user +
                ", userDetail=" + userDetail +
                '}';
    }
}
