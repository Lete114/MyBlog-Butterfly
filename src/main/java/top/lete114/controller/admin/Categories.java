package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lete114.service.*;
import top.lete114.util.*;

import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 12:58
 */
@Controller
@RequestMapping("/admin")
public class Categories {
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    BlogServiceImpl blogService;
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/categories/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(categoryService.getBlogCategoryPage(pageUtil));
    }

    /**
     * 分类添加
     */
    @PostMapping("/categories/save")
    @ResponseBody
    public Result save(@RequestParam("categoryName") String categoryName) {
        if (StringUtils.isEmpty(categoryName)) return ResultGenerator.genFailResult("请输入分类名称！");
        if (categoryService.saveCategory(categoryName)) return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("分类名称重复");
    }

    /**
     * 分类删除
     */
    @PostMapping("/categories/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) return ResultGenerator.genFailResult("参数异常！");
        if (categoryService.deleteBatch(ids)) return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("删除失败");
    }

    /**
     * 分类修改
     */
    @PostMapping("/categories/update")
    @ResponseBody
    public Result update(@RequestParam("categoryId") Integer categoryId,
                         @RequestParam("categoryName") String categoryName) {
        if (StringUtils.isEmpty(categoryName)) return ResultGenerator.genFailResult("请输入分类名称！");
        if (categoryService.updateCategory(categoryId, categoryName)) return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("分类名称重复");
    }

}
