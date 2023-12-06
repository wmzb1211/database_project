<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Payment" %>
<%@ page import="com.EasyRide.entity.RentalRecord" %>
<html>
<head>
    <title>Successful</title>
</head>
<body>

<%
    RentalRecord rentalRecord = (RentalRecord) request.getAttribute("rentalRecord");
    Payment payment = (Payment) request.getAttribute("payment");
%>


<h1>Successful</h1>

<p>Thank you for renting with us!</p>

<p>Here is your rental record:</p>

<p><%= rentalRecord.getRentalId() %></p>

<p>Here is your payment information:</p>

<p><%= payment.getPaymentId() %></p>

</body>
</html>
