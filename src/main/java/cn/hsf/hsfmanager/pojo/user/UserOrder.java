package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 评论表
 * `id`,`comments`,`starLevel`,`commentTypeId`,`commentTime`
 */
public class UserOrder {
    private Integer id;
    private String comments;  //评论内容
    private Integer starLevel;  //星级
    private Integer commentTypeId ; //1、用户评论2、管理员默认评论
    private Date commentTime;  // 评论时间
    private Integer userId;  //下单人id
    private Integer sfId;  //师傅id

    public UserOrder() {
    }

    public UserOrder(String comments, Integer starLevel, Integer commentTypeId) {
        this.comments = comments;
        this.starLevel = starLevel;
        this.commentTypeId = commentTypeId;
    }

    public UserOrder(Integer id, String comments, Integer starLevel) {
        this.id = id;
        this.comments = comments;
        this.starLevel = starLevel;
    }

    public UserOrder(Integer id, String comments, Integer starLevel, Integer commentTypeId, Integer userId, Integer sfId) {
        this.id = id;
        this.comments = comments;
        this.starLevel = starLevel;
        this.commentTypeId = commentTypeId;
        this.userId = userId;
        this.sfId = sfId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public Integer getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(Integer commentTypeId) {
        this.commentTypeId = commentTypeId;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        return "UserOrderMapper{" +
                "id=" + id +
                ", comments='" + comments + '\'' +
                ", starLevel=" + starLevel +
                ", commentTypeId=" + commentTypeId +
                ", commentTime=" + commentTime +
                '}';
    }
}
