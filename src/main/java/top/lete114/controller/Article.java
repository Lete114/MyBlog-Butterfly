package top.lete114.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.lete114.entity.*;
import top.lete114.service.*;
import top.lete114.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lete乐特
 * @createDate 2020- 12-17 17:20
 */
@Controller
public class Article {


    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private CommentServiceImpl commentService;
    @Autowired
    private JavaMailSender mailSender;
    // 获取配置文件内的发送邮箱
    @Value("${spring.mail.username}")
    private String from;


    private String date(){
        User user = userService.selUser();
        /*判断网站运行时间是否与今年相同
            true:赋值今年年份
            false:赋值网站运行时间+今年年份 如：2018-2020
        */
        String date;
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        if(df.format(user.getRunning_time()).equals(df.format(new Date())))return date = df.format(new Date());
        else return date = df.format(user.getRunning_time())+" - "+df.format(new Date());
    }

        // 获取Mail 根据Mail获取评论头像
        private List<Comment> Mail(List<Comment> list) throws IOException, ParseException {
            for (Comment comment: list){
                // 判断Mail   true: QQavatar  false: Gravatar
                if(PatternUtil.isQQEmail(comment.getEmail())){
                    // 获取@之前的内容(获取QQ号)
                    Integer qq = Integer.valueOf(comment.getEmail().substring(0, comment.getEmail().indexOf("@")));
                    comment.setEmail(ApiUtil.getHeadImage(qq));
                }else{
                    comment.setEmail(ApiUtil.getGravatar(MD5Util.MD5Encode(comment.getEmail())));
                }

                // 将MarkDown转换为html
                comment.setContent(MarkDownUtil.mdToHtml(comment.getContent()));
            }
            return list;
        }

    @GetMapping("/article/{url}")
    public String toArticle(Model mod, @PathVariable("url") String url) throws IOException, ParseException {

        Blog blog = blogService.BlogByName(url);
        mod.addAttribute("date",this.date());
        mod.addAttribute("blog",blog);
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());

        // 博主邮箱转头像
        User user = userService.selUser();
        if(PatternUtil.isQQEmail(user.getEmail())){
            // 获取@之前的内容(获取QQ号)
            Integer qq = Integer.valueOf(user.getEmail().substring(0, user.getEmail().indexOf("@")));
            user.setEmail(ApiUtil.getHeadImage(qq));
        }else{
            user.setEmail(ApiUtil.getGravatar(MD5Util.MD5Encode(user.getEmail())));
        }
        mod.addAttribute("user",user);

        // 获取父评论
        List<Comment> listf = commentService.CommentListf(blog.getBlogId());
        mod.addAttribute("commentsf",Mail(listf));
        // 获取子评论
        List<Comment> listz = commentService.CommentListz(blog.getBlogId());
        mod.addAttribute("commentsz",Mail(listz));

        // 获取父评论总数
        mod.addAttribute("count",commentService.CommentCount(blog.getBlogId()));

        /*上一篇文章 and 下一篇文章*/
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mod.addAttribute("prev",blogService.prev(df.format(blog.getCreateTime())));
        mod.addAttribute("next",blogService.next(df.format(blog.getCreateTime())));
        return "article";
    }

    // 评论
    @PostMapping("/reply")
    @ResponseBody
    public String reply(Comment comment, HttpServletRequest request, String verifyCode, HttpSession session) throws MessagingException {
        comment.setCommentatorIP(request.getRemoteHost());
        if(!PatternUtil.isEmail(comment.getEmail())) return "邮箱不规范，请重新输入";
        if(!PatternUtil.isURL(comment.getLink())) return "网址不规范，请重新输入";

        // 转换为小写
        String jccode = session.getAttribute("JCCODE")+"";
        jccode=jccode.toLowerCase();
        verifyCode=verifyCode.toLowerCase();
        // 判断输入的验证码是否与session中的验证码一致
        if (!verifyCode.equals(jccode)) {
            return "验证码错误";
        }

        if(commentService.reply(comment)>0){
            User user = userService.selUser();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setSubject("您的「"+user.getTitle()+"」上有新的评论啦！");
            message.setText("<style> #comment-content img { max-width: 25px; vertical-align: middle; margin: 0 1px; display: inline-block; } </style><tbody> <tr> <td> <div id=\"comment-body\" style=\"border-radius: 10px 10px 10px 10px; font-size: 13px; color: #555555; width: 666px; font-family: 'Century Gothic', 'Trebuchet MS', 'Hiragino Sans GB', 微软雅黑, 'Microsoft Yahei', Tahoma, Helvetica, Arial, 'SimSun', sans-serif; margin: 50px auto; border: 1px solid #eee; max-width: 100%; background: #ffffff repeating-linear-gradient(-45deg, #fff, #fff 1.125rem, transparent 1.125rem, transparent 2.25rem); box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\"> <div id=\"comment-head\" style=\"width: 100%; background: #49BDAD; color: #ffffff; border-radius: 10px 10px 0 0; background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244)); background-image: -webkit-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244)); height: 66px;\"> <p id=\"head-text\" style=\"font-size: 15px; word-break: break-all; padding: 23px 32px; margin: 0; background-color: hsla(0, 0%, 100%, .4); border-radius: 10px 10px 0 0;\"> 您的「"+user.getTitle()+"」上有新的评论啦！ </p> </div> <div id=\"comment-main\" style=\"margin: 40px auto;width: 90%\"> <p style=\"font-size: 16px;\">评论人：<a style=\"color: #12addb;text-decoration: none !important;\" href=\""+comment.getLink()+"\" target=\"_blank\">@"+comment.getNick()+"</a> </p> <span>评论内容：</span> <div id=\"comment-content\" style=\"display: inline-block;background: #fafafa repeating-linear-gradient(-45deg, #fff, #fff 1.125rem, transparent 1.125rem, transparent 2.25rem); box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15); margin: 20px 0px; padding: 15px; border-radius: 5px; font-size: 14px; color: #555555;\"> "+comment.getContent()+" </div> <p style=\"font-size: 16px;\">评论链接：<a style=\"color: #12addb;text-decoration: none !important;\" href=\""+comment.getCommentUrl()+"\" target=\"_blank\">"+comment.getCommentUrl()+"</a></p> </div> </div> </td> </tr> </tbody>",true);
            // 获取配置文件内的发送邮箱
            message.setFrom(from);
            message.setTo(user.getEmail());
            mailSender.send(mimeMessage);
            return "true";
        }
        return "false";
    }
    // 评论获取qq信息
    @PostMapping("/obtainQQInfo")
    @ResponseBody
    public Map<String,Object> obtainQQInfo(String mail) throws IOException {
        Map<String,Object> map = new HashMap<>();
        if(PatternUtil.isQQEmail(mail)){
            Integer qq = Integer.valueOf(mail.substring(0, mail.indexOf("@")));
            map.put("nick", ApiUtil.getName(qq));
            return map;
        }
        return null;
    }


    // 根据文章id获取markdown
    @PostMapping("/ObtainMarkDown")
    @ResponseBody
    public String ObtainMarkDown(Integer blogId) {
        Blog blog = blogService.BlogById(blogId);
        return blog.getBlogContent();
    }
}
