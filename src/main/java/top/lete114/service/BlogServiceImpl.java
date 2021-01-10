package top.lete114.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import top.lete114.entity.*;
import top.lete114.mapper.*;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.*;

/**
 * @author Lete乐特
 * @createDate 2020- 11-13 9:43
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired(required=false)
    BlogMapper mapper;

    @Autowired(required=false)
    CategoryMapper categoryMapper;

    @Autowired(required=false)
    TagMapper tagMapper;

    @Override
    public List<Blog> search(String content) {
        if(StringUtils.isEmpty(content)) content = "U•ェ•*U";
        return mapper.search(content);
    }

    @Override
    public int saveBlog(Blog blog) {
        // 根据id查询分类，赋给blog
        Category category = categoryMapper.CategoryById(blog.getBlogCategoryId());
        if (category == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(category.getCategoryName());
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) return 6;
        //处理文章描述数据
        String description = blog.getDescription();
        if (StringUtils.isEmpty(description)) return 10;
        //新增的tag对象
        List<Tag> tagListForInsert = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            Tag tag = tagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                Tag tempTag = new Tag();
                tempTag.setTagName(tags[i]);
                tempTag.setCreateTime(new Date());
                tagListForInsert.add(tempTag);
            }
        }
        //添加标签
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagMapper.batchInsertBlogTag(tagListForInsert);
        }
        blog.setCreateTime(new Date());// 赋给创建时间
        blog.setUpdateTime(new Date());// 赋给修改时间
        return mapper.saveBlog(blog);
    }

    @Override
    public List<Blog> selBlog() {
        return mapper.selBlog();
    }

    @Override
    public List<Blog> timePage(Integer start, Integer limit) {
        return mapper.selBlog(start,limit);
    }

    @Override
    public List<Blog> selBlogTime() {
        return mapper.selBlogTime();
    }

    @Override
    public List<Blog> timeBlog() {
        return mapper.selBlog();
    }

    /*根据id查询*/
    @Override
    public Blog BlogById(Integer id) {
        return mapper.BlogById(id);
    }

    @Override
    public PageResult getBlogsPage(PageQueryUtil pageUtil) {
        List<Blog> blogList = mapper.findBlogList(pageUtil);
        int total = mapper.getTotalBlogs(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Blog BlogByName(String url) {
        return mapper.BlogByName(url);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return mapper.deleteBatch(ids) > 0;
    }

    @Override
    public int getTotalBlogs() {
        return mapper.getTotalBlogs(null);
    }

    @Override
    public int ModifyArticle(Blog blog) {
        // 根据id查询分类，赋给blog
        Category category = categoryMapper.CategoryById(blog.getBlogCategoryId());
        if (category == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(category.getCategoryName());
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) return 6;
        //处理文章描述数据
        String description = blog.getDescription();
        if (description.equals(null)||description==null) return 10;
        //新增的tag对象
        List<Tag> tagListForInsert = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            Tag tag = tagMapper.selectByTagName(tags[i]);
            if (tag == null) {
                //不存在就新增
                Tag tempTag = new Tag();
                tempTag.setTagName(tags[i]);
                tempTag.setCreateTime(new Date());
                tagListForInsert.add(tempTag);
            }
        }
        //添加标签
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagMapper.batchInsertBlogTag(tagListForInsert);
        }
        blog.setUpdateTime(new Date());// 修改时间
        return mapper.ModifyArticle(blog);
    }


    /*文章*/
    /*上一个文章*/
    @Override
    public Blog prev(String createTime) {
        return mapper.prev(createTime);
    }

    /*下一个文章*/
    @Override
    public Blog next(String createTime) {
        return mapper.next(createTime);
    }
}
