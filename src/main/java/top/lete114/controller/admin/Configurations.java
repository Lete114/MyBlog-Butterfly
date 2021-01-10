package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.lete114.service.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lete乐特
 * @createDate 2020- 11-12 20:15
 */
@Controller
@RequestMapping("/admin")
public class Configurations {

    @Autowired
    private UserServiceImpl userService;

    /*修改网站信息*/
    @PostMapping("/modifywebsite")
    @ResponseBody
    public String ModifyWebSite(
            String title,
            String siteBackground,
            String description,
            String subtitle,
            String running_time,
            String notice,
            String favicon) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(running_time);
        int result = userService.ModifyWebSite(title,siteBackground,description,subtitle,favicon,date,notice);
        if(result>0)return "success";
        return "error";
    }
    /*修改个人信息*/
    @PostMapping("/modifyperson")
    @ResponseBody
    public String ModifyPerson(
            String avatar,
            String author,
            String email,
            String icp){
        int result = userService.ModifyPerson(avatar,author,email,icp);
        if(result>0)return "success";
        return "error";
    }
}
