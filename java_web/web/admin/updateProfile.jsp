<%@ page import="com.EasyRide.entity.Administrator" %>
<%
    Administrator administrator = (Administrator) session.getAttribute("admin");
    if (administrator == null) {
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
    <link rel="stylesheet" type="text/css" href="../customer/style/login.css">
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
<div class="login-container">
    <h2>Update Profile</h2>
    <% administrator = (Administrator) session.getAttribute("admin"); %>
    <form action="/admin/updateProfile" method="post">

        <table>
            <tr>
                <td>管理员名称:</td>
                <td><input type="text" name="username" value="<%=administrator.getName()%>" required></td>
            </tr>
            <tr>
                <td>管理员账号</td>
                <td><input type="text" name="account" value="<%=administrator.getAccount()%>" required></td>
            </tr>
            <tr>
                <td>管理员密码:</td>
                <td><input type="password" id="password" name="password" value=""></td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input type="password" id="password2" name="password2" value=""></td>
            </tr>
            <tr>
                <td>管理员联系方式:</td>
                <td><input type="text" name="ContactInfo" value="<%=administrator.getContactInfo()%>" required></td>
            </tr>

        </table>

        <div class="centered-container">
            <input type="submit" value="更新资料"></div>
    </form>
</div>
</body>
</html>







