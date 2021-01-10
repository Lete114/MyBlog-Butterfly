package top.lete114.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.lete114.entity.Link;
import top.lete114.util.PageQueryUtil;

import java.util.List;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 19:59
 */
@Mapper
public interface LinkMapper {

    int deleteByPrimaryKey(Integer linkId);

    int insert(Link record);

    int insertSelective(Link record);

    Link selectByPrimaryKey(Integer linkId);

    int updateByPrimaryKeySelective(Link record);

    int updateByPrimaryKey(Link record);

    List<Link> findLinkList(PageQueryUtil pageUtil);

    int getTotalLinks(PageQueryUtil pageUtil);

    int deleteBatch(Integer[] ids);

    List<Link> linkRank();
}
