package base;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * 输出请求头信息
 * Created by junpeng.wu on 1/6/2017.
 */
@WebServlet("/base.DisplayHeader")
public class DisplayHeader extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Enumeration headerKeys = req.getHeaderNames();
        StringBuilder stringBuilder =new StringBuilder();
        stringBuilder.append("<ul>");
        String paramKey;
        while (headerKeys.hasMoreElements()){
            paramKey = (String) headerKeys.nextElement();
            stringBuilder.append("<li>").append(paramKey).append(" : ").append(req.getHeader(paramKey)).append("</li>");
        }
        stringBuilder.append("</ul>");
        out.println(stringBuilder.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
