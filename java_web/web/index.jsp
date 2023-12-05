<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/11/25
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>My Web App</title>
</head>
<body>
<h1>Welcome to My Web App</h1>

<%--两个按钮：登录和注册，跳转到login.jsp和register.jsp--%>
<form action="login.jsp">
  <input type="submit" value="登录">
</form>
<form action="register.jsp">
  <input type="submit" value="注册">
</form>

</body>
</html>
