package servlets.login;

import base.Constant;
import model.IRequestCallback;
import model.impl.register.RegisterManager;
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
 * 注册逻辑处理类
 * Created by junpeng.wu on 1/9/2017.
 */
@WebServlet("/servlets/login/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private RegisterManager registerManager = new RegisterManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       doHtmlResponse(req, resp);
        req.setCharacterEncoding("utf-8");
        doJsonResponse(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doPost() start..");
        this.doGet(req, resp);
    }


    /**
     * 放回HTML内容给浏览器网页【Web浏览器用】
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException
     */
    private void doHtmlResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("doGet() start..");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<h1 style=\'text-align:center\'>用户注册ing</h1></br>");

        out.println("<p>开始注册</p>");
        System.out.println("RegisterServlet.doGet() start...");

        System.out.println("注册时，获取user_name，计算其长度：" + req.getParameter(Constant.USER_NAME).getBytes().length);

//        String user_name = ChineseUtil.adjustMessCode(req.getParameter(Constant.USER_NAME));
        String user_name = req.getParameter(Constant.USER_NAME);
        System.out.println("将user_name转utf-8后，计算器长度：" + user_name.getBytes().length);

//        String user_name = req.getParameter(base.Constant.USER_NAME);
        String user_pwd = req.getParameter(Constant.USER_PWD);
        String email = req.getParameter(Constant.EMAIL);
        if (email == null || email.length() == 0) email = null;
        String phone = req.getParameter(Constant.PHONE);
        if (phone == null || phone.length() == 0) phone = null;
        String sexStr = req.getParameter(Constant.SEX);
        String ageStr = req.getParameter(Constant.AGE);
        int age = Integer.valueOf(ageStr);
        int sex = Integer.valueOf(sexStr);
        StringBuilder sb = new StringBuilder();
        if (user_name == null || user_name.length() == 0) {
            if (email != null && email.length() > 0)
                user_name = email;
            else
                user_name = phone;
        }
        System.out.println("user_name=" + user_name);
        System.out.println("user_pwd=" + user_pwd);
        System.out.println("email=" + email);
        System.out.println("phone=" + phone);
        System.out.println("age=" + age);
        System.out.println("sex=" + (sex == 0 ? "男" : "女"));

        sb.append("<ul><li>").append("user_name=").append(user_name).append("</li>")
                .append("<li>").append("user_pwd=").append(user_pwd).append("</li>")
                .append("<li>").append("email=").append(email).append("</li>")
                .append("<li>").append("phone=").append(phone).append("</li>")
                .append("<li>").append("age=").append(age).append("</li>")
                .append("<li>").append("sex=").append((sex == 0 ? "男" : "女")).append("</li></ul>");

        out.println(sb.toString());

        //设置注册回调事件
        this.registerManager.setRequestCallback(new IRequestCallback<String>() {

            @Override
            public void finish(String value) {
                out.println("<p>");
                out.println(value);
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
        System.out.println("准备RegisterManager进行注册。。");
        this.registerManager.register(user_name, user_pwd, email, phone, age, sex);
    }

    /**
     * 返回JSON格式的response【Android用】
     *
     * @param req  请求
     * @param resp 响应
     * @throws IOException
     */
    private void doJsonResponse(HttpServletRequest req, HttpServletResponse resp) {
//        String user_name = ChineseUtil.adjustMessCode(req.getParameter(Constant.USER_NAME));
        String user_name = req.getParameter(Constant.USER_NAME);
//        if (user_name != null)
//            System.out.println("将user_name转utf-8后，计算器长度：" + user_name.getBytes().length);


        String user_pwd = req.getParameter(Constant.USER_PWD);
        String email = req.getParameter(Constant.EMAIL);
        if (email == null || email.length() == 0) email = null;
        String phone = req.getParameter(Constant.PHONE);
        if (phone == null || phone.length() == 0) phone = null;
        String sexStr = req.getParameter(Constant.SEX);
        String ageStr = req.getParameter(Constant.AGE);
        int age = Integer.valueOf(ageStr);
        int sex = Integer.valueOf(sexStr);
        StringBuilder sb = new StringBuilder();
        if (user_name == null || user_name.length() == 0) {
            if (email != null && email.length() > 0)
                user_name = email;
            else
                user_name = phone;
        }

        System.out.println("在写入MySQL之前，phone 是否存在：" + phone);

        //设置注册回调事件
        this.registerManager.setRequestCallback(new IRequestCallback<String>() {
            @Override
            public void finish(String value) {
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                Result<String> result = new Result<>();
                result.code = 200;
                result.msg = "success";
                result.data = value;
                try {
                    out = resp.getWriter();
//                    out.append(responseJSONObject.toString());
                    System.out.println("注册成功，返回的json数据为：" + result.toJsonString());
                    out.print(result.toJsonString());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null)
                        out.close();
                }
            }

            @Override
            public void error(String msg) {
                System.err.println(msg);
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                Result<String> result = new Result<>();
                result.code = 400;
                result.msg = "fail";
                result.data = msg;
                try {
                    out = resp.getWriter();
//                    out.append(responseJSONObject.toString());
                    System.out.println("注册成功，返回的json数据为：" + result.toJsonString());
                    out.print(result.toJsonString());
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null)
                        out.close();
                }
            }
        });
        ///开始注册
        this.registerManager.register(user_name, user_pwd, email, phone, age, sex);

    }
}
