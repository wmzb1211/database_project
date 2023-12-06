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
    <!-- 页面样式和脚本 -->
</head>
<body>
<form action="/customer/login" method="post">
    <input type="hidden" name="action" value="login">
    <input type="text" name="account" placeholder="用户名" required>
    <input type="password" name="password" placeholder="密码" required>
    <input type="submit" value="登录">
</form>
</body>
</html>

