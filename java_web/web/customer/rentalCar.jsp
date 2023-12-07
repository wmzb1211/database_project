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
<%
    String role = "";
    // 检查session中存有customer对象还是admin对象
    if (session.getAttribute("customer") != null) {
        role = "customer";
    } else if (session.getAttribute("admin") == null) {
        role = "admin";
    } else {
        // 重定向到index.jsp页面
        response.sendRedirect("/index.jsp");
    }

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
        <div class="filter-container">
            <div class="filter-selected">
                <label for="brandSelect">品牌:</label>
                <select name="brand" id="brandSelect" class="select-long">
                    <option value="">请选择品牌</option>
                    <% for (String brand : brands) { %>
                    <option value="<%= brand %>"><%= brand %>
                    </option>
                    <% } %>
                </select>

                <label for="modelSelect">型号:</label>
                <select name="model" id="modelSelect" class="select-long" disabled>
                    <option value="">请选择品牌</option>
                </select>

                <label for="colorSelect">颜色:</label>
                <select name="color" id="colorSelect" class="select-long">
                    <option value="">请选择颜色</option>
                    <% for (String color : colors) { %>
                    <option value="<%= color %>"><%= color %>
                    </option>
                    <% } %>
                </select>

                <label for="yearSelect">年份:</label>
                <select name="year" id="yearSelect" class="select-long">
                    <option value="">请选择年份</option>
                    <% for (String year : years) { %>
                    <option value="<%= year %>"><%= year %>
                    </option>
                    <% } %>
                </select>

                <br>

                <label for="statusSelect">状态:</label>
                <select name="status" id="statusSelect" class="select-long">
                    <% if (role.equals("customer")) { %>
                    <option value="Available">Available</option>
                    <% } else { %>
                    <option value="Available">Available</option>
                    <option value="Unavailable">Unavailable</option>
                    <option value="Rented">Rented</option>
                    <% } %>
                </select>


                <label for="minDailyRentalFeeInput">价格区间:</label>
                <input type="text" id="minDailyRentalFeeInput" name="minDailyRentalFee" style="width: 150px; margin-top: 15px;"
                       placeholder="请输入最小租金">

                <label for="maxDailyRentalFeeInput">至</label>
                <input type="text" id="maxDailyRentalFeeInput" name="maxDailyRentalFee" style="width: 150px; margin-top: 15px;"
                       placeholder="请输入最大租金">

            </div>
            <div class="filter-button">
                <input type="submit" value="筛选" style="width: 120px; height: 70px; font-size: 18px;">
            </div>
        </div>
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
            <th class="rental-column">
                <% if (role.equals("customer")) { %>
                Rental Duration
                <% } else { %>
                Details
                <% } %>
            </th>
        </tr>
        <% for (Car car : cars) {

        %>
        <tr>
            <td><%= car.getCarId() %>
            </td>
            <td><%= car.getBrand() %>
            </td>
            <td><%= car.getModelName() %>
            </td>
            <td><%= car.getColor() %>
            </td>
            <td><%= car.getDescription() %>
            </td>
            <td><%= car.getYear() %>
            </td>
            <td><%= car.getDailyRentalFee() %>
            </td>
            <!-- 一个输入框，输入租车天数 -->
            <td class="rental-column">
                    <% if (role.equals("customer")) { %>
                <form id="rentalForm" action="/customer/checkTimeoutRental" method="post"
                      onsubmit="return checkDuration()">
                    <input type="hidden" name="function" value="/customer/rentalCar">
                    <input type="hidden" name="carId" value="<%= car.getCarId() %>">
                    <input type="hidden" name="rentalRecordId" value="0">
                    <input type="text" name="duration" value="1" class="input-short">
                    <input type="submit" value="Rent" class="submit-rent">
                </form>
                    <% } else { %>
                <form action="/admin/details" method="post">
                    <input type="hidden" name="carId" value="<%= car.getCarId() %>">
                    <input type="submit" value="Details" class="submit-details">
                </form>
                    <% } %>

        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
