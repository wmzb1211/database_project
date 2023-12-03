<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/3
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <!-- 页面样式和脚本 -->
</head>
<body>
<h1>欢迎回来，<%= session.getAttribute("user").toString() %></h1>
<!-- 显示用户信息和其他功能 -->
</body>
</html>
