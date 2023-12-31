<%@ page import="com.EasyRide.entity.Customer" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %><%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 14:31
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
<%

    customer = (Customer) session.getAttribute("customer");
    if (customer == null) {
        // 如果用户未登录，重定向到登录页面
        response.sendRedirect("login.jsp");
        return;
    }

    String function = (String) request.getAttribute("function");
    Car car = (Car) request.getAttribute("car");
    int carId = car.getCarId();
    Double dailyRentalFee = car.getDailyRentalFee();
    int rentalRecordId = Integer.parseInt(request.getAttribute("rentalRecordId").toString());
    String paymentType = (String) request.getAttribute("paymentType");
    String paymentDetails = (String) request.getAttribute("paymentDetails");
    int duration = Integer.parseInt(request.getAttribute("duration").toString());

    Double totalFee = dailyRentalFee * duration;
    if (paymentType.equals("Overdue Penalty Fee")) {
        totalFee = totalFee * 3;
    }

%>

<html>
<head>
    <title>Payment Confirmation</title>
    <link rel="stylesheet" type="text/css" href="style/payment.css"> <!-- 确保路径正确 -->
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
<div class="payment-container">
    <h2>Payment Confirmation</h2>
    <p>Car ID: <%= carId %></p>
    <p>Payment Type: <%= paymentType %></p>
    <p class="payment-details">Payment Details: <%= HTMLUtils.escapeHtml(paymentDetails) %></p>
    <p>Item Price: <%= dailyRentalFee %> per day, for <%= duration %> day(s)</p>

    <% if (paymentType.equals("Overdue Penalty Fee")) { %>
    <p class="total-fee">Total Price (3 times than normal price): <%= totalFee %></p>
    <% } else { %>
    <p class="total-fee">Total Price: <%= totalFee %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}<%= function %>" method="post" class="payment-form">
        <input type="hidden" name="carId" value="<%= carId %>">
        <input type="hidden" name="rentalRecordId" value="<%= rentalRecordId %>">
        <input type="hidden" name="duration" value="<%= duration %>">
        <input type="hidden" name="totalFee" value="<%= totalFee %>">
        <input type="submit" value="Confirm Payment">
    </form>

    <button id="cancel-btn" onclick="history.back()" class="back-button">Back</button>
</div>
</body>
</html>

