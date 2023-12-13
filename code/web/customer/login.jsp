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
    <link rel="stylesheet" type="text/css" href="style/login.css"/>
    <style>
        body {
            background-image: url("../images/genshin_background.png");
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>登录到您的账户</h2>
    <form action="${pageContext.request.contextPath}/customer/login" method="post">
        <table>
            <tr>
                <td>账号：</td>
                <td><label>
                    <input type="text" name="account">
                </label></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><label>
                    <input type="password" name="password">
                </label></td>
            </tr>
        </table>
        <div class="centered-container"><input type="submit" value="登录"></div>

    </form>

    <button onclick="window.location.href='register.jsp';">注册新账户</button>

    <br>

    <button onclick="window.location.href='${pageContext.request.contextPath}/index.jsp';"> 返回首页 </button>
</div>
</body>
</html>

