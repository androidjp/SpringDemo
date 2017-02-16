package servlets;

import base.Constant;
import model.IRequestCallback;
import model.impl.cookie.CookieManager;
import model.impl.login.LoginManager;
import utils.ChineseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录Servlet
 * Created by junpeng.wu on 1/6/2017.
 */
@WebServlet("/servlets/LoginServlet")
public class LoginServlet extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        this.message = "用户登录ing";
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ///响应内容样式
        resp.setContentType("text/html;charset=UTF-8");
        //设置逻辑实现
        PrintWriter out = resp.getWriter();
        out.println("<h1 style=\'text-align:center\'>" + message + "</h1></br>");
        // 处理中文
        String id = ChineseUtil.adjustMessCode(req.getParameter(Constant.USER_ID));
//        String id = req.getParameter(Constant.USER_ID);
//        String name = req.getParameter("username");
        String password = req.getParameter(Constant.USER_PWD);

        ///访问数据库
        LoginManager.getInstance().setRequestCallback(new IRequestCallback<String>() {
            @Override
            public void finish(String value) {
                out.println("<p style='font-weight:bold'>成功登录！！返回的user信息：</p>");
                out.println("<p style='font-weight:bold'>");
                out.println(value);
                out.println("</p>");
                ///设置Cookie数据
                Cookie userNameCookie = new Cookie(Constant.USER_ID, CookieManager.getInstance().getChineseCookie(id));
                Cookie passwordCookie = new Cookie(Constant.USER_PWD, CookieManager.getInstance().getChineseCookie(password));
                userNameCookie.setMaxAge(10*60);//10min
                passwordCookie.setMaxAge(10*60);//10min

                resp.addCookie(userNameCookie);
                resp.addCookie(passwordCookie);
            }

            @Override
            public void error(String msg) {
                out.println("<p style='text-color:red;'>");
                out.println(msg);
                out.println("</p>");
            }
        });
        LoginManager.getInstance().login(id, password);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ///POST 方式的登录
        this.doGet(req, resp);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
