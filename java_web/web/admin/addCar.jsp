<%@ page import="com.EasyRide.entity.Administrator.java" %>
<%@ page import="com.EasyRide.entity.RentalRecord" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.EasyRide.dao.RentalRecordDao" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.dao.CarDao" %>
<%@ page import="com.EasyRide.dao.PaymentDao" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.CarModelDao" %>
<%@ page import="com.EasyRide.entity.SystemLog" %>
<%@ page import="com.EasyRide.dao.SystemLogDao" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Add OR Delet Cars</title>
    <link rel="stylesheet" type="text/css" href="../customer/style/user.css">
    <script src="../customer/js/user.js"></script>
</head>
<body>
<%-- 调用CarDao中的addCar和deleteCar --%>
<%
    CarDao carDao = new CarDao();
    CarModelDao carModelDao = new CarModelDao();
    SystemLogDao systemLogDao = new SystemLogDao();
    Administrator administrator = (Administrator) session.getAttribute("administrator");
    String action = request.getParameter("action");
    if (action != null) {
        if (action.equals("add")) {
            String carModelId = request.getParameter("carModelId");
            String carId = request.getParameter("carId");
            String carStatus = request.getParameter("carStatus");
            String carLocation = request.getParameter("carLocation");
            String carPrice = request.getParameter("carPrice");
            String carImage = request.getParameter("carImage");
            CarModel carModel = carModelDao.getCarModelById(carModelId);
            Car car = new Car(carId, carModel, carStatus, carLocation, Double.parseDouble(carPrice), carImage);
            carDao.addCar(car);
            SystemLog systemLog = new SystemLog(administrator, "Add Car", "Add Car " + carId);
            systemLogDao.addSystemLog(systemLog);
            response.sendRedirect("car.jsp");
        } else if (action.equals("delete")) {
            String carId = request.getParameter("carId");
            carDao.deleteCar(carId);
            SystemLog systemLog = new SystemLog(administrator, "Delete Car", "Delete Car " + carId);
            systemLogDao.addSystemLog(systemLog);
            response.sendRedirect("car.jsp");
        }
    }
%>
