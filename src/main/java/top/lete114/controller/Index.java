package top.lete114.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;
import top.lete114.entity.*;
import top.lete114.service.*;
import top.lete114.util.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Lete乐特
 * @createDate 2020- 11-05 9:42
 */

@Controller
public class Index {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    BlogServiceImpl blogService;
    @Autowired
    TagServiceImpl tagService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    LinkServiceImpl linkService;
    @Autowired
    CommentServiceImpl commentService;

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
    /*搜索*/
    @PostMapping("/search")
    @ResponseBody
    public Map<String, Object> search(String Content){
        List<Blog> search = blogService.search(Content);
        Map<String, Object> map = new HashMap<String, Object>();
        for (Blog blog:search){
            blog.setBlogContent(DelHtmlTagsUtil.delHtmlTags(MarkDownUtil.mdToHtml(blog.getBlogContent())));
        }
        map.put("blog",search);
        return map;
    }



    /*首页*/
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){

        List<Tag> tags = tagService.selTag();

        // 分页处理
        PageHelper.startPage(pageNum,5);
        List<Blog> list = blogService.selBlog();
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(list);
        model.addAttribute("pageInfo",pageInfo);

        model.addAttribute("date",this.date());
        model.addAttribute("user",userService.selUser());
        model.addAttribute("tags",tags);
        model.addAttribute("bloglist",blogService.selBlog());
        model.addAttribute("timeblog",blogService.timeBlog());
        model.addAttribute("blogCount",blogService.getTotalBlogs());
        model.addAttribute("tagCount",tagService.getTotalTags());
        model.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "index";
    }


    /*根据标签查询文章*/
    @GetMapping("/tags/{tags}")
    public String toTag(Model mod, @PathVariable("tags") String tags){
        Tag tag = tagService.selectByTagName(tags);

        mod.addAttribute("date",this.date());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("tag",tag);
        mod.addAttribute("list",tagService.likeByTagName(tags));
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "tags";
    }

    /*标签查询*/
    @GetMapping("/tags")
    public String toTags(Model mod){
        List<Tag> tags = tagService.selTag();
        mod.addAttribute("date",this.date());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("tags",tags);
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "tag";
    }


    /*根据归档查询文章*/
    @GetMapping("/archive")
    public String toArchives(Model mod){
        mod.addAttribute("date",this.date());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("time",blogService.selBlogTime());
        mod.addAttribute("count",blogService.getTotalBlogs());
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "archives";
    }

    /*根据标签查询文章*/
    @GetMapping("/categories/{categories}")
    public String toCategorie(Model mod, @PathVariable("categories") String categories){

        mod.addAttribute("date",this.date());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("cat",categoryService.selectByCategoryName(categories));
        mod.addAttribute("list",categoryService.selectByBlogCategoryName(categories));
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "categories";
    }

    /*根据标签查询文章*/
    @GetMapping("/categories")
    public String toCategories(Model mod){
        mod.addAttribute("date",this.date());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("cat",categoryService.selCategory());
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "categorie";
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

    /*友链*/
    @GetMapping("/link")
    public String toLink(Model mod) throws IOException, ParseException {
        User user = userService.selUser();
        mod.addAttribute("date",this.date());
        mod.addAttribute("list",linkService.linkRank());
        mod.addAttribute("user",user);
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());

        // 博主邮箱转头像
        if(PatternUtil.isQQEmail(user.getEmail())){
            // 获取@之前的内容(获取QQ号)
            Integer qq = Integer.valueOf(user.getEmail().substring(0, user.getEmail().indexOf("@")));
            user.setEmail(ApiUtil.getHeadImage(qq));
        }else{
            user.setEmail(ApiUtil.getGravatar(MD5Util.MD5Encode(user.getEmail())));
        }
        mod.addAttribute("user",user);
        // 获取父评论
        List<Comment> listf = commentService.CommentListf(114);
        mod.addAttribute("commentsf",Mail(listf));
        // 获取子评论
        List<Comment> listz = commentService.CommentListz(114);
        mod.addAttribute("commentsz",Mail(listz));

        // 获取父评论总数
        mod.addAttribute("count",commentService.CommentCount(114));
        return "link";
    }

    /*关于*/
    @GetMapping("/about")
    public String toAbout(Model mod){
        User user = userService.selUser();

        mod.addAttribute("date",this.date());
        mod.addAttribute("content",MarkDownUtil.mdToHtml(user.getAbout()));
        mod.addAttribute("user",user);
        mod.addAttribute("blogCount",blogService.getTotalBlogs());
        mod.addAttribute("tagCount",tagService.getTotalTags());
        mod.addAttribute("categoriesCount",categoryService.getTotalCategories());
        return "about";
    }




}
