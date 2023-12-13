<%--
  Created by IntelliJ IDEA.
  User: iamjiangzhe
  Date: 2023/12/10
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.*" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %>
<%@ page import="com.EasyRide.entity.Administrator"%>
<%@ page import="com.EasyRide.entity.*" %>
<%@ page import="java.util.List" %>
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
  int customerId = Integer.parseInt(request.getParameter("customerId"));
  if (customerId ==0) {
    response.sendRedirect("/");
  }
  CustomerDao customerDao = new CustomerDao();
  Customer customer = customerDao.getCustomerById(customerId);
  RentalRecordDao rentalRecordDao = new RentalRecordDao();
  List<RentalRecord> rentalRecords = rentalRecordDao.getRentalRecordsByCustomerID(customerId,null);
%>
<div class="details-container">
  <h1>Customer Details</h1>
  <form action ="${pageContext.request.contextPath}/admin/editCustomer" method ="post">
    <table>
      <tr>
        <td>Customer ID</td>
        <td id="customer_ID" name="customerID"><%= customer.getCustomerId()%></td>
      </tr>
      <tr>
        <td>Customer Name</td>
        <td><input type="text" id="customerName" name="customerName" value="<%= customer.getName()%>"></td>
      </tr>
      <tr>
        <td>account</td>
        <td><input type="text" id="account" name="account" value="<%=customer.getAccount()%>"></td>
      </tr>
      <tr>
        <td>License Number</td>
        <td><input type="txt" id="licenseNumber" name="licenseNumber" value="<%=customer.getLicenseNumber()%>"></td>
      </tr>
      <tr>
        <td>Contact Info</td>
        <td><input type="txt" id="contactInfo" name="contactInfo" value="<%=customer.getContactInfo()%>"></td>
      </tr>
      <tr>
        <td>Address</td>
        <td><input type="txt" id="address" name="address" value="<%=customer.getAddress()%>"></td>
      </tr>
      <div class="centered-container">
        <input type="hidden" id="customerId" name="customerId" value="<%=customer.getCustomerId()%>"></input>
        <input type="submit" value="Update Customer Infomation">
      </div>
    </table>
  </form>
</div>
<div class="details-container">
  <h1>RentalRecord Details</h1>
  <table>
    <tr>
      <th>Rental Record ID</th>
      <th>Rent Date</th>
      <th>Expexted Return Date</th>
      <th>Rental Fee</th>
      <th>Status</th>
      <th></th>
      <%--            <th>Payment</th>--%>
    </tr>
    <%
      PaymentDao paymentDao = new PaymentDao();
      for (RentalRecord rentalRecordDetail: rentalRecords) {
    %>
    <tr>
      <td><%= rentalRecordDetail.getRentalId()%></td>
      <td><%= rentalRecordDetail.getStartDate()%></td>
      <td><%= rentalRecordDetail.getExpectedReturnDate()%></td>
      <td><%= rentalRecordDetail.getRentalFee()%></td>
      <td><%= rentalRecordDetail.getStatus()%></td>
      <td>
        <form id="detailForm" action="${pageContext.request.contextPath}/admin/filterPayment" method="post">
          <input type="hidden" name="rentalId" value="<%= rentalRecordDetail.getRentalId() %>">
          <input type="submit" value="Details" class="submit-details">
        </form></td>
      <%--            <td>        <%List<Payment> payments= paymentDao.getPaymentByRentalRecordId(rentalRecordDetail.getRentalId());%>--%>
      <%--                <table>--%>
      <%--                    <tr>--%>
      <%--                        <th>Payment ID</th>--%>
      <%--                        <th>RentalRecord ID</th>--%>
      <%--                        <th>Amount</th>--%>
      <%--                        <th>Payment Method</th>--%>
      <%--                        <th>Payment Type</th>--%>
      <%--                        <th>Payment Date</th>--%>
      <%--                    </tr>--%>
      <%--                    <% for (Payment payment: payments) {%>--%>
      <%--                    <tr>--%>
      <%--                        <td><%= payment.getPaymentId() %>--%>
      <%--                        </td>--%>
      <%--                        <td><%= payment.getRentalId() %>--%>
      <%--                        </td>--%>
      <%--                        <td><%= payment.getAmount() %>--%>
      <%--                        </td>--%>
      <%--                        <td><%= payment.getPaymentMethod() %>--%>
      <%--                        </td>--%>
      <%--                        <td><%= payment.getPaymentType() %>--%>
      <%--                        </td>--%>
      <%--                        <td><%= payment.getPaymentDate() %>--%>
      <%--                        </td>--%>
      <%--                    </tr>--%>
      <%--                    <%}%>--%>
      <%--                </table></td>--%>
    </tr>
    <%}%>
  </table>
</div>

</body>
</html>