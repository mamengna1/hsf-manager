package cn.hsf.hsfmanager.pojo;

public class Admin {

    private Integer id;
    private String account;   // 运营商账号
    private String password;  // 密码
    private Integer level;   //级别
    private Integer typeId;   // 类别id


    private AdminType adminType;

    public Admin(Integer id, String account, Integer level, Integer typeId) {
        this.id = id;
        this.account = account;
        this.level = level;
        this.typeId = typeId;
    }

    public Admin(String account, String password, Integer level, Integer typeId) {
        this.account = account;
        this.password = password;
        this.level = level;
        this.typeId = typeId;
    }

    public AdminType getAdminType() {
        return adminType;
    }

    public void setAdminType(AdminType adminType) {
        this.adminType = adminType;
    }

    public Admin(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public Admin(Integer level) {
        this.level = level;
    }

    public Admin() {
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", level=" + level +
                ", typeId=" + typeId +
                ", adminType=" + adminType +
                '}';
    }
}
