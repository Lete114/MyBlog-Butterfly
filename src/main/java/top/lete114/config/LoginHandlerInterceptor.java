package top.lete114.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lete乐特
 * @createDate 2020- 11-11 15:42
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Object loginUser = request.getSession().getAttribute("session");
        if (null == loginUser) {
            request.setAttribute("message", "请先登陆！");
            request.getRequestDispatcher(request.getContextPath() + "/admin/login")
                    .forward(request,response);
            return false;
        } else {
            request.removeAttribute("message");
            return true;
        }
    }
}
