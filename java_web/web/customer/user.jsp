<%@ page import="com.EasyRide.entity.Customer" %>
<%@ page import="com.EasyRide.entity.RentalRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/3
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
</head>
<body>
<h1>Welcome, User!</h1>
<% Customer customer = (Customer) request.getSession().getAttribute("customerObject"); %>
<p>Name: <%= customer.getName() %></p>
<p>Account: <%= customer.getAccount()%></p>
<p>Contact Info: <%= customer.getContactInfo()%></p>
<p>License Number: <%= customer.getLicenseNumber()%></p>
<p>Address: <%= customer.getAddress()%></p>

<!--  显示rentalRecordList  -->
<table border="1">
    <tr>
        <th>ID</th>
        <th>Car ID</th>
        <th>Rent Date</th>
        <th>Return Date</th>
        <th>Total Fee</th>
    </tr>
    <%
        List<RentalRecord> rentalRecordList = new ArrayList<>();
        try{
            rentalRecordList = (List<RentalRecord>) request.getSession().getAttribute("rentalRecordList");
        }catch (Exception e){
            System.out.println("rentalRecordList is null");
        }
    for (RentalRecord record : rentalRecordList) {
    %>
    <tr>
        <td><%= record.getRentalId() %></td>
        <td><%= record.getCarId() %></td>
        <td><%= record.getStartDate() %></td>
        <td><%= record.getExpectedReturnDate() %></td>
        <td><%= record.getRentalFee() %></td>
    </tr>
    <%
    }
    %>
</table>

<!-- 生成一个按钮，跳转到可以租车的页面，传递参数customerId  -->
<form action="/customer/rentalCar.jsp" method="post">
    <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
    <input type="submit" value="Rent a Car">
</form>
</body>
</html>


