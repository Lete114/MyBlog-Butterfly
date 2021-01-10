package top.lete114.mapper;

import org.apache.ibatis.annotations.*;
import top.lete114.entity.Blog;
import top.lete114.util.PageQueryUtil;

import java.util.List;

/**
 * @author Lete乐特
 * @createDate 2020- 11-13 9:44
 */
@Mapper
public interface BlogMapper {
    int saveBlog(Blog blog);

    List<Blog> selBlog();

    Blog BlogByName(String url);

    List<Blog> selBlogTime();

    List<Blog> selBlog(Integer start,Integer limit);

    Blog BlogById(Integer id);

    int ModifyArticle(Blog blog);

    List<Blog> findBlogList(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    int getTotalBlogs(PageQueryUtil pageUtil);

    int updateBlogCategorys(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids")Integer[] ids);

    @Select("SELECT * FROM blog WHERE blogTitle LIKE CONCAT('%',#{content},'%')")
    List<Blog> search(String content);


    /*文章*/
    /*上一个文章*/
    Blog prev(String createTime);
    /*下一个文章*/
    Blog next(String createTime);

}
