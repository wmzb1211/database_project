<%@ page import="com.EasyRide.entity.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
    <link rel="stylesheet" type="text/css" href="style/login.css">
</head>
<body>
<h1>Update Your Profile</h1>
<% Customer customer = (Customer) session.getAttribute("customer"); %>
<form action="/customer/updateProfile" method="post">

    <table>
        <tr>
            <td>昵称</td>
            <td><input type="text" id="name" name="name" value="<%= customer.getName() %>"></td>
        </tr>
        <tr>
            <td>账号</td>
            <td><input type="text" id="account" name="account" value="<%= customer.getAccount() %>"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" id="password" name="password" value=""></td>
        </tr>
        <tr>
            <td>确认密码</td>
            <td><input type="password" id="password2" name="password2" value=""></td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" id="contactInfo" name="contactInfo" value="<%= customer.getContactInfo() %>"></td>
        </tr>
        <tr>
            <td>驾驶证号</td>
            <td><input type="text" id="licenseNumber" name="licenseNumber" value="<%= customer.getLicenseNumber() %>"></td>
        </tr>
        <tr>
            <td>地址</td>
            <td><input type="text" id="address" name="address" value="<%= customer.getAddress() %>"></td>
        </tr>
    </table>

    <div class="centered-container"> <input type="submit" value="更新资料"> </div>
</form>
</body>
</html>