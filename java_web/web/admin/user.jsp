<%@ page import="com.EasyRide.entity.Administrator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Date" %>
<%@ page import="com.EasyRide.dao.RentalRecordDao" %>
<%@ page import="com.EasyRide.dao.CarDao" %>
<%@ page import="com.EasyRide.dao.PaymentDao" %>
<%@ page import="com.EasyRide.dao.CarModelDao" %>
<%@ page import="com.EasyRide.dao.SystemLogDao" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.EasyRide.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--检查管理员是否已经登录--%>
<%
    Administrator administrator = (Administrator) session.getAttribute("admin");
    if (administrator == null) {
        response.sendRedirect("/admin/login.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" type="text/css" href="../customer/style/user.css">
<%--    <script src="../customer/js/user.js"></script>--%>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        #barChart {
            width: 300px;
            height: 150px;
        }
    </style>

</head>

<body style="display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0;">
<% Administrator admin = (Administrator) session.getAttribute("admin"); %>

<div class="header">
    <h1>Welcome, <%= admin.getName() %>!</h1>
</div>

<div class = "content">
<%-- 管理員信息 --%>
    <div class="admin-info">
        <!-- 管理员资料部分-->
        <div class="profile-info">
            <h2>Admin Info</h2>
            <p>Account: <%= admin.getAccount() %></p>
            <p>Contact Info: <%= admin.getContactInfo() %></p>
    <%--        <p>Address: <%= admin.getAddress() %></p>--%>

            <table>
                <tr>
                    <td>Admin ID</td>
                    <td><%= admin.getAdminId() %></td>
                </tr>
                <tr>
                    <td>Name</td>
                    <td><%= admin.getName() %></td>
                </tr>
                <tr>
                    <td>Contact Info</td>
                    <td><%= admin.getContactInfo() %></td>
                </tr>

            </table>
        </div>
        <h2></h2>
        <!-- 按钮部分-->
        <div class="profile-actions">
            <form action="/admin/updateProfile.jsp" method="post" class="form-button">
                <input type="hidden" name="customerId" value="<%= admin.getAdminId() %>">
                <input type="submit" value="Update Profile">
            </form>

            <form action="LogoutServlet" method="post">
                <div class="logout"> <input type="submit" value="Logout"> </div>
            </form>
        </div>
    </div>

<%
    int id = admin.getAdminId();
    List<SystemLog> systemLogs = new SystemLogDao().getSystemLogbyOperatorId(id);
%>

<%-- 系統日誌 --%>
    <div class="system-log">
        <h2>System Log</h2>
        <table>
            <tr>
                <th>Log ID</th>
                <th>Admin ID</th>
                <th>Log Time</th>
                <th>Log Content</th>
            </tr>
            <% for (SystemLog systemLog : systemLogs) { %>
            <tr>
                <td><%= systemLog.getLogId() %></td>
                <td><%= systemLog.getOperatorId() %></td>
                <td><%= systemLog.getOperationDateTime() %></td>
                <td><%= systemLog.getOperationDescription() %></td>
                <td><%= systemLog.getResult() %></td>
            </tr>
            <% } %>
        </table>
    </div>
<%-- 所有车辆统计信息展示 --%>
    <div class="Cars-Info">
        <h2>Cars Info</h2>
        <%-- 展示有多少辆车被租出去了 --%>
        <% CarDao carDao = new CarDao();
            int count_rented = carDao.getCarsByStatus("Rented").size(); %>
        <p>Number of cars rented: <%= count_rented %></p>
        <%-- 展示有多少辆车处于闲置的状态 --%>
        <% int count_Available = carDao.getCarsByStatus("Available").size(); %>
        <p>Number of cars available: <%= count_Available %></p>
        <%-- 展示本月收入 --%>
        <%

            Calendar calendar = Calendar.getInstance();
            Date currentDate = new Date(calendar.getTimeInMillis());

            // 设置当前日期
            calendar.setTime(currentDate);

            // 将日期调整到上一个月
            calendar.add(Calendar.MONTH, -1);

            // 将日期调整到月初（1号）
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            // 获取上一个月1号的日期，注意类型转换
            Date previousMonthFirstDate = new Date(calendar.getTimeInMillis());

            // 使用 SimpleDateFormat 格式化日期
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String formattedDate = dateFormat.format(previousMonthFirstDate);
            double count_Income = new PaymentDao().getPaymentByStartDate(previousMonthFirstDate);
        %>
        <p>Income in the past month: <%= count_Income %></p>
        <%-- 一个统计图表，柱状图，可视化展示过去十二个月的payment --%>
        <% List<Double> PaymentInPastYear = new PaymentDao().getPaymentInPastYear();
        Double sum = PaymentInPastYear.stream().reduce(0.0, Double::sum);
        %>
        <p>Payment in the past year: <%= sum%></p>
        <p>666: <%=PaymentInPastYear%></p>
        <h2>过去十二个月支付数据柱状图</h2>

        <!-- 用于显示柱状图的 canvas 元素 -->
        <canvas id="barChart" width="300" height="150"></canvas>

        <script>
            // 将 paymentData 传递给 JavaScript 变量
            var paymentDataArray = <%= PaymentInPastYear %>;

            // 使用 Chart.js 创建柱状图
            var ctx = document.getElementById('barChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Month 1', 'Month 2', 'Month 3', 'Month 4', 'Month 5', 'Month 6', 'Month 7', 'Month 8', 'Month 9', 'Month 10', 'Month 11', 'Month 12'],
                    datasets: [{
                        label: 'Payment Data',
                        data: paymentDataArray,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
        <%-- 展示各品牌的租赁情况，饼状图 --%>
        

        

        
    </div>
<%-- 对系统的操作按钮 --%>


<form action="/admin/addCar.jsp" method="post" class="form-button">
    <input type="submit" name="operation" value="Add Car">
</form>
<form action="/admin/addCustomer.jsp" method="post" class="form-button">
    <input type="submit" name="operation" value="Add Customer">
</form>
<form action="/admin/addRentalRecord.jsp" method="post" class="form-button">
    <input type="submit" name="operation" value="Add Rental Record">
</form>
<form action="/admin/addSystemLog.jsp" method="post" class="form-button">
    <input type="submit" name="operation" value="Add System Log">
</form>

</div>
</body>
</html>


