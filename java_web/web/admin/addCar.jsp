<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.*" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %>
<%@ page import="com.EasyRide.entity.Administrator"%>
<%@ page import="com.EasyRide.entity.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    Administrator administrator = (Administrator) session.getAttribute("admin");
    // 检查用户是否已登录
    if (administrator == null) {
        // 如果用户未登录，重定向到登录页面
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Profile</title>
    <link rel="stylesheet" type="text/css" href="style/login.css">
</head>
<body>
<%

    Car carDetail = (Car) request.getAttribute("car");
    request.setAttribute("car", carDetail);
//    if (carDetail.getCarId() ==0) {
//        response.sendRedirect("/user.jsp");
//    }
    CarModel carModelDetail =  (CarModel) request.getAttribute("model");
    request.setAttribute("model", carModelDetail);
    List<RentalRecord> rentalRecordDetails = (List<RentalRecord>) request.getAttribute("rentalRecords");
//    List<RentalRecord> returnRecordDetails = (List<RentalRecord>) request.getAttribute("returnRecords");
//    List<RentalRecord> rentalRecordDetails = new RentalRecordDao().getRentalRecordsByCarID(carDetailId);
    List<List<Payment>> allPayments = (List<List<Payment>>) request.getAttribute("allPayments");
    List<List<ReturnRecord>> allReturnRecords = (List<List<ReturnRecord>>) request.getAttribute("allReturnRecords");

%>
<div class="details-container">
    <h1>Car Details</h1>
    <form action ="/admin/updateCar" method ="post">
        <table>
            <tr>
                <td>Car ID</td>
                <td><%= carDetail.getCarId()%></td>
            </tr>
            <tr><td>Brand</td><td><%= carModelDetail.getBrand()%></td></tr>
            <tr><td>Model</td><td><%= carModelDetail.getModelName()%></td></tr>
            <tr>
                <td>Plate Number</td>
                <td><input type="text" id="plateNumber" name="plateNumber" value="<%= carDetail.getPlateNumber()%>"></td>
            </tr>
            <tr>
                <td>Color</td>
                <td><input type="text" id="color" name="color" value="<%=carDetail.getColor()%>"></td>
            </tr>
            <tr>
                <td>Year</td>
                <td><input type="number" id="year" name="year" value="<%=carDetail.getYear()%>"></td>
            </tr>
            <tr>
                <td>Status</td>
                <td><select id="status" name="status" class="select-long">
                    <option value="<%=carDetail.getStatus()%>"><%=carDetail.getStatus()%></option>
                    <option value="unavailable">unavailable</option>
                </select></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><input type="text" id="description" name="description" value="<%=carDetail.getDescription()%>"></td>
            </tr>
            <tr>
                <td>Daily Rental Fee</td>
                <td><input type="number" id="fee" name="fee" value="<%=carDetail.getDailyRentalFee()%>"></td>
            </tr>
        </table>
        <input type="hidden" id="carId" name="carId" value ="<%=carDetail.getCarId()%>">
        <input type="hidden" id="carModelId" name="carModelId" value ="<%=carModelDetail.getModelId()%>">
        <div class="centered-container">
            <input type="submit" value="Update Car">
        </div>
    </form>
</div>
<%--<div class="details-container">--%>
<%--    <h1>Car Model Details</h1>--%>
<%--    <p></p>--%>
<%--</div>--%>
<div class="details-container">
    <h1>RentalRecord Details</h1>
    <table>
        <tr>
            <th>Rental Record ID</th>
            <th>Rent Date</th>
            <th>Expexted Return Date</th>
            <th>Rental Fee</th>
            <th>Status</th>
            <th></th>
            <%--            <th>Payment</th>--%>
        </tr>
        <%
            PaymentDao paymentDao = new PaymentDao();
            for (RentalRecord rentalRecordDetail: rentalRecordDetails) {
        %>
        <tr>
            <td><%= rentalRecordDetail.getRentalId()%></td>
            <td><%= rentalRecordDetail.getStartDate()%></td>
            <td><%= rentalRecordDetail.getExpectedReturnDate()%></td>
            <td><%= rentalRecordDetail.getRentalFee()%></td>
            <td><%= rentalRecordDetail.getStatus()%></td>
            <td>
                <form id="detailForm" action="/admin/filterPayment" method="post">
                    <input type="hidden" name="rentalId" value="<%= rentalRecordDetail.getRentalId() %>">
                    <input type="submit" value="Details" class="submit-details">
                </form></td>
            <%--            <td>        <%List<Payment> payments= paymentDao.getPaymentByRentalRecordId(rentalRecordDetail.getRentalId());%>--%>
            <%--                <table>--%>
            <%--                    <tr>--%>
            <%--                        <th>Payment ID</th>--%>
            <%--                        <th>RentalRecord ID</th>--%>
            <%--                        <th>Amount</th>--%>
            <%--                        <th>Payment Method</th>--%>
            <%--                        <th>Payment Type</th>--%>
            <%--                        <th>Payment Date</th>--%>
            <%--                    </tr>--%>
            <%--                    <% for (Payment payment: payments) {%>--%>
            <%--                    <tr>--%>
            <%--                        <td><%= payment.getPaymentId() %>--%>
            <%--                        </td>--%>
            <%--                        <td><%= payment.getRentalId() %>--%>
            <%--                        </td>--%>
            <%--                        <td><%= payment.getAmount() %>--%>
            <%--                        </td>--%>
            <%--                        <td><%= payment.getPaymentMethod() %>--%>
            <%--                        </td>--%>
            <%--                        <td><%= payment.getPaymentType() %>--%>
            <%--                        </td>--%>
            <%--                        <td><%= payment.getPaymentDate() %>--%>
            <%--                        </td>--%>
            <%--                    </tr>--%>
            <%--                    <%}%>--%>
            <%--                </table></td>--%>
        </tr>
        <%}%>
    </table>
</div>

</body>
</html>
