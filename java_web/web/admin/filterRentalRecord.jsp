<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %>

<%@ page import="java.util.*" %>
<%@ page import="com.EasyRide.entity.Customer" %>
<%@ page import="com.EasyRide.entity.RentalRecord" %>
<%
  String role = "";
  // 检查session中存有customer对象还是admin对象
  if (session.getAttribute("customer") != null) {
    role = "customer";
  } else if (session.getAttribute("admin") != null) {
    role = "admin";
  } else {
    // 重定向到index.jsp页面
    response.sendRedirect("/index.jsp");
    return;
  }

  List<RentalRecord> rentalRecords = (List<RentalRecord>) request.getAttribute("rentalRecords");


%>
<html>
<head>
  <title>Customer</title>
  <link rel="stylesheet" type="text/css" href="style/cars.css">
  <script src="js/filter.js"></script>
</head>
<body>


<div class="cars-container">

  <div class="table-header">
    <h2>Rental Record List</h2>
    <% if (role.equals("customer")) { %>
    <button id="cancel-btn" onclick="window.location.href='${pageContext.request.contextPath}/customer/user.jsp'" class="back-button">Back</button>
    <% } else if (role.equals("admin")) { %>
    <button id="cancel-btn" onclick="window.location.href='${pageContext.request.contextPath}/admin/user.jsp'" class="back-button">Back</button>
    <% } %>
  </div>


  <form action="${pageContext.request.contextPath}/admin/filterRentalRecord" method="get" onsubmit="return validateRentalFeeInput();">
    <div class="filter-container">
      <div class="filter-selected">
        <label for="brandSelect">筛选条件:</label>
        <select name="selectType" id="brandSelect" class="select-long">
          <option value="">请选择筛选条件</option>
          <option value="rentalId">ID</option>
          <option value="customerId">Customer</option>
          <option value="carId">Car</option>
          <option value="status">Status</option>
        </select>

        <label></label>
        <input name="filterValue" id="filterValue" class="select-long"></input>
      </div>
      <div class="filter-button">
        <input type="submit" value="筛选" style="width: 120px; height: 70px; font-size: 18px;">
      </div>
    </div>
  </form>

  <table>
    <tr>
      <th>Record ID</th>
      <th>Customer Id</th>
      <th>Car Id</th>
      <th>Start Date</th>
      <th>Expected Return Date</th>
      <th>Actual Return Date</th>
      <th>Rental Fee</th>
      <th>Status</th>
      <th></th>
    </tr>
    <% for (RentalRecord rentalRecord : rentalRecords) {

    %>
    <tr>
      <td><%= rentalRecord.getRentalId() %>
      </td>
      <td><%= rentalRecord.getCustomerId() %>
      </td>
      <td><%= rentalRecord.getCarId() %>
      </td>
      <td><%= rentalRecord.getStartDate() %>
      </td>
      <td><%= rentalRecord.getExpectedReturnDate() %>
      </td>
      <td><%= rentalRecord.getActualReturnDate() %>
      </td>
      <td><%= rentalRecord.getRentalFee() %></td>
      <td><%= rentalRecord.getStatus() %></td>
      <td>
        <form id="detailForm" action="${pageContext.request.contextPath}/admin/getRentalRecordDetail" method="post">
          <input type="hidden" name="rentalRecordId" value="<%= rentalRecord.getRentalId() %>">
          <input type="submit" value="Details" class="submit-details">
        </form>
      </td>
    </tr>
    <% } %>
  </table>
</div>

</body>
</html>
