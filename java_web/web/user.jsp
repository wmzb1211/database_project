<%@ page import="com.EasyRide.entity.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/3
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>
<h1>Welcome, User!</h1>
<% Customer customer = (Customer) request.getSession().getAttribute("customerObject"); %>
<p>Name: <%= customer.getName() %></p>
<p>Account: <%= customer.getAccount()%></p>
<p>Contact Info: <%= customer.getContactInfo()%></p>
<p>License Number: <%= customer.getLicenseNumber()%></p>
<p>Address: <%= customer.getAddress()%></p>

<!--  显示  -->


</body>
</html>


