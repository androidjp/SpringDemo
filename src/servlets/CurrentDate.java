package servlets;

import utils.DateFormatUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Date 相关操作
 * Created by androidjp on 2017/1/10.
 */
@WebServlet("/servlets/CurrentDate")
public class CurrentDate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String title = "show 当前日期与时间";
        Date date = new Date();
        String docType = "<!DOCTYPE html>\n";
        out.println(docType);
        out.println("<html><head>"+title +"</head><body><h2>"+title+"</h2>\n"+"<p>"+date.toString()+"</p>"+"<p>"+ DateFormatUtil.formatDateTime(date)+"</p>");

    }
}
