package top.lete114.service;

import top.lete114.entity.Comment;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.List;
import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 12-14 17:35
 */
public interface CommentService {
    // 查询父信息
    List<Comment> CommentListf(Integer blogId);
    // 查询子信息
    List<Comment> CommentListz(Integer blogId);
    // 查询全部信息
    int CommentCount(Integer blogId);

    // 获取评论页
    PageResult getCommentsPage(PageQueryUtil pageUtil);

    // 获取评论数
    int getTotalBlogComments(Map map);

    // 审核
    Boolean checkDone(Integer[] ids);

    // 删除
    Boolean deleteBatch(Integer[] ids);

    // 回复
    Boolean reply(Integer commentId, String content);

    int reply(Comment comment);

    Comment selectByPrimaryKey(Integer id);
}
