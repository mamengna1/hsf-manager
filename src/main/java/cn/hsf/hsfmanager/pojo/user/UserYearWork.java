package cn.hsf.hsfmanager.pojo.user;

/**
 * @author kaituozhe
 */
public class UserYearWork {

    private Integer id;
    private String yearName;

    @Override
    public String toString() {
        return "UserYearWork{" +
                "id=" + id +
                ", yearName='" + yearName + '\'' +
                '}';
    }

    public UserYearWork(Integer id, String yearName) {
        this.id = id;
        this.yearName = yearName;
    }

    public UserYearWork() {
    }

    public UserYearWork(String yearName) {
        this.yearName = yearName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }
}
