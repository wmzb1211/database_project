<%@ page import="com.EasyRide.entity.Customer" %>
<%@ page import="com.EasyRide.entity.RentalRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.EasyRide.dao.RentalRecordDao" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.dao.CarDao" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.CarModelDao" %>
<%@ page import="java.sql.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/3
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%
    Customer customer = (Customer) session.getAttribute("customer");
    // 检查用户是否已登录
    if (customer == null) {
        // 如果用户未登录，重定向到登录页面
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" type="text/css" href="style/user.css">
    <script src="js/user.js"></script>
    <style>
        body {
            background-image: url("../images/genshin_background.png");
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
        }
    </style>
</head>
<body>

<%
    //    Customer customer = null;
//    if (session.getAttribute("customer")!= null) {
//        customer = (Customer) session.getAttribute("customer");
//        if (customer.getName() == null) {
//            response.sendRedirect("/index.jsp");
//        }
//        if (customer.getCustomerId() == 0) {
//            response.sendRedirect("/index.jsp");
//        }
//    } else {
//        response.sendRedirect("/index.jsp");
//    }
    customer = (Customer) session.getAttribute("customer");
%>

<div class="header">
    <h1>Welcome, <%= customer.getName() %>!</h1>
</div>

<div class="content">
    <%-- 用户信息 --%>
    <div class="user-info">
        <!-- 个人资料部分 -->
        <div class="profile-info">
            <h2>User Information</h2>
            <p>Account: <%= customer.getAccount()%>
            </p>
            <p>Contact Info: <%= customer.getContactInfo()%>
            </p>
            <p>License Number: <%= customer.getLicenseNumber()%>
            </p>
            <p>Address: <%= customer.getAddress()%>
            </p>
        </div>

        <!-- 按钮部分 -->
        <div class="profile-actions">
            <form action="LogoutServlet" method="post">
                <div class="logout"><input type="submit" value="Logout"></div>
            </form>

            <form action="${pageContext.request.contextPath}/customer/updateProfile.jsp" method="post">
                <input type="hidden" name="customerId" value="<%= customer.getCustomerId() %>">
                <input type="submit" value="Update Profile">
            </form>
        </div>
    </div>


    <%
        int id = customer.getCustomerId();
        List<RentalRecord> rentalRecordListOngoing = new RentalRecordDao().getRentalRecordsByCustomerID(id, "Ongoing");
        List<RentalRecord> rentalRecordListCompleted = new RentalRecordDao().getRentalRecordsByCustomerID(id, "Completed");
        Date now = new Date(System.currentTimeMillis());
    %>

    <%-- 租车记录 --%>
    <div class="rental-records">
        <div class="table-header">
            <h2>Ongoing Rentals (<%= rentalRecordListOngoing.size() %>)</h2>
            <button id="toggle-ongoing-btn">隐藏</button>
        </div>

        <table id="ongoing-rentals" class="rental-table">
            <tr>
                <th>Rental ID</th>
                <th>Plate Number</th>
                <th>Car Model</th>
                <th>Color</th>
                <th>Rent Date</th>
                <th>Expected Return Date</th>
                <th>Total Fee</th>
                <th>Status</th>
                <th>Operation</th>
            </tr>
            <%
                for (RentalRecord record : rentalRecordListOngoing) {
                    Car car = new Car();
                    if (record.getCarId() != 0) {
                        car = new CarDao().getCarById(record.getCarId());
                    }
                    Date expectedReturnDate = record.getExpectedReturnDate();
                    long dayDifference = (now.getTime() - expectedReturnDate.getTime()) / (24 * 60 * 60 * 1000);
            %>
            <tr>
                <td><%= record.getRentalId() %>
                </td>
                <td><%= car.getPlateNumber() %>
                </td>
                <td><%= car.getBrand() + " " + car.getModelName() %>
                </td>
                <td><%= car.getColor() %>
                </td>
                <td><%= record.getStartDate() %>
                </td>
                <td>
                    <% if (dayDifference > 0) { %>
                    <div style="font-size: xx-large;color: red;font-weight: bold;">
                        <%= record.getExpectedReturnDate() %>
                    </div>
                    <% } else { %>
                    <%= record.getExpectedReturnDate() %>
                    <% } %>
                </td>
                <td><%= record.getRentalFee() %>
                </td>
                <td><%= record.getStatus() %>
                </td>
                </td>
                <!--  显示一个按钮，跳转到详情页面  -->
                <td>
                    <form action="${pageContext.request.contextPath}/customer/rentalDetail.jsp" method="post" class="form-button">
                        <input type="hidden" name="rentalRecordID" value="<%= record.getRentalId() %>">
                        <input type="submit" value="Operation">
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>

        <h2></h2>

        <div class="table-header">
            <h2>Completed Rentals (<%= rentalRecordListCompleted.size() %>)</h2>
            <button id="toggle-completed-btn">展开</button>
        </div>

        <table id="completed-rentals" class="rental-table" style="display: none;">
            <tr>
                <th>Rental ID</th>
                <th>Plate Number</th>
                <th>Car Model</th>
                <th>Color</th>
                <th>Rent Date</th>
                <th>Actual Return Date</th>
                <th>Total Fee</th>
                <th>Status</th>
                <th>Operation</th>
            </tr>
            <%
                for (RentalRecord record : rentalRecordListCompleted) {
                    Car car = new Car();
                    if (record.getCarId() != 0) {
                        car = new CarDao().getCarById(record.getCarId());
                    }
            %>
            <tr>
                <td><%= record.getRentalId() %>
                </td>
                <td><%= car.getPlateNumber() %>
                </td>
                <td><%= car.getBrand() + " " + car.getModelName() %>
                </td>
                <td><%= car.getColor() %>
                </td>
                <td><%= record.getStartDate() %>
                </td>
                <td><%= record.getActualReturnDate() %>
                </td>
                <td><%= record.getRentalFee() %>
                </td>
                <td><%= record.getStatus() %>
                </td>
                </td>
                <!--  显示一个按钮，跳转到详情页面  -->
                <td>
                    <form action="${pageContext.request.contextPath}/customer/rentalDetail.jsp" method="post"
                          class="form-button">
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
</div>

<div class="rent-car-container">
    <%-- 租车按钮 --%>
    <form action="${pageContext.request.contextPath}/customer/filterCars" method="get">
        <input type="hidden" name="status" value="Available">
        <input type="submit" value="Rent a Car!" class="rent-car-button">
    </form>
</div>

</body>
</html>


