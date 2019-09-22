package cn.hsf.hsfmanager.pojo.platform;

public class GraphicType {
   private Integer id;
   private String graName;

    public GraphicType() {
    }

    public GraphicType(String graName) {
        this.graName = graName;
    }

    public GraphicType(Integer id) {
        this.id = id;
    }

    public GraphicType(Integer id, String graName) {
        this.id = id;
        this.graName = graName;
    }

    @Override
    public String toString() {
        return "GraphicType{" +
                "id=" + id +
                ", graName='" + graName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGraName() {
        return graName;
    }

    public void setGraName(String graName) {
        this.graName = graName;
    }
}
