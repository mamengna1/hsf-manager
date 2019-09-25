package cn.hsf.hsfmanager.pojo.user;

/**
 * @author kaituozhe
 */
public class ScoreSourceType {

    private Integer id;
    private String sourceName;

    @Override
    public String toString() {
        return "ScoreSourceType{" +
                "id=" + id +
                ", sourceName='" + sourceName + '\'' +
                '}';
    }

    public ScoreSourceType(Integer id, String sourceName) {
        this.id = id;
        this.sourceName = sourceName;
    }

    public ScoreSourceType() {
    }

    public ScoreSourceType(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
