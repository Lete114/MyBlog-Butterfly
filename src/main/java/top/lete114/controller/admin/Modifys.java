package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.lete114.service.UserServiceImpl;
import top.lete114.util.MD5Util;

/**
 * @author Lete乐特
 * @createDate 2020- 11-12 20:34
 */
@Controller
@RequestMapping("/admin")
public class Modifys {

    @Autowired
    UserServiceImpl userService;

    // 修改基础信息
    @PostMapping("/modifybasic")
    @ResponseBody
    public String ModifyBasic(String author,String name){
        int result = userService.ModifyBasic(name, author);
        if(result>0) return "success";
        return "error";
    }

    // 修改密码
    @PostMapping("/modifypassword")
    @ResponseBody
    public String ModifyPassword(String newPassword,String originalPassword){

        if(userService.selUser().getPassword().equals(MD5Util.MD5Encode(originalPassword))){
            int result = userService.ModifyPassword(MD5Util.MD5Encode(newPassword));
            if(result>0)return "success";
        }
        return "error";
    }

}
