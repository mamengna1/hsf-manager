package cn.hsf.hsfmanager.pojo.user;

/**
 * 服务地区
 */
public class SerAddress {
    private Integer id;
    private String addName;
    private Integer parentId;  //父类id

    @Override
    public String toString() {
        return "SerAddress{" +
                "id=" + id +
                ", addName='" + addName + '\'' +
                ", parentId=" + parentId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
