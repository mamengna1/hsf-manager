package cn.hsf.hsfmanager.pojo.user;

public class DistributionStatus {
    private Integer id;
    private String statusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "DistributionStatus{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
