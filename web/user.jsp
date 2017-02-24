<%@ page import="base.Constant" %>
<%@ page import="pojo.User" %>
<%@ page import="utils.ChineseUtil" %><%--
  Created by IntelliJ IDEA.
  User: androidjp
  Date: 2017/2/22
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpringDemo--User管理</title>
</head>
<body>

<%!
    private String userName = null;
    private String userID;
    private String userPwd;
    private String age;
    private String email;
    private String phone;
    private String sex;
    private String userPic;
%>

<h1>用户信息界面</h1>

<%
    //    Cookie[] cookies  = request.getCookies();
//    for (Cookie cookie : cookies){
//        if (cookie.getName().compareTo(Constant.USER_NAME)==0){
//            userName = cookie.getValue();
//
//        }else if (cookie.getName().compareTo("user_id")==0){
//            userID = cookie.getValue();
//        }else if (cookie.getName().compareTo("user_pwd")==0){
//            userPwd = cookie.getValue();
//        }else if (cookie.getName().compareTo("age")==0){
//            age = cookie.getValue();
//        }else if (cookie.getName().compareTo("sex")==0){
//            sex = cookie.getValue();
//        }else if (cookie.getName().compareTo("email")==0){
//            email = cookie.getValue();
//        }else if (cookie.getName().compareTo("phone")==0){
//            phone = cookie.getValue();
//        }
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//    }

    User user = (User) request.getAttribute("user");
    if (user != null) {
        userName = ChineseUtil.adjustMessCode(user.getUser_name());
        System.out.println("user.jsp 界面中，获取user对象的userName 后，计算其长度："+ userName.getBytes().length);
        System.out.println("user.jsp 界面中，获取user对象的userName 后，将其转成utf-8编码后，计算其长度："+ userName.getBytes("utf-8").length);

        userID = user.getUser_id();
        userPwd = user.getUser_pwd();
        email = user.getEmail();
        phone = user.getPhone();
        age = user.getAge() + "";
        sex = user.getSex() + "";
        userPic = user.getUser_pic();
    }


%>

<ul>
    <li>用户名: <%=userID%>
    </li>
    <li>昵称：<%=userName%>
    </li>
    <li>密码：<%=userPwd%>
    </li>
    <li>年龄：<%=age%>
    </li>
    <li>邮箱：<%=email%>
    </li>
    <li>手机号：<%=phone%>
    </li>
    <li>性别：<%=sex%>
    </li>
    <li>图片：<%=userPic%>
    </li>
</ul>


<form action="/SpringDemo/servlets/UserServlet" method="post">

    要修改的用户ID：<%=userID%>
    </br>密码：<input class="input_bg" type="password" name="user_pwd" id="ed_pwd">
    </br>性别：<input class="input_bg" type="radio" name="sex" value="0">男 <input class="input_bg" type="radio" name="sex"
                                                                               value="1">女
    </br>年龄：<input class="input_bg" type="number" name="age" id="ed_age">
    </br>用户名：<input class="input_bg" type="text" name="user_name" id="ed_user_name">
    </br>用户图片：<input class="input_bg" type="text" name="user_pic" id="ed_user_pic">
    </br>邮箱：<input class="input_bg" type="email" name="email" id="ed_email">
    </br>手机号：<input class="input_bg" type="tel" name="phone" id="ed_phone">
    </br><input type="submit" value="修改"> <input type="reset" value="重置">
</form>
</body>
</html>
