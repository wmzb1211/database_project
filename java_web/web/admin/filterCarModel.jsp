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
<%@ page import="com.EasyRide.entity.CarModel" %>
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

  List<CarModel> models = (List<CarModel>) request.getAttribute("models");
  List<String> brands = (List<String>) request.getAttribute("brands");

%>
<html>
<head>
  <title>filter carmodel</title>
  <link rel="stylesheet" type="text/css" href="style/cars.css">
  <script src="js/filter.js"></script>
</head>
<body>


<div class="cars-container">

  <div class="table-header">
    <h2>Car model List</h2>
    <button id="cancel-btn" onclick="window.location.href='/admin/user.jsp'" class="back-button">Back</button>
  </div>


  <form action="/admin/filterCarModel" method="get" onsubmit="return validateRentalFeeInput();">
    <div class="filter-container">
      <div class="filter-selected">
        <label for="brandSelect">品牌:</label>
        <select name="brandSelect" id="brandSelect" class="select-long">
          <option value="">请选择品牌</option>
          <% for (String brand : brands) { %>
          <option value="<%= brand %>"><%= brand %>
          </option>
          <% } %>
        </select>

      </div>
      <div class="filter-button">
        <input type="submit" value="筛选" style="width: 120px; height: 70px; font-size: 18px;">
      </div>
    </div>
  </form>

  <table>
    <tr>
      <th>Car Model ID</th>
      <th>Brand</th>
      <th>Model Name</th>
      <th>Description</th>
      <th></th>
    </tr>
    <%
      for (CarModel carModel : models) {
    %>
    <tr>
      <td><%= carModel.getModelId() %>
      </td>
      <td><%= carModel.getBrand() %>
      </td>
      <td><%= carModel.getModelName() %>
      </td>
      <td><%= carModel.getDescription() %>
      </td>
      <td>
        <form id="detailForm" action="/admin/editCarModel.jsp" method="post">
          <input type="hidden" name="carModelId" value="<%= carModel.getModelId() %>">
          <input type="submit" value="Details" class="submit-details">

        </form></td>
    </tr>
    <% } %>
  </table>
  <button id="open-btn" onclick="openFloatingWindow()" style="margin: auto">Add Car Model</button>

</div>
<div id="floating-window">
  <form action="/admin/editCarModel" method="post">

    <table>
      <tr>
        <td>Brand</td>
        <td><input type="text" id="brandName" name="brandName" value=""></td>
      </tr>
      <tr>
        <td>Model</td>
        <td><input type="text" id="modelName" name="modelName" value=""></td>
      </tr>
      <tr>
        <td>Description</td>
        <td><input type="text" id="description" name="description" value=""></td>
      </tr>
    </table>

    <div class="centered-container" onclick="closeFloatingWindow()">
      <input type="hidden" name="carModelId" value="">
      <input type="submit" value="Add Car">
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