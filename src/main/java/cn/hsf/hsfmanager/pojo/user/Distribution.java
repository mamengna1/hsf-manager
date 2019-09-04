package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 派单表
 * `id`,`releaseId`,`status`,`refusedMessage`,`createTime`,`updateTime`,`sfId`,`orderId`
 */
public class Distribution {
    private Integer id;   //id
    private Integer releaseId;  //用户下单id
    private Integer statusId;   //接单状态1、新订单2、已接单3、已拒单4、进行中5、已取消6、已完成
    private String refusedMessage;  // 拒单理由（选填）
    private Date createTime;   // 生成时间
    private Date updateTime;   //  派单反馈时间
    private Integer sfId;   // 师傅id
    private Integer orderId;  //订单号(师傅接单后生成)

    private String realName;   //雇佣人
    private String realTitle;  //派单标题
    private String sfName;  // 派单师傅名称
    private String statusName;   //状态名称
    private Integer resId;  // 雇佣id

    private Integer tag;  //标记
    public Distribution() {
    }

    public Distribution(Integer releaseId, Integer sfId) {
        this.releaseId = releaseId;
        this.sfId = sfId;
    }

    public Distribution(Integer id, Integer statusId, String refusedMessage) {
        this.id = id;
        this.statusId = statusId;
        this.refusedMessage = refusedMessage;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Distribution(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealTitle() {
        return realTitle;
    }

    public void setRealTitle(String realTitle) {
        this.realTitle = realTitle;
    }

    public String getSfName() {
        return sfName;
    }

    public void setSfName(String sfName) {
        this.sfName = sfName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(Integer releaseId) {
        this.releaseId = releaseId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getRefusedMessage() {
        return refusedMessage;
    }

    public void setRefusedMessage(String refusedMessage) {
        this.refusedMessage = refusedMessage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSfId() {
        return sfId;
    }

    public void setSfId(Integer sfId) {
        this.sfId = sfId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "Distribution{" +
                "id=" + id +
                ", releaseId=" + releaseId +
                ", statusId=" + statusId +
                ", refusedMessage='" + refusedMessage + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sfId=" + sfId +
                ", orderId=" + orderId +
                '}';
    }
}
