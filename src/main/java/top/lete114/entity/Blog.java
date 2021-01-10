package top.lete114.entity;


import java.util.Date;

public class Blog {

  private Integer blogId;
  private String blogTitle;
  private String blogSubUrl;
  private String blogCoverImage;
  private String blogContent;
  private Integer blogCategoryId;
  private String blogCategoryName;
  private String blogTags;
  private String description;
  private Integer blogStatus;
  private Integer blogViews;
  private Integer enableComment;
  private Date createTime;
  private Date updateTime;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getBlogId() {
    return blogId;
  }

  public void setBlogId(Integer blogId) {
    this.blogId = blogId;
  }

  public String getBlogTitle() {
    return blogTitle;
  }

  public void setBlogTitle(String blogTitle) {
    this.blogTitle = blogTitle;
  }

  public String getBlogSubUrl() {
    return blogSubUrl;
  }

  public void setBlogSubUrl(String blogSubUrl) {
    this.blogSubUrl = blogSubUrl;
  }

  public String getBlogCoverImage() {
    return blogCoverImage;
  }

  public void setBlogCoverImage(String blogCoverImage) {
    this.blogCoverImage = blogCoverImage;
  }

  public String getBlogContent() {
    return blogContent;
  }

  public void setBlogContent(String blogContent) {
    this.blogContent = blogContent;
  }

  public Integer getBlogCategoryId() {
    return blogCategoryId;
  }

  public void setBlogCategoryId(Integer blogCategoryId) {
    this.blogCategoryId = blogCategoryId;
  }

  public String getBlogCategoryName() {
    return blogCategoryName;
  }

  public void setBlogCategoryName(String blogCategoryName) {
    this.blogCategoryName = blogCategoryName;
  }

  public String getBlogTags() {
    return blogTags;
  }

  public void setBlogTags(String blogTags) {
    this.blogTags = blogTags;
  }

  public Integer getBlogStatus() {
    return blogStatus;
  }

  public void setBlogStatus(Integer blogStatus) {
    this.blogStatus = blogStatus;
  }

  public Integer getBlogViews() {
    return blogViews;
  }

  public void setBlogViews(Integer blogViews) {
    this.blogViews = blogViews;
  }

  public Integer getEnableComment() {
    return enableComment;
  }

  public void setEnableComment(Integer enableComment) {
    this.enableComment = enableComment;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
}
