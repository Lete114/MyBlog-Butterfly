package top.lete114.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.lete114.entity.Link;
import top.lete114.service.LinkService;
import top.lete114.util.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author Lete乐特
 * @createDate 2020- 11-14 20:16
 */
@Controller
@RequestMapping("/admin")
public class Links {

    @Resource
    private LinkService linkService;

    @GetMapping("/links/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(linkService.getBlogLinkPage(pageUtil));
    }

    /**
     * 友链添加
     */
    @RequestMapping(value = "/links/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestParam("avatar") String avatar,
                       @RequestParam("linkName") String linkName,
                       @RequestParam("linkUrl") String linkUrl,
                       @RequestParam("linkRank") Integer linkRank,
                       @RequestParam("linkDescription") String linkDescription) {

        Link link = new Link();
        link.setAvatar(avatar);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkRank(linkRank);
        link.setLinkDescription(linkDescription);
        link.setCreateTime(new Date());
        return ResultGenerator.genSuccessResult(linkService.saveLink(link));
    }

    /**
     * 详情
     */
    @GetMapping("/links/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Link link = linkService.selectById(id);
        return ResultGenerator.genSuccessResult(link);
    }

    /**
     * 友链修改
     */
    @RequestMapping(value = "/links/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestParam("linkId") Integer linkId,
                         @RequestParam("avatar") String avatar,
                         @RequestParam("linkName") String linkName,
                         @RequestParam("linkUrl") String linkUrl,
                         @RequestParam("linkRank") Integer linkRank,
                         @RequestParam("linkDescription") String linkDescription) {
        Link tempLink = linkService.selectById(linkId);
        if (tempLink == null) return ResultGenerator.genFailResult("无数据！");
        tempLink.setAvatar(avatar);
        tempLink.setLinkName(linkName);
        tempLink.setLinkUrl(linkUrl);
        tempLink.setLinkRank(linkRank);
        tempLink.setLinkDescription(linkDescription);
        return ResultGenerator.genSuccessResult(linkService.updateLink(tempLink));
    }

    /**
     * 友链删除
     */
    @RequestMapping(value = "/links/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) return ResultGenerator.genFailResult("参数异常！");
        if (linkService.deleteBatch(ids))return ResultGenerator.genSuccessResult();
        else return ResultGenerator.genFailResult("删除失败");
    }

}