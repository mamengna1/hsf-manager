package cn.hsf.hsfmanager.pojo.appMenu;


/**
 * @author kaituozhe
 */
public class AppMenu {

    //
    private Integer id;
    //名称
    private String menuName;
    //菜单类型
    private Integer menuTypeId;
    //父菜单
    private Integer parentMenuId;
    //路径 OR 自动回复信息
    private String message;
    //menuType=1的时候需要填写
    private String key;

    private AppMenuType appMenuType;

    public AppMenu(String menuName, Integer menuTypeId, Integer parentMenuId, String message, String key) {
        this.menuName = menuName;
        this.menuTypeId = menuTypeId;
        this.parentMenuId = parentMenuId;
        this.message = message;
        this.key = key;
    }

    public AppMenu(Integer id, String menuName, Integer menuTypeId, String message, String key) {
        this.id = id;
        this.menuName = menuName;
        this.menuTypeId = menuTypeId;
        this.message = message;
        this.key = key;
    }

    public AppMenu() {
    }

    @Override
    public String toString() {
        return "AppMenu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", menuTypeId=" + menuTypeId +
                ", parentMenuId=" + parentMenuId +
                ", message='" + message + '\'' +
                ", key='" + key + '\'' +
                ", appMenuType=" + appMenuType +
                '}';
    }
    //get set 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getMenuTypeId() {
        return menuTypeId;
    }

    public void setMenuTypeId(Integer menuTypeId) {
        this.menuTypeId = menuTypeId;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public AppMenuType getAppMenuType() {
        return appMenuType;
    }

    public void setAppMenuType(AppMenuType appMenuType) {
        this.appMenuType = appMenuType;
    }
}
