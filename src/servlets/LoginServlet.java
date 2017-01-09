package servlets;

import model.IRequestCallback;
import model.login.LoginManager;
import utils.ChineseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
        this.message = "init the LoginServlet, 您好，孩子~";
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("请求方式：" + req.getMethod());
        ///响应内容样式
        resp.setContentType("text/html;charset=UTF-8");
        //设置逻辑实现
        PrintWriter out = resp.getWriter();
        out.println("<h3 style=\'text-align:center\'>" + message + "</h3></br>");
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");

        // 处理中文
        String name = ChineseUtil.adjustMessCode(req.getParameter("username"));
//        String name = req.getParameter("username");
        String password = req.getParameter("pwd");
        log("name:" + name + ", pwd:" + password);
        sb.append(name).append(" , ").append(password);
        sb.append("</p>");
        out.println(sb.toString());

        ///访问数据库
        LoginManager.getInstance().setRequestCallback(new IRequestCallback() {
            @Override
            public void finish() {
                out.println("<p style='text-color:blue;'>");
                out.println("成功登录！");
                out.println("</p>");
            }

            @Override
            public void error(String msg) {
                out.println("<p style='text-color:red;'>");
                out.println(msg);
                out.println("</p>");
            }
        });
        LoginManager.getInstance().login(name, password);
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
