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
    <title>SpringDemo-后台登录</title>
    <link rel="stylesheet" href="css/page_login.css" type="text/css">
</head>
<body style="background:#FFA4B5">
<form action="servlets/LoginServlet" method="post">
    用户名：<input class="input_bg" type="text" name="username" id="ed_username" >
    </br>密码：<input class="input_bg" type="password" name="pwd" id="ed_pwd">
    </br><input type="submit" value="登录"> <input type="reset" value="重置">
</form>
</body>
</html>
