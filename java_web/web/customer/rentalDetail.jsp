<%@ page import="com.EasyRide.entity.RentalRecord" %>
<%@ page import="com.EasyRide.entity.Payment" %>
<%@ page import="com.EasyRide.dao.PaymentDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.dao.CarDao" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.CarModelDao" %>
<%@ page import="com.EasyRide.dao.RentalRecordDao" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %><%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 19:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>RentalRecord Details</title>
    <link rel="stylesheet" type="text/css" href="style/details.css">
</head>
<body>

<%
    int rentalRecordID = Integer.parseInt(request.getParameter("rentalRecordID"));
    if (rentalRecordID == 0) {
        response.sendRedirect("/customer/user.jsp");
    }
    RentalRecord rentalRecord = new RentalRecordDao().getRentalRecordsByID(rentalRecordID);
    Car car = new CarDao().getCarById(rentalRecord.getCarId());

    if (rentalRecord == null) {
        response.sendRedirect("/customer/user.jsp");
    }
    PaymentDao paymentDao = new PaymentDao();
    List<Payment> payments = null;
    if (rentalRecord != null) {
        payments = paymentDao.getPaymentByRentalRecordId(rentalRecord.getRentalId());
    }
%>

<div class="details-container">
    <h1>RentalRecord Details</h1>

    <p>RentalRecord ID: <%= rentalRecord.getRentalId() %></p>
    <p>Car ID: <%= rentalRecord.getCarId() %></p>
    <p>Car Model: <%= car.getBrand() + " " + car.getModelName() %></p>
    <p>Description: <%= HTMLUtils.escapeHtml(car.getDescription()) %></p>
    <p>Color: <%= car.getColor() %></p>
    <p>Car Year: <%= car.getYear() %></p>
    <p>Rent Date: <%= rentalRecord.getStartDate() %></p>
    <p>Expected Return Date: <%= rentalRecord.getExpectedReturnDate() %></p>
    <p>Rental Fee: <%= rentalRecord.getRentalFee() %></p>
    <p>Status: <%= rentalRecord.getStatus() %></p>

    <h2>Payment Details</h2>

    <table>
        <tr>
            <th>Payment ID</th>
            <th>RentalRecord ID</th>
            <th>Amount</th>
            <th>Payment Method</th>
            <th>Payment Type</th>
            <th>Payment Date</th>
        </tr>
        <% for (Payment payment : payments) { %>
        <tr>
            <td><%= payment.getPaymentId() %></td>
            <td><%= payment.getRentalId() %></td>
            <td><%= payment.getAmount() %></td>
            <td><%= payment.getPaymentMethod() %></td>
            <td><%= payment.getPaymentType() %></td>
            <td><%= payment.getPaymentDate() %></td>
        </tr>
        <% } %>
    </table>

    <% if (rentalRecord.getStatus().equals("Ongoing")) { %>
    <form action="/customer/checkReturnTime" method="post">
        <input type="hidden" name="rentalRecordId" value="<%= rentalRecord.getRentalId() %>">
        <input type="submit" value="Return Car">
    </form>
</div>
<%--<form action="/customer/renewCar" method="post">--%>
<%--    <input type="hidden" name="rentalRecordId" value="<%= rentalRecord.getRentalId() %>">--%>
<%--    <input type="text" name="renewDuration" value="1">--%>
<%--    <input type="submit" value="Renew">--%>
<%--</form>--%>
<% } %>



</body>
</html>
