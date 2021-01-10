package top.lete114.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.lete114.entity.Comment;
import top.lete114.entity.User;
import top.lete114.mapper.CommentMapper;
import top.lete114.mapper.UserMapper;
import top.lete114.util.PageQueryUtil;
import top.lete114.util.PageResult;

import java.util.*;

/**
 * @author Lete乐特
 * @createDate 2020- 12-14 17:36
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired(required=false)
    CommentMapper mapper;
    @Autowired(required=false)
    UserMapper userMapper;



    @Override
    public List<Comment> CommentListf(Integer blogId) {
        return mapper.CommentListf(blogId);
    }

    @Override
    public List<Comment> CommentListz(Integer blogId) {
        return mapper.CommentListz(blogId);
    }

    @Override
    public PageResult getCommentsPage(PageQueryUtil pageUtil) {
        List<Comment> comments = mapper.findBlogCommentList(pageUtil);
        int total = mapper.getTotalBlogComments(pageUtil);
        PageResult pageResult = new PageResult(comments, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override // 获取评论数
    public int getTotalBlogComments(Map map) {
        return mapper.getTotalBlogComments(null);
    }

    @Override // 审核
    public Boolean checkDone(Integer[] ids) {
        return mapper.checkDone(ids) > 0;
    }

    @Override // 删除
    public Boolean deleteBatch(Integer[] ids) {
        return mapper.deleteBatch(ids) > 0;
    }

    @Override // 博主回复
    public Boolean reply(Integer commentId, String content) {
        Comment comment = mapper.selectByPrimaryKey(commentId);
        //Comment不为空且状态为已审核，则继续后续操作
        if (comment != null && comment.getCommentStatus().intValue() == 1) {
            User user = userMapper.selUser();
            comment.setContent(content);
            comment.setCreateTime(new Date());
            comment.setCommentatorIP("127.0.0.1");
            comment.setEmail(user.getEmail());
            comment.setLink("/");
            comment.setNick(user.getAuthor());
            if(comment.getIsReply()==0){
                comment.setIsReply(commentId);
            }
            return mapper.reply(comment) > 0;
        }
        return false;
    }

    // 访客回复
    @Override
    public int reply(Comment comment) {
        // 判断是否是父评论
        if(comment.getIsReply()==null)comment.setIsReply(0);
        if(comment.getCommentId()!=0)comment.setIsReply(comment.getCommentId());
        comment.setCreateTime(new Date());
        comment.setCommentStatus(0);
        return mapper.reply(comment);
    }

    @Override
    public int CommentCount(Integer blogId) {
        return mapper.CommentCount(blogId);
    }

    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }
}
