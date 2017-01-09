package servlets;

import base.Constant;
import model.IRequestCallback;
import model.register.RegisterManager;
import utils.ChineseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by junpeng.wu on 1/9/2017.
 */
@WebServlet("/servlets/RegisterServlet")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<p>开始注册</p>");

        String user_name  = ChineseUtil.adjustMessCode(req.getParameter(Constant.USER_NAME));
        String user_pwd = req.getParameter(Constant.USER_PWD);
        //设置注册回调事件
        RegisterManager.getInstance().setRequestCallback(new IRequestCallback() {
            @Override
            public void finish() {
                out.println("<p>");
                out.println("成功注册");
                out.println("</p>");
            }
            @Override
            public void error(String msg) {
                out.println("<p>");
                out.println(msg);
                out.println("</p>");
            }
        });
        ///开始注册
        RegisterManager.getInstance().register(user_name,user_pwd);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
