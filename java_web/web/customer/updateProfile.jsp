<%@ page import="com.EasyRide.entity.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%
    Customer customer = (Customer) session.getAttribute("customer");
    // 检查用户是否已登录
    if (customer == null) {
        // 如果用户未登录，重定向到登录页面
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
    <link rel="stylesheet" type="text/css" href="style/login.css">
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
    <h2>Update Your Profile</h2>
    <% customer = (Customer) session.getAttribute("customer"); %>
    <form action="${pageContext.request.contextPath}/customer/updateProfile" method="post">

        <table>
            <tr>
                <td>昵称</td>
                <td><label for="name"></label><input type="text" id="name" name="name" value="<%= customer.getName() %>"></td>
            </tr>
            <tr>
                <td>账号</td>
                <td><label for="account"></label><input type="text" id="account" name="account" value="<%= customer.getAccount() %>"></td>
            </tr>
            <tr>
                <td>密码</td>
                <td><label for="password"></label><input type="password" id="password" name="password" value=""></td>
            </tr>
            <tr>
                <td>确认密码</td>
                <td><label for="password2"></label><input type="password" id="password2" name="password2" value=""></td>
            </tr>
            <tr>
                <td>联系方式</td>
                <td><label for="contactInfo"></label><input type="text" id="contactInfo" name="contactInfo" value="<%= customer.getContactInfo() %>">
                </td>
            </tr>
            <tr>
                <td>驾驶证号</td>
                <td><label for="licenseNumber"></label><input type="text" id="licenseNumber" name="licenseNumber"
                                                              value="<%= customer.getLicenseNumber() %>"></td>
            </tr>
            <tr>
                <td>地址</td>
                <td><label for="address"></label><input type="text" id="address" name="address" value="<%= customer.getAddress() %>"></td>
            </tr>
        </table>

        <div class="centered-container"><input type="submit" value="更新资料"></div>
    </form>
</div>
</body>
</html>