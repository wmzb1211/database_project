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
<body>

<h2>Cars List</h2>

<!-- 创建一个筛选器 -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const brandSelect = document.getElementById("brandSelect");
        const modelSelect = document.getElementById("modelSelect");

        brandSelect.addEventListener("change", function () {
            const selectedBrand = brandSelect.value;

            // 启用型号下拉菜单
            modelSelect.disabled = false;

            // 清空型号下拉菜单的选项
            modelSelect.innerHTML = '<option value="">请选择型号</option>';

            if (selectedBrand) {
                // 创建一个新的XMLHttpRequest对象
                const xhr = new XMLHttpRequest();

                // 设置请求方法和URL，这里假设你的后端Servlet URL是"getModelsByBrand"，并传递选择的品牌作为参数
                xhr.open("GET", "getModelsByBrand?brand=" + selectedBrand, true);

                // 监听XMLHttpRequest的readyState变化
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        // 当请求成功完成时，处理后端返回的数据
                        const response = JSON.parse(xhr.responseText);

                        // 填充型号下拉菜单
                        for (const model of response.models) {
                            const option = document.createElement("option");
                            option.value = model.model_name;
                            option.textContent = model.model_name;
                            modelSelect.appendChild(option);
                        }
                    }
                };

                // 发送Ajax请求
                xhr.send();
            }
        });
    });
</script>

<form action="/customer/filterCars" method="get">
    <label for="brandSelect">品牌:</label>
    <select name="brand" id="brandSelect">
        <option value="">请选择品牌</option>
        <% for (String brand : brands) { %>
        <option value="<%= brand %>"><%= brand %></option>
        <% } %>
    </select>

    <label for="modelSelect">型号:</label>
    <select name="model" id="modelSelect" disabled>
        <option value="">请选择品牌</option>
    </select>

    <label for="colorSelect">颜色:</label>
    <select name="color", id="colorSelect">
        <option value="">请选择颜色</option>
        <% for (String color : colors) { %>
        <option value="<%= color %>"><%= color %></option>
        <% } %>
    </select>

    <label for="yearSelect">年份:</label>
    <select name="year", id="yearSelect">
        <option value="">请选择年份</option>
        <% for (String year : years) { %>
        <option value="<%= year %>"><%= year %></option>
        <% } %>
    </select>

    <label for="statusSelect">状态:</label>
    <select name="status", id="statusSelect">
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
        <th>Rental Duration</th>
    </tr>
    <% for(Car car : cars) {
        int id = car.getModelId();
        CarModelDao carModelDao = new CarModelDao();
        CarModel carModel = carModelDao.getCarModelById(id);
        if (carModel == null) {
            carModel = new CarModel();
        }
    %>
    <tr>
        <td><%= car.getCarId() %></td>
        <td><%= carModel.getBrand() %></td>
        <td><%= carModel.getModelName() %></td>
        <td><%= car.getColor() %></td>
        <td><%= carModel.getDescription() %></td>
        <td><%= car.getYear() %></td>
        <td><%= car.getDailyRentalFee() %></td>
        <!-- 一个输入框，输入租车天数 -->
        <td>
            <form action="payment.jsp" method="post">
                <input type="hidden" name="function"        value="/customer/rentalCar">
                <input type="hidden" name="carId"           value="<%= car.getCarId() %>">
                <input type="hidden" name="rentalRecordId"  value="0">
                <input type="hidden" name="paymentType"     value="Rental Fee">
                <input type="hidden" name="paymentDetails"  value="<%= HTMLUtils.escapeHtml(car.toString()) %>">
                <input type="hidden" name="dailyRentalFee"  value="<%= car.getDailyRentalFee() %>">
                <input type="text"   name="duration"        value="1">
                <input type="submit" value="Rent">
            </form>
    </tr>
    <% } %>
</table>
</body>
</html>
