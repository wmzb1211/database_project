<%--
  Created by IntelliJ IDEA.
  User: iamjiangzhe
  Date: 2023/12/12
  Time: 15:50
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
  List<Payment> payments = new ArrayList<>();
  payments = (List<Payment>) request.getAttribute("payments");
%>
<div class="details-container">
  <h1>Payment Details</h1>
  <table>
    <tr>
      <th>Payment ID</th>
      <th>Payment Type</th>
      <th>Payment Date</th>
      <th>Amount</th>
      <th>Payment Method</th>
    </tr>
    <% for (Payment payment : payments) {

    %>
    <tr>
      <td><%= payment.getPaymentId() %>
      </td>
      <td><%= payment.getPaymentType() %>
      </td>
      <td><%= payment.getPaymentDate() %>
      </td>
      <td><%= payment.getAmount() %>
      </td>
      <td><%= payment.getPaymentMethod() %>
      </td>
    </tr>
    <% } %>
  </table>

</div>


</body>
</html>