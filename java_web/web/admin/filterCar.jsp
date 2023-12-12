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
  } else if (session.getAttribute("admin") != null) {
    role = "admin";
  } else {
    // 重定向到index.jsp页面
    response.sendRedirect("/index.jsp");
    return;
  }

  List<Car> cars = (List<Car>) request.getAttribute("cars");
  List<String> brands = (List<String>) request.getAttribute("brands");
  List<String> colors = (List<String>) request.getAttribute("colors");
  List<String> years = (List<String>) request.getAttribute("years");
  List<String> statuses = (List<String>) request.getAttribute("statuses");

  Map<String, String> filterParams = (Map<String, String>) session.getAttribute("filterParams");
%>
<html>
<head>
  <title>汽车详情 - 汽车租赁系统</title>
  <link rel="stylesheet" type="text/css" href="style/cars.css">
  <script src="js/filter.js"></script>
</head>
<body>


<div class="cars-container">

  <div class="table-header">
    <h2>Cars List</h2>
    <% if (role.equals("customer")) { %>
    <button id="cancel-btn" onclick="window.location.href='/customer/user.jsp'" class="back-button">Back</button>
    <% } else if (role.equals("admin")) { %>
    <button id="cancel-btn" onclick="window.location.href='/admin/user.jsp'" class="back-button">Back</button>
    <% } %>
  </div>


  <form action="/customer/filterCars" method="get" onsubmit="return validateRentalFeeInput();">
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
        <select name="model" id="modelSelect" class="select-long">
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
          <%
          } else {
            for (String status : statuses) { %>
          <%--                    <option value="<%= status %>"><%= status %>--%>
          <option value="<%= status %>" <% if (status.equals("Available")) { %>selected<% } %>><%= status %></option>
          </option>
          <%
              }
            }
          %>
        </select>


        <label for="minDailyRentalFeeInput">价格区间:</label>
        <input type="text" id="minDailyRentalFeeInput" name="minDailyRentalFee"
               style="width: 150px; margin-top: 15px;"
               placeholder="请输入最小租金">

        <label for="maxDailyRentalFeeInput">至</label>
        <input type="text" id="maxDailyRentalFeeInput" name="maxDailyRentalFee"
               style="width: 150px; margin-top: 15px;"
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
        <form id="detailForm" action="/admin/getCarDetail" method="post">
          <input type="hidden" name="carId" value="<%= car.getCarId() %>">
          <input type="submit" value="Details" class="submit-details">
        </form>


    </tr>
    <% } %>
  </table>
  <button id="open-btn" onclick="openFloatingWindow()" style="margin: auto">Add Car</button>

</div>
<div id="floating-window">
  <form action="/admin/addCar" method="post">

    <table>
      <tr>
        <td>Brand</td>
        <td><input type="text" id="brand" name="brand" value=""></td>
      </tr>
      <tr>
        <td>Model</td>
        <td><input type="text" id="model" name="model" value=""></td>
      </tr>
      <tr>
        <td>Color</td>
        <td>
          <select name="color" id="color" class="select-long">
            <option value="">请选择颜色</option>
            <% for (String color : colors) { %>
            <option value="<%= color %>"><%= color %>
            </option>
            <% } %>
          </select></td>
      </tr>
      <tr>
        <td>Description</td>
        <td><input type="text" id="description" name="description" value=""></td>
      </tr>
      <tr>
        <td>Year</td>
        <td>
          <input type="number" id="year" name="year" value="">
        </td>
      </tr>
      <tr>
        <td>Daily Fee</td>
        <td><input type="number" id="dailyFee" name="dailyFee"
                   value=""></td>
      </tr>
      <tr>
        <td>Plate Number</td>
        <td><input type="text" id="plateNumber" name="plateNumber" value=""></td>
      </tr>
    </table>

    <div class="centered-container" onclick="closeFloatingWindow()"><input type="submit" value="Add Car"></div>
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