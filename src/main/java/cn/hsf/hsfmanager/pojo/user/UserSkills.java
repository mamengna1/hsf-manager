package cn.hsf.hsfmanager.pojo.user;

import java.util.Date;

/**
 * 技能详细信息表  `id`,`skillName`,`parentId`,`describe`
 */
public class UserSkills {
    private Integer id;
    private String skillName;
    private Integer parentId;
    private String describes;
    private String imgUrl;  //图标路径
    private Integer isRecommend;  //是否推荐
    private Date updDate;  // 推荐时间


    private Integer mark;  //标记


    public UserSkills(String skillName,Integer parentId,String describes) {
        this.skillName = skillName;
        this.parentId = parentId;
        this.describes = describes;
    }

    public UserSkills(String skillName,Integer parentId,String describes,String imgUrl, Integer isRecommend) {
        this.skillName = skillName;
        this.parentId = parentId;
        this.describes = describes;
        this.imgUrl = imgUrl;
        this.isRecommend = isRecommend;
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

    public UserSkills(Integer id, Integer isRecommend) {
        this.id = id;
        this.isRecommend = isRecommend;
    }

    public UserSkills() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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
                ", imgUrl='" + imgUrl + '\'' +
                ", isRecommend=" + isRecommend +
                ", updDate=" + updDate +
                ", mark=" + mark +
                '}';
    }
}
