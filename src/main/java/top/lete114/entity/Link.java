package top.lete114.entity;

import java.util.Date;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 19:55
 */
public class Link {

    private Integer linkId;
    private String avatar;
    private Integer linkRank;
    private String linkName;
    private String linkUrl;
    private String linkDescription;
    private Date createTime;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getLinkRank() {
        return linkRank;
    }

    public void setLinkRank(Integer linkRank) {
        this.linkRank = linkRank;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

