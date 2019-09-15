package cn.hsf.hsfmanager.pojo.platform;

public class PlatformSlideshow {

    private Integer id;
    private String url;
    private Integer imgType;
    private Integer priority;

    private ImgType type;

    @Override
    public String toString() {
        return "PlatformSlideshow{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", imgType=" + imgType +
                ", priority=" + priority +
                ", type=" + type +
                '}';
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
