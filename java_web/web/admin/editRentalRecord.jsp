<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.*" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %>
<%@ page import="com.EasyRide.entity.Administrator"%>
<%@ page import="com.EasyRide.entity.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
  Administrator administrator = (Administrator) session.getAttribute("admin");
  // 检查用户是否已登录
  if (administrator == null) {
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
</head>
<body>
<%

  Car carDetail = (Car) request.getAttribute("cars");
  CarModel carModelDetail =  (CarModel) request.getAttribute("model");
  Customer customer = (Customer) request.getAttribute("customer");
  RentalRecord rentalRecordDetail = (RentalRecord) request.getAttribute("rentalRecord");
  List<Payment> payments = (List<Payment>) request.getAttribute("payments");
  List<ReturnRecord> returnRecords = (List<ReturnRecord>) request.getAttribute("returnRecords");

%>
<div class="details-container">
  <h1>Rental Record Details</h1>
  <form action ="/admin/editRentalRecord" method ="post">
    <table>
      <tr>
        <td>Rental Record ID</td>
        <td><%= carDetail.getCarId()%></td>
      </tr>
      <tr><td>Customer Name</td><td><%= customer.getName()%></td></tr>
      <tr><td>Car ID</td><td><%= carDetail.getCarId()%></td></tr>
      <tr>
        <td>Plate Number</td>
        <td><%= carDetail.getPlateNumber()%></td>
      </tr>
      <tr>
        <td>Color</td>
        <td><%=carDetail.getColor()%></td>
      </tr>
      <tr>
        <td>Start Date</td>
        <td><%= rentalRecordDetail.getStartDate() %></td>
      </tr>
      <tr>
        <td>Expected Return Date</td>
        <td><%= rentalRecordDetail.getExpectedReturnDate() %></td>
      </tr>
      <tr>
        <td>Status</td>
        <td><%= rentalRecordDetail.getStatus() %></td>
      </tr>
      <tr>
        <td>Actual Return Date</td>
        <td><%= rentalRecordDetail.getActualReturnDate() %></td>
      </tr>
      <tr>
        <td>Rental Fee</td>
        <td><input type="number" id="fee" name="fee" value="<%=rentalRecordDetail.getRentalFee()%>"></td>
      </tr>
    </table>
    <input type="hidden" id="rentalRecordId" name="rentalRecordId" value ="<%= rentalRecordDetail.getRentalId() %>">
    <div class="centered-container">
      <input type="submit" value="Update Rental Record">
    </div>
  </form>
</div>
<div class="details-container">
  <h1>Car Model Details</h1>
  <p></p>
</div>
<div class="details-container">
  <h1>RentalRecord Details</h1>

  <table>
    <tr>
      <th>Payment ID</th>
      <th>RentalRecord ID</th>
      <th>Amount</th>
      <th>Payment Method</th>
      <th>Payment Type</th>
      <th>Payment Date</th>
    </tr>
    <% for (Payment payment: payments) {%>
    <tr>
      <td><%= payment.getPaymentId() %>
      </td>
      <td><%= payment.getRentalId() %>
      </td>
      <td><%= payment.getAmount() %>
      </td>
      <td><%= payment.getPaymentMethod() %>
      </td>
      <td><%= payment.getPaymentType() %>
      </td>
      <td><%= payment.getPaymentDate() %>
      </td>
    </tr>
    <%}%>
  </table>
</div>

</body>
</html>
