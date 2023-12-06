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
</head>
<body>
<h1>Update Your Profile</h1>
<% Customer customer = (Customer) session.getAttribute("customer"); %>
<form action="/customer/updateProfile" method="post">
    <label for="name">Name:</label>

    <input type="text" id="name" name="name" value="<%= customer.getName() %>"><br>

    <label for="account">Account:</label>
    <input type="text" id="account" name="account" value="<%= customer.getAccount() %>"><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" value=""><br>

    <label for="contactInfo">Contact Info:</label>
    <input type="text" id="contactInfo" name="contactInfo" value="<%= customer.getContactInfo() %>"><br>

    <label for="licenseNumber">License Number:</label>
    <input type="text" id="licenseNumber" name="licenseNumber" value="<%= customer.getLicenseNumber() %>"><br>

    <label for="address">Address:</label>
    <input type="text" id="address" name="address" value="<%= customer.getAddress() %>"><br>

    <input type="submit" value="Update Profile">
</form>
</body>
</html>