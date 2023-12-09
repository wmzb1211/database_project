<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <title>登录 - 汽车租赁系统</title>
    <link rel="stylesheet" type="text/css" href="../customer/style/login.css" />

</head>

<%--<h2>登录到您的账户</h2>--%>
<%--<form action="/admin/login" method="post">--%>
<%--    <input type="hidden" name="action" value="login">--%>
<%--    <input type="text" name="account" placeholder="用户名" required>--%>
<%--    <input type="password" name="password" placeholder="密码" required>--%>
<%--    <div class="centered-container"> <input type="submit" value="登录"> </div>--%>
<%--</form>--%>
<%--<button onclick="window.location.href='register.jsp';">注册新账户</button>--%>
<%--</body>--%>
<%--</html>--%>

<div class="login-container">
    <h2>管理员登录</h2>
    <form action="/admin/login" method="post">
        <table>
            <tr>
                <td>账号：</td>
                <td><input type="text" name="account"></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="password" name="password"></td>
            </tr>
        </table>
        <div class="centered-container"><input type="submit" value="登录"></div>

    </form>

    <button onclick="window.location.href='/index.jsp';"> 返回首页 </button>
<%--    <button onclick="window.location.href='register.jsp';">注册新账户</button>--%>
</div>
</body>
</html>