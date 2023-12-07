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
    <link rel="stylesheet" type="text/css" href="style/user.css">
    <script src="js/user.js"></script>
</head>
<body>

<% Customer customer = (Customer) session.getAttribute("customer"); %>

<div class="header">
    <h1>Welcome, <%= customer.getName() %>!</h1>
</div>

<div class="content">
<%-- 用户信息 --%>
    <div class="user-info">
        <h2>User Information</h2>
        <p>Account: <%= customer.getAccount()%></p>
        <p>Contact Info: <%= customer.getContactInfo()%></p>
        <p>License Number: <%= customer.getLicenseNumber()%></p>
        <p>Address: <%= customer.getAddress()%></p>

        <form action="/customer/updateProfile.jsp" method="post" class="form-button">
            <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
            <input type="submit" value="Update Profile">
        </form>
    </div>

<%
    int id = customer.getCustomerId();
    List<RentalRecord> rentalRecordListOngoing = new RentalRecordDao().getRentalRecordsByCustomerID(id, "Ongoing");
    List<RentalRecord> rentalRecordListDone = new RentalRecordDao().getRentalRecordsByCustomerID(id, "Done");
%>

<%-- 租车记录 --%>
<div class="rental-records">
    <h2 id="toggle-ongoing">Ongoing Rentals</h2>
    <!-- 详情表格 -->
    <table id="ongoing-rentals" class="rental-table">
        <tr>
            <th>RentalRecord ID</th>
            <th>Car ID</th>
            <th>Car Model</th>
            <th>Color</th>
            <th>Rent Date</th>
            <th>Expected/Actual Return Date</th>
            <th>Total Fee</th>
            <th>Status</th>
            <th>Operation</th>
        </tr>
        <%
            for (RentalRecord record : rentalRecordListOngoing) {
                Car car = new Car();
                if (record.getCarId()!= 0) {
                    car = new CarDao().getCarById(record.getCarId());
                }
        %>
        <tr>
            <td><%= record.getRentalId() %></td>
            <td><%= record.getCarId() %></td>
            <td><%= car.getBrand() + " " + car.getModelName() %></td>
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


    <h2 id="toggle-completed">Completed Rentals</h2>
    <!-- 详情表格 -->
    <table id="completed-rentals" class="rental-table">
        <tr>
            <th>RentalRecord ID</th>
            <th>Car ID</th>
            <th>Car Model</th>
            <th>Color</th>
            <th>Rent Date</th>
            <th>Expected/Actual Return Date</th>
            <th>Total Fee</th>
            <th>Status</th>
            <th>Operation</th>
        </tr>
        <%
            for (RentalRecord record : rentalRecordListDone) {
                Car car = new Car();
                if (record.getCarId()!= 0) {
                    car = new CarDao().getCarById(record.getCarId());
                }
        %>
        <tr>
            <td><%= record.getRentalId() %></td>
            <td><%= record.getCarId() %></td>
            <td><%= car.getBrand() + " " + car.getModelName() %></td>
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
</div>


<%-- 租车按钮 --%>
<form action="/customer/filterCars" method="get" class="form-button">
    <input type="hidden" name="status" value="Available">
    <input type="submit" value="Rent a Car">
</form>

</div>
</body>
</html>


