package top.lete114.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.lete114.entity.User;
import top.lete114.service.UserServiceImpl;
import top.lete114.util.MD5Util;
import top.lete114.util.ValidateCodeUtil;

import javax.servlet.http.*;

/**
 * @author Lete乐特
 * @createDate 2020- 11-12 9:25
 */
@Controller
public class Login {
    @Autowired
    private UserServiceImpl us;

    /*登陆页*/
    @GetMapping({"/admin/login","/admin"})
    public String toLogin(Model mod){
        mod.addAttribute("user",us.selUser());
        return "admin/login";
    }

    /*登陆后台*/
    @PostMapping("/admin/logins")
    public String Login(String userName, String password,String verifyCode, HttpSession session,
                        RedirectAttributes redirect){

        // 转换为小写
        String jccode = session.getAttribute("JCCODE")+"";
        jccode=jccode.toLowerCase();
        verifyCode=verifyCode.toLowerCase();
        // 判断输入的验证码是否与session中的验证码一致
        if (!verifyCode.equals(jccode)) {
            redirect.addFlashAttribute("message", "验证码错误");
            return "redirect:/admin/login";
        }

        // 获取用户全部信息
        User user = us.selUser();
        // 判断用户名，密码是否正确（正确进入后台-失败返回登录页）
        if(user.getName().equals(userName)&&user.getPassword().equals(MD5Util.MD5Encode(password))){
            session.setAttribute("session",userName);
            return "redirect:/admin/index";
        }
        redirect.addFlashAttribute("message","用户名或密码错误！");
        return "redirect:/admin/login";
    }

    //返回验证码图片
    @GetMapping("/getCaptchaImg")
    public void getCaptchaImg(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        try {

            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            ValidateCodeUtil validateCode = new ValidateCodeUtil();
            // getRandomCodeImage方法会直接将生成的验证码图片写入response
            validateCode.getRandomCodeImage(request, response);
             System.out.println("session里面存储的验证码为："+session.getAttribute("JCCODE"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
