package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 师父详细信息
 */
public class UserDetail {
    //id
    private Integer id;
    //真实姓名
    private String name;
    //身份证号
    private String card;
    //居住省
    private Integer placeProvince;
    //居住城市
    private Integer placeCity;
    //居住区
    private Integer placeArea;
    //详细地址
    private String address;
    // 身份证正面
    private String cardOne;
    // 身份证反面
    private String cardTwo;
    //技能
    private String skills;
    //工作省
    private Integer workProvince;
    //工作城市
    private Integer workCity;
    //工作区
    private String  workArea;
    //审核状态 (0 审核中 1 审核通过 2 审核不通过)
    private Integer status;
    //审核不通过的原因
    private String statusMessage;
    //在线状态0不在线 1 在线
    private Integer lineStatus;
    private Integer totalOrder;  // 总接单数量
    private Integer totalRefuse ;  //总拒单数量
    private Integer yearWorkId;
    private Date updTime;  //提交时间
    private Date passTime;  //审核时间

    private Integer totalCount;  // 总结单数量
    private Integer countDay;  // 当天已接几单
    private String skillName;   //技能名称
    private String areas;   //地区
    private String isExist;   //是否存在
    private Integer shijian;
    public UserDetail() {
    }

    public UserDetail(Integer id, Integer status, String statusMessage,Integer lineStatus,Integer shijian) {
        this.id = id;
        this.status = status;
        this.statusMessage = statusMessage;
        this.lineStatus = lineStatus;
        this.shijian = shijian;
    }

    public UserDetail(Integer id, Integer status, String statusMessage, Integer shijian) {
        this.id = id;
        this.status = status;
        this.statusMessage = statusMessage;
        this.shijian = shijian;
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", card='" + card + '\'' +
                ", placeProvince=" + placeProvince +
                ", placeCity=" + placeCity +
                ", placeArea=" + placeArea +
                ", address='" + address + '\'' +
                ", cardOne='" + cardOne + '\'' +
                ", cardTwo='" + cardTwo + '\'' +
                ", skills='" + skills + '\'' +
                ", workProvince=" + workProvince +
                ", workCity=" + workCity +
                ", workArea=" + workArea +
                ", status=" + status +
                ", statusMessage='" + statusMessage + '\'' +
                ", lineStatus=" + lineStatus +
                ", yearWorkId=" + yearWorkId +
                ", totalCount=" + totalCount +
                ", countDay=" + countDay +
                '}';
    }

    //get set 方法


    public Integer getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(Integer totalOrder) {
        this.totalOrder = totalOrder;
    }

    public Integer getTotalRefuse() {
        return totalRefuse;
    }

    public void setTotalRefuse(Integer totalRefuse) {
        this.totalRefuse = totalRefuse;
    }

    public Integer getShijian() {
        return shijian;
    }

    public void setShijian(Integer shijian) {
        this.shijian = shijian;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getIsExist() {
        return isExist;
    }

    public void setIsExist(String isExist) {
        this.isExist = isExist;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCountDay() {
        return countDay;
    }

    public void setCountDay(Integer countDay) {
        this.countDay = countDay;
    }

    public Integer getYearWorkId() {
        return yearWorkId;
    }

    public void setYearWorkId(Integer yearWorkId) {
        this.yearWorkId = yearWorkId;
    }

    public Integer getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(Integer lineStatus) {
        this.lineStatus = lineStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardOne() {
        return cardOne;
    }

    public void setCardOne(String cardOne) {
        this.cardOne = cardOne;
    }

    public String getCardTwo() {
        return cardTwo;
    }

    public void setCardTwo(String cardTwo) {
        this.cardTwo = cardTwo;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Integer getPlaceProvince() {
        return placeProvince;
    }

    public void setPlaceProvince(Integer placeProvince) {
        this.placeProvince = placeProvince;
    }

    public Integer getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(Integer placeCity) {
        this.placeCity = placeCity;
    }

    public Integer getPlaceArea() {
        return placeArea;
    }

    public void setPlaceArea(Integer placeArea) {
        this.placeArea = placeArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getWorkProvince() {
        return workProvince;
    }

    public void setWorkProvince(Integer workProvince) {
        this.workProvince = workProvince;
    }

    public Integer getWorkCity() {
        return workCity;
    }

    public void setWorkCity(Integer workCity) {
        this.workCity = workCity;
    }

    public String getWorkArea() {
        return workArea;
    }

    public void setWorkArea(String workArea) {
        this.workArea = workArea;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}

