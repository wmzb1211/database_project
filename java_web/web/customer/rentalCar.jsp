<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/6
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.CarDao" %>
<%@ page import="com.EasyRide.dao.CarModelDao" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %>

<%@ page import="com.EasyRide.entity.Customer" %>
<%@ page import="java.util.*" %>
<%
    int customerId = ((Customer) session.getAttribute("customer")).getCustomerId();

    List<Car> cars = (List<Car>) request.getAttribute("cars");
    List<String> brands = (List<String>) request.getAttribute("brands");
    List<String> colors = (List<String>) request.getAttribute("colors");
    List<String> years = (List<String>) request.getAttribute("years");
    List<String> statuses = (List<String>) request.getAttribute("statuses");
%>
<html>
<head>
    <title>汽车详情 - 汽车租赁系统</title>
    <link rel="stylesheet" type="text/css" href="style/cars.css">
    <script src="js/filter.js"></script>
</head>
<body>
<div class="cars-container">
<h2>Cars List</h2>

<form action="/customer/filterCars" method="get">
    <label for="brandSelect">品牌:</label>
    <select name="brand" id="brandSelect" class="select-long">
        <option value="">请选择品牌</option>
        <% for (String brand : brands) { %>
        <option value="<%= brand %>"><%= brand %></option>
        <% } %>
    </select>

    <label for="modelSelect">型号:</label>
    <select name="model" id="modelSelect" class="select-long" disabled>
        <option value="">请选择品牌</option>
    </select>

    <label for="colorSelect">颜色:</label>
    <select name="color", id="colorSelect" class="select-long">
        <option value="">请选择颜色</option>
        <% for (String color : colors) { %>
        <option value="<%= color %>"><%= color %></option>
        <% } %>
    </select>

    <label for="yearSelect">年份:</label>
    <select name="year", id="yearSelect" class="select-long">
        <option value="">请选择年份</option>
        <% for (String year : years) { %>
        <option value="<%= year %>"><%= year %></option>
        <% } %>
    </select>

    <label for="statusSelect">状态:</label>
    <select name="status", id="statusSelect" class="select-long">
        <option value="Available">Available</option>
    </select>

    <input type="submit" value="筛选">
</form>

<table>
    <tr>
        <th>Car ID</th>
        <th>Brand</th>
        <th>Model</th>
        <th>Color</th>
        <th>Description</th>
        <th>Year</th>
        <th>Daily Rental Fee</th>
        <th class="rental-column">Rental Duration</th>
    </tr>
    <% for(Car car : cars) {

    %>
    <tr>
        <td><%= car.getCarId() %></td>
        <td><%= car.getBrand() %></td>
        <td><%= car.getModelName() %></td>
        <td><%= car.getColor() %></td>
        <td><%= car.getDescription() %></td>
        <td><%= car.getYear() %></td>
        <td><%= car.getDailyRentalFee() %></td>
        <!-- 一个输入框，输入租车天数 -->
        <td class="rental-column">
            <form id="rentalForm" action="payment.jsp" method="post" onsubmit="return checkDuration()">
                <input type="hidden" name="function"        value="/customer/rentalCar">
                <input type="hidden" name="carId"           value="<%= car.getCarId() %>">
                <input type="hidden" name="rentalRecordId"  value="0">
                <input type="hidden" name="paymentType"     value="Rental Fee">
                <input type="hidden" name="paymentDetails"  value="<%= HTMLUtils.escapeHtml(car.toString()) %>">
                <input type="hidden" name="dailyRentalFee"  value="<%= car.getDailyRentalFee() %>">
                <input type="text"   name="duration"        value="1"       class="input-short">
                <input type="submit"                        value="Rent"    class="submit-rent">
            </form>
    </tr>
    <% } %>
</table>
</div>
</body>
</html>
