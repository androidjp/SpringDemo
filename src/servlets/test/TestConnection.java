package servlets.test;

import pojo.User;
import pojo.network.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by androidjp on 2017/2/17.
 */
@WebServlet("/servlets/test/TestConnection")
public class TestConnection extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        println("authType : "+req.getAuthType());
        println("ContextPath : "+req.getContextPath());
        println("Header信息 : "+req.getHeader("method"));
        println("Method : "+req.getMethod());
        println("pathInfo : " + req.getPathInfo());
        println("pathTranslated : "+req.getPathTranslated());
        println("queryString : "+req.getQueryString());
        println("remoteUser : "+req.getRemoteUser());
        println("requestURI : "+ req.getRequestURI());
        println(req.getCharacterEncoding());
        println(req.getContentType());
        println(req.getRequestURL().toString());
        println("所有的key：");
        Enumeration<String> enumeration =  req.getParameterNames();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            println("key = "+ key+" , value = "+ req.getParameter(key));
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        Result<User> result = new Result<User>();
        result.code = 200;
        result.msg = "wait";
        result.data = new User();
        PrintWriter out = resp.getWriter();
        out.println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    private void println(String msg){
        System.out.println(msg);
    }
}
