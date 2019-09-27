package cn.hsf.hsfmanager.pojo.user;

/**
 * 服务地区
 */
public class SerAddress {
    private Integer id;
    private String addName;
    private Integer parentId;  //父类id

    public SerAddress() {
    }

    public SerAddress(String addName, Integer parentId) {
        this.addName = addName;
        this.parentId = parentId;
    }

    public SerAddress(Integer id, String addName, Integer parentId) {
        this.id = id;
        this.addName = addName;
        this.parentId = parentId;
    }

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
