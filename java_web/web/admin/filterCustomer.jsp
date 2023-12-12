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

  List<Customer> customers = (List<Customer>) request.getAttribute("customers");


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
    <h2>Customer List</h2>
    <% if (role.equals("customer")) { %>
    <button id="cancel-btn" onclick="window.location.href='${pageContext.request.contextPath}/customer/user.jsp'" class="back-button">Back</button>
    <% } else if (role.equals("admin")) { %>
    <button id="cancel-btn" onclick="window.location.href='${pageContext.request.contextPath}/admin/user.jsp'" class="back-button">Back</button>
    <% } %>
  </div>


  <form action="${pageContext.request.contextPath}/admin/filterCustomer" method="get" onsubmit="return validateRentalFeeInput();">
    <div class="filter-container">
      <div class="filter-selected">
        <label for="brandSelect">筛选条件:</label>
        <select name="selectType" id="brandSelect" class="select-long">
          <option value="">请选择筛选条件</option>
          <option value="customerId">ID</option>
          <option value="account">account</option>
          <option value="licenseNumber">licenceNumber</option>
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
      <th>Customer ID</th>
      <th>Name</th>
      <th>Account</th>
      <th>Contact Info</th>
      <th>License Number</th>
      <th>Address</th>
      <th></th>
    </tr>
    <% for (Customer customer : customers) {

    %>
    <tr>
      <td><%= customer.getCustomerId() %>
      </td>
      <td><%= customer.getName() %>
      </td>
      <td><%= customer.getAccount() %>
      </td>
      <td><%= customer.getContactInfo() %>
      </td>
      <td><%= customer.getLicenseNumber() %>
      </td>
      <td><%= customer.getAddress() %>
      </td>
      <td><form id="detailForm" action="${pageContext.request.contextPath}/admin/editCustomer.jsp" method="post">
        <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
        <%--        <input type="hidden" name="customerStatus" value="<%=customer.get%>">--%>
        <input type="submit" value="Details" class="submit-details">

      </form></td>
    </tr>
    <% } %>
  </table>
  <button id="open-btn" onclick="openFloatingWindow()" style="margin: auto">Add Customer</button>

</div>
<div id="floating-window">
  <form action="${pageContext.request.contextPath}/admin/editCustomer" method="post">

    <table>
      <tr>
        <td>Name</td>
        <td><input type="text" id="name" name="name" value=""></td>
      </tr>
      <tr>
        <td>Account</td>
        <td><input type="text" id="account" name="account" value=""></td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input type="text" id="password" name="password" value=""></td>
      </tr>
      <tr>
        <td>Contact Info</td>
        <td><input type="text" id="contactInfo" name="contactInfo" value=""></td>
      </tr>
      <tr>
        <td>License Number</td>
        <td><input type="text" id="licenseNumber" name="licenseNumber" value=""></td>
      </tr>
      <tr>
        <td>Address</td>
        <td><input type="text" id="address" name="address" value="">
        </td>
      </tr>
    </table>
    <div class="centered-container" onclick="closeFloatingWindow()">
      <input type="hidden" name="customerId" value="">
      <input type="submit" value="Add Customer">
    </div>
  </form>
  <%--    <button id="close-btn" onclick="closeFloatingWindow()">Close</button>--%>
</div>

</body>
</html>
<script>
  function openFloatingWindow() {
    var floatingWindow = document.getElementById("floating-window");
    floatingWindow.style.display = "block";
  }

  function closeFloatingWindow() {
    var floatingWindow = document.getElementById("floating-window");
    floatingWindow.style.display = "none";
  }
</script>
<style>
  #floating-window {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    padding: 20px;
    background-color: #f0f0f0;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
    z-index: 1000;
  }

  #close-btn {
    position: absolute;
    top: 10px;
    right: 10px;
  }

</style>