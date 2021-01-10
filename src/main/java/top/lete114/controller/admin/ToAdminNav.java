package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.lete114.entity.User;
import top.lete114.service.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lete乐特
 * @createDate 2020- 11-11 13:44
 */
@Controller
@RequestMapping("/admin")
public class ToAdminNav {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private TagServiceImpl tagService;
    @Autowired
    private LinkServiceImpl linkService;
    @Autowired
    private CommentServiceImpl commentService;

    public String date(){
        User user = userService.selUser();
        /*判断网站运行时间是否与今年相同
            true:赋值今年年份
            false:赋值网站运行时间+今年年份 如：2018-2020
        */
        String date;
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        if(df.format(user.getRunning_time()).equals(df.format(new Date()))){
            return date = df.format(new Date());
        }else {
            return date = df.format(user.getRunning_time())+" - "+df.format(new Date());
        }
    }

    /*后台首页*/
    @GetMapping({"/index.html","/index"})
    public String toAdmin(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("blog",blogService.getTotalBlogs());
        mod.addAttribute("category",categoryService.getTotalCategories());
        mod.addAttribute("tag",tagService.getTotalTags());
        mod.addAttribute("link",linkService.getTotalLinks());
        mod.addAttribute("comment",commentService.getTotalBlogComments(null));
        mod.addAttribute("date",this.date());
        return "admin/index";
    }

    /*添加文章*/
    @GetMapping("/edit")
    public String toEdit(Model mod){
        mod.addAttribute("list",categoryService.selCategory());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/edit";
    }

    /*文章管理*/
    @GetMapping("/blogs")
    public String toBlogs(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/blogs";
    }

    /*评论管理*/
    @GetMapping("/comments")
    public String toComments(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/comments";
    }

    /*分类管理*/
    @GetMapping("/categories")
    public String toCategories(Model mod){
        mod.addAttribute("categories",categoryService.selCategory());
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/categories";
    }

    /*标签管理*/
    @GetMapping("/tags")
    public String toTags(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/tags";
    }

    /*友链管理*/
    @GetMapping("/links")
    public String toLinks(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/links";
    }

    /*编辑关于我*/
    @GetMapping("/about")
    public String toAbout(Model mod){
        User user = userService.selUser();
        mod.addAttribute("user",user);
        mod.addAttribute("date",this.date());
        return "admin/about";
    }

    /*编辑关于我*/
    @PostMapping("/saveAbout")
    @ResponseBody
    public String saveAbout(String Content){
        Content = Content.replaceAll("[^\\u0000-\\uFFFF]", "");
        return userService.saveAbout(Content);
    }

    /*系统配置*/
    @GetMapping("/configurations")
    public String toConfigurations(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/configurations";
    }

    /*修改密码*/
    @GetMapping("/modifys")
    public String toModifys(Model mod){
        mod.addAttribute("user",userService.selUser());
        mod.addAttribute("date",this.date());
        return "admin/modifys";
    }

    /*安全退出*/
    @GetMapping("/logout")
    public String toLogout(Model mod, HttpSession session, RedirectAttributes attributes){
        session.removeAttribute("session");
        attributes.addFlashAttribute("message","注销成功！");
        mod.addAttribute("user",userService.selUser());
        return "redirect:/admin/login";
    }
}
