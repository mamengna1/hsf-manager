package cn.hsf.hsfmanager.pojo.platform;

public class ImgType {

    private Integer id;
    private String imgType;

    @Override
    public String toString() {
        return "ImgType{" +
                "id=" + id +
                ", imgType='" + imgType + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
}
