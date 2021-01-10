package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lete114.entity.*;
import top.lete114.service.*;
import top.lete114.util.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 9:42
 */
@Controller
@RequestMapping("/admin")
public class Blogs {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    UserServiceImpl userService;

    /*查询全部文章 and 分页*/
    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(blogService.getBlogsPage(pageUtil));
    }

    /*删除文章*/
    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) return ResultGenerator.genFailResult("参数异常！");
        if (blogService.deleteBatch(ids)) return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("删除失败");
    }

    /*修改文章(获取修改信息)*/
    @GetMapping("/edit/{id}")
    public String toModify(Model mod, @PathVariable("id") Integer id){
        Blog blog = blogService.BlogById(id);
        List<Category> list = categoryService.selCategory();
        mod.addAttribute("list",list);
        mod.addAttribute("blog",blog);
        User user = userService.selUser();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        String date = df.format(user.getRunning_time())+" - "+df.format(new Date());
        mod.addAttribute("date",date);
        mod.addAttribute("user",user);
        return "admin/edit";
    }
    /*修改文章*/
    @PostMapping("/blogs/modify")
    @ResponseBody
    public String Modify(Blog blog){
        blog.setBlogContent(blog.getBlogContent().replaceAll("[^\\u0000-\\uFFFF]", ""));
        int result = blogService.ModifyArticle(blog);
        /*标签数量超过6个标签是提示*/
        if(result==6)return "6";
        /*文章描述不能为空*/
        if(result==10)return "description";
        if(result>0)return "success";
        return "error";
    }
    /*添加文章*/
    @PostMapping("/blogs/save")
    @ResponseBody
    public String SaveBlog(Blog blog){
        blog.setBlogContent(blog.getBlogContent().replaceAll("[^\\u0000-\\uFFFF]", ""));
        int result = blogService.saveBlog(blog);
        /*标签数量超过6个标签是提示*/
        if(result==6)return "6";
        /*文章描述不能为空*/
        if(result==10)return "description";
        if(result>0)return "success";
        return "error";
    }
}
