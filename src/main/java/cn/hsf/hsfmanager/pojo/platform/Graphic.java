package cn.hsf.hsfmanager.pojo.platform;

import java.util.Date;

/**
 * 资讯类  `id`,`title`,`subtitle`,`content`,`imageUrl`,`viewCount`,`createDate`   `tb_graphic`
 */
public class Graphic {
    private Integer id;
    private String title;  //主标题
    private String subtitle;  // 副标题
    private String content;  //内容
    private String imageUrl;   //缩略图
    private Integer viewCount;  //点击量
    private Date createDate;

    public Graphic() {
    }

    public Graphic(String title, String subtitle, String content,  Integer viewCount) {
        this.title = title;
        this.subtitle = subtitle;
        this.content = content;
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "Graphic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", viewCount=" + viewCount +
                ", createDate=" + createDate +
                '}';
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
