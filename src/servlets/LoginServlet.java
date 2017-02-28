package servlets;

import base.Constant;
import jp.org.json.JSONObject;
import model.IRequestCallback;
import model.impl.login.LoginManager;
import pojo.User;
import pojo.network.Result;
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
        this.message = "用户登录ing";
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        getHttpResponse(req, resp);
        getJsonResponse(req, resp);
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

    private void getHttpResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ///响应内容样式
        resp.setContentType("text/html;charset=UTF-8");
        //设置逻辑实现
        PrintWriter out = resp.getWriter();
        out.println("<h1 style=\'text-align:center\'>" + message + "</h1></br>");
        // 处理中文
//        String id = ChineseUtil.adjustMessCode(req.getParameter(Constant.USER_ID));
        String id = req.getParameter(Constant.USER_ID);
//        String name = req.getParameter("username");
        String password = req.getParameter(Constant.USER_PWD);

        ///访问数据库
        LoginManager.getInstance().setRequestCallback(new IRequestCallback<User>() {
            @Override
            public void finish(User value) {
//                out.println("<p style='font-weight:bold'>成功登录！！返回的user信息：</p>");
//                out.println("<p style='font-weight:bold'>");
//                out.println(value);
//                out.println("</p>");
                ///设置Cookie数据
//                Cookie userNameCookie = new Cookie(Constant.USER_ID, CookieManager.getInstance().getChineseCookie(id));
//                Cookie passwordCookie = new Cookie(Constant.USER_PWD, password);
//                Cookie emailCookie = new Cookie(Constant.USER_PWD, value.getEmail());
//                Cookie phoneCookie = new Cookie(Constant.USER_PWD, value.getPhone());
//                Cookie ageCookie = new Cookie(Constant.USER_PWD, value.getAge()+"");
//                Cookie sexCookie = new Cookie(Constant.USER_PWD, value.getSex()+"");
//                Cookie userPicCookie = new Cookie(Constant.USER_PIC, value.getUser_pic());
//                Cookie userIDCookie = new Cookie(Constant.USER_ID, value.getUser_id());
//
//                userNameCookie.setMaxAge(10*60);//10min
//                passwordCookie.setMaxAge(10*60);//10min
//                emailCookie.setMaxAge(10*60);
//                phoneCookie.setMaxAge(10*60);
//                ageCookie.setMaxAge(10*60);
//                sexCookie.setMaxAge(10*60);
//                userPicCookie.setMaxAge(10*60);
//                userIDCookie.setMaxAge(10*60);
//
//                resp.addCookie(userNameCookie);
//                resp.addCookie(passwordCookie);
//                resp.addCookie(emailCookie);
//                resp.addCookie(phoneCookie);
//                resp.addCookie(sexCookie);
//                resp.addCookie(ageCookie);
//                resp.addCookie(userIDCookie);
//                resp.addCookie(userPicCookie);

                req.setAttribute("user", value);
                try {
                    req.getRequestDispatcher("../user.jsp").forward(req,resp);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                ///TODO: 准备重定向
                //设置重定向的新位置
//                String site = new String("/SpringDemo/user.jsp");
//                resp.setStatus(resp.SC_MOVED_TEMPORARILY);
//                resp.setHeader("Location",site);
                //以上的setStatus 和 setHeader 相当于 resp.sendDirect("URL"); 重定向作用：将重定向的网页的状态码与网页位置发送回浏览器
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

    private void getJsonResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=utf-8");

        String id = ChineseUtil.adjustMessCode(req.getParameter(Constant.USER_ID));
        String password = req.getParameter(Constant.USER_PWD);
        System.out.println("id 和 password分别为："+ id+","+password);
        LoginManager.getInstance().setRequestCallback(new IRequestCallback<User>() {
            @Override
            public void finish(User value) {
                if (value!=null){
                    System.out.println("登录成功，获得的User信息："+value.toJsonString());
                    Result<User> result = new Result<>();
                    result.code = 200;
                    result.msg = "success";
                    result.data = value;
                    System.out.println(result.toJsonString());

                    try {
                        PrintWriter out = resp.getWriter();
                        out.print(result.toJsonString());
//                        out.print(value.toJsonString());
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void error(String msg) {
                System.err.println(msg);
            }
        });
        LoginManager.getInstance().login(id,password);
    }

}
