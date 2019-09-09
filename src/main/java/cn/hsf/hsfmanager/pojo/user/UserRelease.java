package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 用户发布找师傅信息
 */
public class UserRelease {

    //`id`,`title`,`nickName`,`phone`,`serviceProvince`,`serviceCity`,`serviceArea`,`serverDetail`,`appointTime`,`demand`,`createDate`,`state`,`receiveId`
    private Integer id;
    private String title;  //雇佣标题
    private String nickName;  //昵称
    private String phone;  //联系方式
    private Integer serviceProvince;  //服务省
    private Integer serviceCity;   // 服务县
    private Integer serviceArea;  // 服务区
    private String serverDetail;  //详细地址
    private Date appointTime;  // 预约时间
    private String demand;  // 需求
    private Date createDate;  //订单生成时间
    private Integer state;  //0 等待中 1接单中  2 已接单 3 实施中 4  订单完成
    private Integer receiveId;  //接单师傅的id
    private Integer userId;   //发布人的id
    private DistributionStatus distributionStatus;
    public UserRelease(Integer id, Integer state) {
        this.id = id;
        this.state = state;
    }

    public UserRelease(Integer id, Integer state, Integer receiveId) {
        this.id = id;
        this.state = state;
        this.receiveId = receiveId;
    }

    public DistributionStatus getDistributionStatus() {
        return distributionStatus;
    }

    public void setDistributionStatus(DistributionStatus distributionStatus) {
        this.distributionStatus = distributionStatus;
    }

    public UserRelease() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getServiceProvince() {
        return serviceProvince;
    }

    public void setServiceProvince(Integer serviceProvince) {
        this.serviceProvince = serviceProvince;
    }

    public Integer getServiceCity() {
        return serviceCity;
    }

    public void setServiceCity(Integer serviceCity) {
        this.serviceCity = serviceCity;
    }

    public Integer getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(Integer serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getServerDetail() {
        return serverDetail;
    }

    public void setServerDetail(String serverDetail) {
        this.serverDetail = serverDetail;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    @Override
    public String toString() {
        return "UserRelease{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", serviceProvince=" + serviceProvince +
                ", serviceCity=" + serviceCity +
                ", serviceArea=" + serviceArea +
                ", serverDetail='" + serverDetail + '\'' +
                ", appointTime=" + appointTime +
                ", demand='" + demand + '\'' +
                ", createDate=" + createDate +
                ", state=" + state +
                ", receiveId=" + receiveId +
                ", userId=" + userId +
                '}';
    }
}
