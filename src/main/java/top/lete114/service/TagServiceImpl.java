package top.lete114.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lete114.entity.Blog;
import top.lete114.entity.Tag;
import top.lete114.mapper.TagMapper;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.List;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 18:29
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired(required = false)
    TagMapper mapper;

    @Override
    public PageResult getBlogTagPage(PageQueryUtil pageUtil) {
        List<Tag> tags = mapper.findTagList(pageUtil);
        int total = mapper.getTotalTags(pageUtil);
        PageResult pageResult = new PageResult(tags, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalTags() {
        return mapper.getTotalTags(null);
    }

    @Override
    public List<Tag> selTag() {
        return mapper.selTag();
    }

    @Override
    public Tag selectByTagName(String tagName) {
        return mapper.selectByTagName(tagName);
    }

    @Override
    public List<Blog> likeByTagName(String tagName) {
        return mapper.likeByTagName(tagName);
    }

    @Override
    public Boolean saveTag(String tagName) {
        Tag temp = mapper.selectByTagName(tagName);
        if (temp == null) {
            Tag blogTag = new Tag();
            blogTag.setTagName(tagName);
            return mapper.insertSelective(blogTag) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        //删除tag
        return mapper.deleteBatch(ids) > 0;
    }
}
