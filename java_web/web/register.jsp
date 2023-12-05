<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/12/5
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        function validateForm() {
            var password1 = document.forms["registrationForm"]["password"].value;
            var password2 = document.forms["registrationForm"]["password2"].value;

            if (password1 !== password2) {
                alert("密码不匹配，请重新输入。");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<form name="registrationForm" action="register" method="post" onsubmit="return validateForm()">
    <!--    (String name, String account, String password, String contactInfo, String licenseNumber, String address) -->
    <table>
        <tr>
            <td>昵称</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>账号</td>
            <td><input type="text" name="account"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr>
            <td>确认密码</td>
            <td><input type="password" name="password2"></td>
        </tr>
        <tr>
            <td>联系方式</td>
            <td><input type="text" name="contactInfo"></td>
        </tr>
        <tr>
            <td>驾驶证号</td>
            <td><input type="text" name="licenseNumber"></td>
        </tr>
        <tr>
            <td>地址</td>
            <td><input type="text" name="address"></td>
        </tr>
        <tr>
            <td><input type="submit" value="注册"></td>
        </tr>
    </table>
</form>
</body>
</html>
