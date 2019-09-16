package cn.hsf.hsfmanager.pojo.platform;

import java.util.Date;

public class PlatformSlideshow {

    private Integer id;
    private String url;   //图片地址
    private Integer imgType;    //图片类型
    private Integer priority;   //优先级
    private String linkUrl;   // 路径
    private String title;  //标题
    private Integer state ;   //状态
    private Date createDate;  //创建时间

    private ImgType type;

    @Override
    public String toString() {
        return "PlatformSlideshow{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", imgType=" + imgType +
                ", priority=" + priority +
                ", linkUrl='" + linkUrl + '\'' +
                ", title='" + title + '\'' +
                ", state=" + state +
                ", createDate=" + createDate +
                ", type=" + type +
                '}';
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getImgType() {
        return imgType;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ImgType getType() {
        return type;
    }

    public void setType(ImgType type) {
        this.type = type;
    }
}
