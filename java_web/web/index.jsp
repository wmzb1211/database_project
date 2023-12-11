<%--
  Created by IntelliJ IDEA.
  User: Harrison
  Date: 2023/11/25
  Time: 21:51
  index.jsp
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>汽车租赁系统</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      text-align: center;
      margin: 0;
      padding: 0;
      background-image: url("images/genshin_background.png");
        background-repeat: no-repeat;
        background-size: cover;
        background-attachment: fixed;
    }
    h1 {
      color: #333;
    }
    .button-container {
      margin: 20px;
      /*opacity: 1.0;*/
    }
    .button-container form {
      /*opacity: 1.0;*/
      margin: 10px;
    }
    input[type=submit] {
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      background-color: #007bff;
      color: white;
      cursor: pointer;
      transition: background-color 0.3s;
    }
    input[type=submit]:hover {
      background-color: #0056b3;
    }
    .login-container {
      background-color: rgba(255, 255, 255, 0.8);
      width: 50%;
      /*不透明度*/
      /*  opacity: 0.5;*/

      max-width: 700px;
      margin: 40px auto;
      padding: 20px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      border-radius: 8px;
    }
  </style>
</head>
<body>
<div class="login-container">
<h1>欢迎来到汽车租赁系统</h1>

<div class="button-container">
  <form action="customer/login.jsp">
    <input type="submit" value="登录">
  </form>
  <form action="customer/register.jsp">
    <input type="submit" value="注册">
  </form>
  <form action="admin/login.jsp">
    <input type="submit" value="管理员登录">
  </form>

<%--  <form action="admin/statsBrands" method="post">--%>
<%--    <input type="submit" value="统计">--%>
<%--  </form>--%>
</div>
</div>
</body>
</html>

