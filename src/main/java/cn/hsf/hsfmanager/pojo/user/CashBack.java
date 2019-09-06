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
                '}';
    }
}
