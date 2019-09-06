package cn.hsf.hsfmanager.pojo.user;

public class BackStatus {
    private Integer id;
    private String  backStatusName;  //提现状态

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackStatusName() {
        return backStatusName;
    }

    public void setBackStatusName(String backStatusName) {
        this.backStatusName = backStatusName;
    }

    @Override
    public String toString() {
        return "BackStatus{" +
                "id=" + id +
                ", backStatusName='" + backStatusName + '\'' +
                '}';
    }
}
