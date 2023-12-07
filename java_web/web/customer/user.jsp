<%@ page import="com.EasyRide.entity.Customer" %>
<%@ page import="com.EasyRide.entity.RentalRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.EasyRide.dao.RentalRecordDao" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.dao.CarDao" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.CarModelDao" %>
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
<% Customer customer = (Customer) session.getAttribute("customer"); %>
<p>Name: <%= customer.getName() %></p>
<p>Account: <%= customer.getAccount()%></p>
<p>Contact Info: <%= customer.getContactInfo()%></p>
<p>License Number: <%= customer.getLicenseNumber()%></p>
<p>Address: <%= customer.getAddress()%></p>

<%
    int id = customer.getCustomerId();
    List<RentalRecord> rentalRecordList = new RentalRecordDao().getRentalRecordsByCustomerID(id, "Ongoing");
%>
<!--  显示一个按钮，跳转到个人资料编辑页面  -->
<form action="/customer/updateProfile.jsp" method="post">
    <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
    <input type="submit" value="Update Profile">
</form>


<!--  显示rentalRecordList  -->
<table border="1">
    <tr>
        <th>RentalRecord ID</th>
        <th>Car ID</th>
        <th>Car Model</th>
        <th>Color
        <th>Rent Date</th>
        <th>Expected/Actual Return Date</th>
        <th>Total Fee</th>
        <th>Status</th>
        <th>Operation</th>
    </tr>
    <%
    for (RentalRecord record : rentalRecordList) {
        Car car = new Car();
        CarModel carModel = new CarModel();
        if (record.getCarId()!= 0) {
            car = new CarDao().getCarById(record.getCarId());
            carModel = new CarModelDao().getCarModelById(car.getModelId());
        }
    %>
    <tr>
        <td><%= record.getRentalId() %></td>
        <td><%= record.getCarId() %></td>
        <td><%= carModel.getBrand() + " " + carModel.getModelName() %></td>
        <td><%= car.getColor() %></td>
        <td><%= record.getStartDate() %></td>
        <td><%= record.getExpectedReturnDate() %></td>
        <td><%= record.getRentalFee() %></td>
        <td><%= record.getStatus() %></td></td>
        <!--  显示一个按钮，跳转到详情页面  -->
        <td>
        <form action="/customer/rentalDetail.jsp" method="post">
            <input type="hidden" name="rentalRecordID" value="<%= record.getRentalId() %>">
            <input type="submit" value="Detail">
        </form>
        </td>
    </tr>
    <%
    }
    %>
</table>

<!-- 生成一个按钮，跳转到可以租车的页面，传递参数customerId  -->
<form action="/customer/filterCars" method="get">
    <input type="hidden" name="status" value="Available">
    <input type="submit" value="Rent a Car">
</form>
</body>
</html>


