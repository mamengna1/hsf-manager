package cn.hsf.hsfmanager.pojo;

public class Admin {

    private Long id;
    private String account;   // 运营商账号
    private String password;  // 密码

    public Admin(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public Admin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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


}
