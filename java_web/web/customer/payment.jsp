<%@ page import="com.EasyRide.entity.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Customer customer = (Customer) session.getAttribute("customer");
    int customerId = customer.getCustomerId();

    String function = request.getParameter("function");
    int carId = Integer.parseInt(request.getParameter("carId"));

    int rentalRecordId = Integer.parseInt(request.getParameter("rentalRecordId"));

    String paymentType = request.getParameter("paymentType"); // Overdue Penalty Fee OR Rental Fee
    String paymentDetails = request.getParameter("paymentDetails");

    Double dailyRentalFee = Double.parseDouble(request.getParameter("dailyRentalFee"));
    int duration = Integer.parseInt(request.getParameter("duration"));

    Double totalFee = dailyRentalFee * duration;
    if (paymentType.equals("Overdue Penalty Fee")) {
        totalFee = totalFee * 3;
    }

%>
<html>
<body>
<h2>Payment Confirmation</h2>
<p>Car ID: <%= carId %></p>
<p>Payment Type: <%= paymentType %></p>
<p>paymentDetails: <%= paymentDetails %></p>
<p>Item Price: <%= dailyRentalFee %> per day, for <%= duration %> day(s)</p>

<% if (paymentType.equals("Overdue Penalty Fee")) { %>
<p>Total Price(3 times than normal price): <%= totalFee %></p>
<% } else { %>
<p>Total Price: <%= totalFee %></p>
<% } %>


<form action="<%= function %>" method="post">
    <input type="hidden" name="carId" value="<%= carId %>">
    <input type="hidden" name="rentalRecordId" value="<%= rentalRecordId %>">
    <input type="hidden" name="duration" value="<%= duration %>">
    <input type="hidden" name="totalFee" value="<%= totalFee %>">
    <input type="submit" value="Confirm Payment">
</form>
</body>
</html>
