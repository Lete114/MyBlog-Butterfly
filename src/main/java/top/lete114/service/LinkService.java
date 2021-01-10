package top.lete114.service;

import org.apache.ibatis.annotations.Mapper;
import top.lete114.entity.Link;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 20:00
 */
public interface LinkService {
    PageResult getBlogLinkPage(PageQueryUtil pageUtil);

    int getTotalLinks();

    Boolean saveLink(Link link);

    Link selectById(Integer id);

    Boolean updateLink(Link tempLink);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回友链页面所需的所有数据
     *
     * @return
     */
    Map<String, List<Link>> getLinksForLinkPage();

    List<Link> linkRank();
}
