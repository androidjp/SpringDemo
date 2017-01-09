package filters;

import utils.ChineseUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 *
 * Created by junpeng.wu on 1/9/2017.
 */
public class LoginFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String site = ChineseUtil.adjustMessCode(filterConfig.getInitParameter("Site"));
        System.out.println("Site："+site);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter先过滤");
        String userName = servletRequest.getParameter("username");
        if (userName.equals("小明")){
            System.out.println("欢迎您，小明！！");
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
