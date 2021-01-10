package top.lete114.service;

import top.lete114.entity.Blog;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.List;

/**
 * @author Lete乐特
 * @createDate 2020- 11-13 9:42
 */
public interface BlogService {
    int saveBlog(Blog blog);

    List<Blog> selBlog();

    List<Blog> timeBlog();

    List<Blog> selBlogTime();

    List<Blog> timePage(Integer start,Integer limit);

    Blog BlogByName(String url);

    Blog BlogById(Integer id);

    PageResult getBlogsPage(PageQueryUtil pageUtil);

    Boolean deleteBatch(Integer[] ids);

    int getTotalBlogs();

    int ModifyArticle(Blog blog);

    List<Blog> search(String content);

    /*文章*/
    /*上一个文章*/
    Blog prev(String createTime);
    /*下一个文章*/
    Blog next(String createTime);
}
