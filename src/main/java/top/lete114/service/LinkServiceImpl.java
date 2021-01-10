package top.lete114.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lete114.entity.Link;
import top.lete114.mapper.LinkMapper;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 20:01
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired(required = false)
    LinkMapper mapper;

    @Override
    public PageResult getBlogLinkPage(PageQueryUtil pageUtil) {
        List<Link> links = mapper.findLinkList(pageUtil);
        int total = mapper.getTotalLinks(pageUtil);
        PageResult pageResult = new PageResult(links, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalLinks() {
        return mapper.getTotalLinks(null);
    }


    @Override
    public Boolean saveLink(Link link) {
        return mapper.insertSelective(link) > 0;
    }

    @Override
    public Link selectById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }


    @Override
    public Boolean updateLink(Link tempLink) {
        return mapper.updateByPrimaryKeySelective(tempLink) > 0;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return mapper.deleteBatch(ids) > 0;
    }

    @Override
    public Map<String, List<Link>> getLinksForLinkPage() {
        //获取所有链接数据
        List<Link> links = mapper.findLinkList(null);
        Map<String, List<Link>> linksMap = links.stream().collect(Collectors.groupingBy(Link::getAvatar));
        return linksMap;
    }

    @Override
    public List<Link> linkRank() {
        return mapper.linkRank();
    }
}
