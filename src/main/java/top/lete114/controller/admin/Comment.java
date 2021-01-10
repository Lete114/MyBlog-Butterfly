package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lete114.entity.User;
import top.lete114.service.CommentServiceImpl;
import top.lete114.service.UserServiceImpl;
import top.lete114.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 12-14 16:50
 */
@Controller
@RequestMapping("/admin")
public class Comment {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private JavaMailSender mailSender;
    // 获取配置文件内的发送邮箱
    @Value("${spring.mail.username}")
    private String from;

    @GetMapping("/comments/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(commentService.getCommentsPage(pageUtil));
    }

    // 审核
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result checkDone(@RequestBody Integer[] ids) throws MessagingException {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }

        // 审核成功
        if (commentService.checkDone(ids)){
            for (Integer commentId:ids){
                // 根据博主审核的评论id查询评论
                top.lete114.entity.Comment comment = commentService.selectByPrimaryKey(commentId);
                // 如果不等于0(子评论)
                if(comment.getIsReply()!=0){
                    // 根据子评论的isReply查询父评论邮箱
                    top.lete114.entity.Comment reply = commentService.selectByPrimaryKey(comment.getIsReply());
                    User user = userService.selUser();
                    MimeMessage mimeMessage = mailSender.createMimeMessage();
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                    message.setSubject("您的「"+user.getTitle()+"」上有新的评论啦！");
                    message.setText("<style> #comment-content img { max-width: 25px; vertical-align: middle; margin: 0 1px; display: inline-block; } </style><tbody> <tr> <td> <div id=\"comment-body\" style=\"border-radius: 10px 10px 10px 10px; font-size: 13px; color: #555555; width: 666px; font-family: 'Century Gothic', 'Trebuchet MS', 'Hiragino Sans GB', 微软雅黑, 'Microsoft Yahei', Tahoma, Helvetica, Arial, 'SimSun', sans-serif; margin: 50px auto; border: 1px solid #eee; max-width: 100%; background: #ffffff repeating-linear-gradient(-45deg, #fff, #fff 1.125rem, transparent 1.125rem, transparent 2.25rem); box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\"> <div id=\"comment-head\" style=\"width: 100%; background: #49BDAD; color: #ffffff; border-radius: 10px 10px 0 0; background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244)); background-image: -webkit-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244)); height: 66px;\"> <p id=\"head-text\" style=\"font-size: 15px; word-break: break-all; padding: 23px 32px; margin: 0; background-color: hsla(0, 0%, 100%, .4); border-radius: 10px 10px 0 0;\"> 您的「"+user.getTitle()+"」上有新的评论啦！ </p> </div> <div id=\"comment-main\" style=\"margin: 40px auto;width: 90%\"> <p style=\"font-size: 16px;\">评论人：<a style=\"color: #12addb;text-decoration: none !important;\" href=\""+comment.getLink()+"\" target=\"_blank\">@"+comment.getNick()+"</a> </p> <span>评论内容：</span> <div id=\"comment-content\" style=\"display: inline-block;background: #fafafa repeating-linear-gradient(-45deg, #fff, #fff 1.125rem, transparent 1.125rem, transparent 2.25rem); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15); margin: 20px 0px; padding: 15px; border-radius: 5px; font-size: 14px; color: #555555;\"> "+comment.getContent()+" </div> <p style=\"font-size: 16px;\">评论链接：<a style=\"color: #12addb;text-decoration: none !important;\" href=\""+comment.getCommentUrl()+"\" target=\"_blank\">"+comment.getCommentUrl()+"</a></p> </div> </div> </td> </tr> </tbody>",true);
                    message.setFrom(from);
                    // 获得父评论并将子评论内容发送给父评论
                    message.setTo(reply.getEmail());
                    mailSender.send(mimeMessage);
                }
            }
            return ResultGenerator.genSuccessResult();
        }
        else return ResultGenerator.genFailResult("审核失败");
    }

    @PostMapping("/comments/reply")
    @ResponseBody
    public Result checkDone(@RequestParam("commentId") Integer commentId,
                            @RequestParam("content") String content, HttpServletRequest request) throws MessagingException {
        if (commentId == null || commentId < 1 || StringUtils.isEmpty(content)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (commentService.reply(commentId, content)) {
            User user = userService.selUser();
            top.lete114.entity.Comment comment = commentService.selectByPrimaryKey(commentId);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setSubject("您的「"+user.getTitle()+"」上有新的评论啦！");
            message.setText("<style> #comment-content img { max-width: 25px; vertical-align: middle; margin: 0 1px; display: inline-block; } </style><tbody> <tr> <td> <div id=\"comment-body\" style=\"border-radius: 10px 10px 10px 10px; font-size: 13px; color: #555555; width: 666px; font-family: 'Century Gothic', 'Trebuchet MS', 'Hiragino Sans GB', 微软雅黑, 'Microsoft Yahei', Tahoma, Helvetica, Arial, 'SimSun', sans-serif; margin: 50px auto; border: 1px solid #eee; max-width: 100%; background: #ffffff repeating-linear-gradient(-45deg, #fff, #fff 1.125rem, transparent 1.125rem, transparent 2.25rem); box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\"> <div id=\"comment-head\" style=\"width: 100%; background: #49BDAD; color: #ffffff; border-radius: 10px 10px 0 0; background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244)); background-image: -webkit-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244)); height: 66px;\"> <p id=\"head-text\" style=\"font-size: 15px; word-break: break-all; padding: 23px 32px; margin: 0; background-color: hsla(0, 0%, 100%, .4); border-radius: 10px 10px 0 0;\"> 您的「"+user.getTitle()+"」上有新的评论啦！ </p> </div> <div id=\"comment-main\" style=\"margin: 40px auto;width: 90%\"> <p style=\"font-size: 16px;\">评论人：<a style=\"color: #12addb;text-decoration: none !important;\" href=\"https://"+request.getServerName()+"\" target=\"_blank\">@"+user.getAuthor()+"</a> </p> <span>评论内容：</span> <div id=\"comment-content\" style=\"display: inline-block;background: #fafafa repeating-linear-gradient(-45deg, #fff, #fff 1.125rem, transparent 1.125rem, transparent 2.25rem); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15); margin: 20px 0px; padding: 15px; border-radius: 5px; font-size: 14px; color: #555555;\"> "+content+" </div> <p style=\"font-size: 16px;\">评论链接：<a style=\"color: #12addb;text-decoration: none !important;\" href=\""+comment.getCommentUrl()+"\" target=\"_blank\">"+comment.getCommentUrl()+"</a></p> </div> </div> </td> </tr> </tbody>",true);
            message.setFrom(from);
            // 根据博主回复的评论id获取评论邮箱
            message.setTo(comment.getEmail());
            mailSender.send(mimeMessage);
            return ResultGenerator.genSuccessResult();
        }
        else return ResultGenerator.genFailResult("回复失败");
    }


    @PostMapping("/comments/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (commentService.deleteBatch(ids)) return ResultGenerator.genSuccessResult();

        else return ResultGenerator.genFailResult("刪除失败");
    }
}
