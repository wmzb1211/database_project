<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/5
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册 - 汽车租赁系统</title>
    <link rel="stylesheet" type="text/css" href="style/login.css">
    <script src="js/checkInput.js"></script>
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
<h2>注册新账户</h2>
<form name="registrationForm" action="/customer/register" method="post" onsubmit="return validateForm()">
    <!--    (String name, String account, String password, String contactInfo, String licenseNumber, String address) -->
    <table>
        <tr>
            <td>昵称</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>账号</td>
            <td><input type="text" name="account"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>确认密码</td>
            <td><input type="password" name="password2"></td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" name="contactInfo"></td>
        </tr>
        <tr>
            <td>驾驶证号</td>
            <td><input type="text" name="licenseNumber"></td>
        </tr>
        <tr>
            <td>地址</td>
            <td><input type="text" name="address"></td>
        </tr>
    </table>
    <div class="centered-container"> <input type="submit" value="注册"> </div>

</form>
<button type="button" onclick="window.location.href='login.jsp';" class="secondary-button">已有账户？登录</button>

<br>

<button onclick="window.location.href='/index.jsp';"> 返回首页 </button>

</div>
</body>
</html>
