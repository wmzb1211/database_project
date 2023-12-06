<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String function = request.getParameter("function");
    int carId = Integer.parseInt(request.getParameter("carId"));
    int customerId = Integer.parseInt(request.getParameter("customerId"));
    String paymentDetails = request.getParameter("paymentDetails");
    Double dailyRentalFee = Double.parseDouble(request.getParameter("dailyRentalFee"));
    int rentalDuration = Integer.parseInt(request.getParameter("rentalDuration"));
    Double totalRentalFee = dailyRentalFee * rentalDuration;

%>
<html>
<body>
<h2>Payment Confirmation</h2>
<p>Car ID: <%= carId %></p>
<p>paymentDetails: <%= paymentDetails %></p>
<p>Item Price: <%= dailyRentalFee %> per day, for <%= rentalDuration %> days</p>
<p>Total Price: <%= totalRentalFee %></p>

<form action="<%= function %>" method="post">
    <input type="hidden" name="carId" value="<%= carId %>">
    <input type="hidden" name="customerId" value="<%= customerId %>">
    <input type="hidden" name="rentalDuration" value="<%= rentalDuration %>">
    <input type="hidden" name="totalRentalFee" value="<%= totalRentalFee %>">
    <input type="submit" value="Confirm Payment">
</form>
</body>
</html>
