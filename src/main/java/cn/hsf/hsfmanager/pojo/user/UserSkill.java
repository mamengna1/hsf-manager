package cn.hsf.hsfmanager.pojo.user;

public class UserSkill {
    private Integer id;
    private String skillName;

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

    @Override
    public String toString() {
        return "UserSkill{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                '}';
    }
}
