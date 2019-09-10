package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

public class UserScoreSource {

  //  `id`,`openId`,`score`,`scoreSourceId`,`sourceOpenId`,`createDate`
    private Integer id;
    private String openId;
    private double score;
    private Integer scoreSourceId;   //积分来源
    private String sourceOpenId;   //来自分红的openId
    private Date createDate;

    private ScoreSourceType scoreSourceType;  //积分来源
    private User user;  //用户表
    private String userName;   //用户昵称
    public UserScoreSource() {
    }

    public UserScoreSource(String openId, double score, Integer scoreSourceId) {
        this.openId = openId;
        this.score = score;
        this.scoreSourceId = scoreSourceId;
    }

    public UserScoreSource(String openId, double score, Integer scoreSourceId, String sourceOpenId) {
        this.openId = openId;
        this.score = score;
        this.scoreSourceId = scoreSourceId;
        this.sourceOpenId = sourceOpenId;
    }

    public UserScoreSource(String openId, Integer scoreSourceId) {
        this.openId = openId;
        this.scoreSourceId = scoreSourceId;
    }

    public UserScoreSource(Integer id, double score, Integer scoreSourceId) {
        this.id = id;
        this.score = score;
        this.scoreSourceId = scoreSourceId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ScoreSourceType getScoreSourceType() {
        return scoreSourceType;
    }

    public void setScoreSourceType(ScoreSourceType scoreSourceType) {
        this.scoreSourceType = scoreSourceType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "UserScoreSource{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", score=" + score +
                ", scoreSourceId=" + scoreSourceId +
                ", sourceOpenId='" + sourceOpenId + '\'' +
                ", createDate=" + createDate +
                ", scoreSourceType=" + scoreSourceType +
                ", user=" + user +
                ", userName='" + userName + '\'' +
                '}';
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Integer getScoreSourceId() {
        return scoreSourceId;
    }

    public void setScoreSourceId(Integer scoreSourceId) {
        this.scoreSourceId = scoreSourceId;
    }

    public String getSourceOpenId() {
        return sourceOpenId;
    }

    public void setSourceOpenId(String sourceOpenId) {
        this.sourceOpenId = sourceOpenId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
