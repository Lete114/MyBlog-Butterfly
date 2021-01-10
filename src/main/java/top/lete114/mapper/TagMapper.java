package top.lete114.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lete114.entity.Blog;
import top.lete114.entity.Tag;
import top.lete114.util.PageQueryUtil;

import java.util.List;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 18:28
 */
@Mapper
public interface TagMapper {

    int insert(Tag record);

    List<Blog> likeByTagName(String tagName);

    int insertSelective(Tag record);

    Tag selectByTagName(String tagName);

    List<Tag> findTagList(PageQueryUtil pageUtil);

    List<Tag> selTag();

    int getTotalTags(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    int batchInsertBlogTag(List<Tag> tagList);

}
