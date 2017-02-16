package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网页重定向处理类
 * Created by androidjp on 2017/1/10.
 */
@WebServlet("/servlets/PageRedirect")
public class PageRedirect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(" 正在重定向  ...");
        ///设置响应内容
        resp.setContentType("text/html;charset=UTF-8");
        //设置重定向的新位置
        String site = new String("http://www.runoob.com");
        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
        resp.setHeader("Location",site);
    }
}
