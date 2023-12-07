<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/3
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录 - 汽车租赁系统</title>
    <link rel="stylesheet" type="text/css" href="style/login.css" />
</head>
<body>

<h2>登录到您的账户</h2>
<form action="/customer/login" method="post">
    <input type="hidden" name="action" value="login">
    <input type="text" name="account" placeholder="用户名" required>
    <input type="password" name="password" placeholder="密码" required>
    <div class="centered-container"> <input type="submit" value="登录"> </div>
</form>

<button onclick="window.location.href='register.jsp';">注册新账户</button>
</body>
</html>

