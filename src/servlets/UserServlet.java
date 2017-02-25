package servlets;

import base.Constant;
import model.IRequestCallback;
import model.user.UserManager;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * 用户管理（修改）
 * Created by junpeng.wu on 2/10/2017.
 */
@WebServlet("/servlets/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        System.out.println("UserServlet 开始跑了");

        resp.setContentType("text/html;charset=UTF-8");
        String user_id = req.getParameter(Constant.USER_ID);
        String user_name = req.getParameter(Constant.USER_NAME);
        String user_pwd = req.getParameter(Constant.USER_PWD);
        String email = req.getParameter(Constant.EMAIL);
        String phone = req.getParameter(Constant.PHONE);
        String sex = req.getParameter(Constant.SEX);
        String age = req.getParameter(Constant.AGE);
        String user_pic = req.getParameter(Constant.USER_PIC);

        StringBuilder sb = new StringBuilder();
        sb.append(user_id).append(" , \r\n")
                .append(user_name).append(" , \r\n")
                .append(user_pwd).append(" , \r\n")
                .append(email).append(" , \r\n")
                .append(phone).append(" , \r\n")
                .append(sex).append(" , \r\n")
                .append(age).append(" , \r\n")
                .append(user_pic);

        System.out.println(sb.toString());
        System.out.println(user_id == null);

        UserManager.getInstance().setRequestCallback(new IRequestCallback<User>() {

            @Override
            public void finish(User value) {
                System.out.println("成功修改用户信息~");
                ///TODO: 更新User信息，并重新回到这个界面
                req.setAttribute("user", value);
                try {
                    req.getRequestDispatcher("../user.jsp").forward(req, resp);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ServletException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String msg) {
                System.out.println(msg);
            }
        });
        ////
        if (Constant.isEmpty(user_id))
            return;
        if (!Constant.isEmpty(user_name)) {
            UserManager.getInstance().updateUserName(user_id, user_name);
        } else if (!Constant.isEmpty(user_pwd)) {
            UserManager.getInstance().updateUserPwd(user_id, user_pwd);
        } else if (!Constant.isEmpty(email)) {
            UserManager.getInstance().updateEmail(user_id, email);
        } else if (!Constant.isEmpty(phone)) {
            UserManager.getInstance().updatePhone(user_id, phone);
        } else if (!Constant.isEmpty(sex)) {
            UserManager.getInstance().updateSex(user_id, Integer.valueOf(sex));
        } else if (!Constant.isEmpty(age)) {
            UserManager.getInstance().updateAge(user_id, Integer.valueOf(age));
        } else if (!Constant.isEmpty(user_pic)) {
//            UserManager.getInstance().updateUserPic(user_id,user_pic);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
