package top.lete114.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.lete114.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 12-14 17:33
 */
@Mapper
public interface CommentMapper {
    // 查询父评论
    @Select("select * from comment where blogId=#{blogId} AND isReply=0 AND commentStatus=1 ORDER BY createTime DESC")
    List<Comment> CommentListf(Integer blogId);
    // 查询子评论
    @Select("select * from comment where blogId=#{blogId} AND isReply!=0 AND commentStatus=1 ORDER BY createTime DESC")
    List<Comment> CommentListz(Integer blogId);
    // 根据blogId查询
    @Select("select count(1) from comment where blogId=#{blogId} AND commentStatus=1 and isReply=0")
    int CommentCount(Integer blogId);

    List<Comment> findBlogCommentList(Map map);

    int getTotalBlogComments(Map map);

    int checkDone(Integer[] ids);

    int deleteBatch(Integer[] ids);

    // 根据id查询
    Comment selectByPrimaryKey(Integer commentId);

    int reply(Comment comment);
}
