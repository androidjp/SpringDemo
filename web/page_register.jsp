<%--
  Created by IntelliJ IDEA.
  User: junpeng.wu
  Date: 1/6/2017
  Time: 4:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>SpringDemo-后台注册</title>
    <link rel="stylesheet" href="css/page_login.css" type="text/css">
</head>
<body style="background:#FFA4B5">
<form action="servlets/RegisterServlet" method="post">
    用户名：<input class="input_bg" type="text" name="user_name" id="ed_username" >
    </br>密码：<input class="input_bg" type="password" name="user_pwd" id="ed_pwd">
    </br>邮箱：<input class="input_bg" type="email" name="email" id="ed_email">
    </br>手机号：<input class="input_bg" type="tel" name="phone" id="ed_phone">
    </br>年龄：<input class="input_bg" type="number" name="user_age" id="ed_age">
    <br/>性别：<input type="radio" name="sex" id="radio_male" checked="checked" value="0" >男<input type="radio" name="sex" id="radio_female" value="1">女
    </br><input type="submit" value="注册"> <input type="reset" value="重置">
</form>
</body>
</html>
