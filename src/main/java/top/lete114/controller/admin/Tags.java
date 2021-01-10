package top.lete114.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lete114.service.TagServiceImpl;
import top.lete114.util.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 18:25
 */
@Controller
@RequestMapping("/admin")
public class Tags {

    @Resource
    private TagServiceImpl tagService;

    @GetMapping("/tags/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(tagService.getBlogTagPage(pageUtil));
    }


    @PostMapping("/tags/save")
    @ResponseBody
    public Result save(@RequestParam("tagName") String tagName) {
        if (StringUtils.isEmpty(tagName)) return ResultGenerator.genFailResult("参数异常！");
        if (tagService.saveTag(tagName)) return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("标签名称重复");
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) return ResultGenerator.genFailResult("参数异常！");
        if (tagService.deleteBatch(ids)) return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("有关联数据请勿强行删除");
    }
}
