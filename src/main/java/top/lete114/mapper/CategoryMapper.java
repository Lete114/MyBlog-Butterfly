package top.lete114.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lete114.entity.Blog;
import top.lete114.entity.Category;
import top.lete114.util.PageQueryUtil;

import java.util.List;

/**
 * @author Lete乐特
 * @createDate 2020- 11-13 11:27
 */
@Mapper
public interface CategoryMapper {
    // 查询全部信息
    List<Category> selCategory();

    List<Blog> selectByBlogCategoryName(String name);

    // 根据id查询
    Category CategoryById(Integer id);

    List<Category> findCategoryList(PageQueryUtil pageUtil);

    int getTotalCategories(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    int modifyCategorys(Category category);

    /*添加*/
    int insertCategory(Category category);
    Category selectByCategoryName(String name);

    /*查询记录数*/
    int getTotalCategories();
}
