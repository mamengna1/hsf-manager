package cn.hsf.hsfmanager.config;

import cn.hsf.hsfmanager.pojo.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证是否登录
 */
@Component
public class LoginInterceptor  implements HandlerInterceptor {
    //这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       Admin admin= (Admin) request.getSession().getAttribute("admin");
        //如果session中没有user，表示没登陆
        if (admin == null){
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }else {
            return true;    //如果session里有user，表示该用户已经登陆，放行，用户即可继续调用自己需要的接口
        }
    }

}
