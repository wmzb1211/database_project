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

<%@ page import="java.util.List" %>
<%@ page import="com.EasyRide.entity.Customer" %>
<%
    int customerId = ((Customer) session.getAttribute("customer")).getCustomerId();
    CarDao carDao = new CarDao();
    CarModelDao carModelDao = new CarModelDao();
    List<Car> cars = carDao.getCarsByStatus("Available");
%>
<html>
<body>
<h2>Available Cars</h2>
<table>
    <tr>
        <th>Car ID</th>
        <th>Brand</th>
        <th>Model</th>
        <th>Description</th>
        <th>Year</th>
        <th>Daily Rental Fee</th>
        <th>Rental Duration</th>
    </tr>
    <% for(Car car : cars) {
        int id = car.getModelId();
        CarModel carModel = carModelDao.getCarModelById(id);
        if (carModel == null) {
            carModel = new CarModel();
        }
    %>
    <tr>
        <td><%= car.getCarId() %></td>
        <td><%= carModel.getBrand() %></td>
        <td><%= carModel.getModelName() %></td>
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
