package cn.hsf.hsfmanager.pojo.user;

/**
 * 技能详细信息表  `id`,`skillName`,`parentId`,`describe`
 */
public class UserSkills {
    private Integer id;
    private String skillName;
    private Integer parentId;
    private String describes;

    public UserSkills(String skillName,Integer parentId,String describes) {
        this.skillName = skillName;
        this.parentId = parentId;
        this.describes = describes;
    }

    public UserSkills(Integer id, String skillName, Integer parentId, String describes) {
        this.id = id;
        this.skillName = skillName;
        this.parentId = parentId;
        this.describes = describes;
    }

    public UserSkills(String skillName) {
        this.skillName = skillName;
    }

    public UserSkills(Integer id) {
        this.id = id;
    }

    public UserSkills() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    @Override
    public String toString() {
        return "UserSkills{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                ", parentId=" + parentId +
                ", describes='" + describes + '\'' +
                '}';
    }
}
