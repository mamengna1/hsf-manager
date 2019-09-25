package cn.hsf.hsfmanager.pojo;

public class AdminType {

    private Integer id;
    private String typeName;

    @Override
    public String toString() {
        return "AdminType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }

    public AdminType(String typeName) {
        this.typeName = typeName;
    }

    public AdminType() {
    }

    public AdminType(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
