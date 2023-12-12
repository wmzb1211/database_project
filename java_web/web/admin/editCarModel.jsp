<%--
  Created by IntelliJ IDEA.
  User: iamjiangzhe
  Date: 2023/12/10
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.EasyRide.entity.Car" %>
<%@ page import="com.EasyRide.entity.CarModel" %>
<%@ page import="com.EasyRide.dao.*" %>
<%@ page import="com.EasyRide.util.HTMLUtils" %>
<%@ page import="com.EasyRide.entity.Administrator"%>
<%@ page import="com.EasyRide.entity.*" %>
<%@ page import="java.util.List" %>
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
  int carModelDetailId = Integer.parseInt(request.getParameter("carModelId"));
  if (carModelDetailId ==0) {
    response.sendRedirect("/");
  }
  CarModelDao carModelDao = new CarModelDao();
  CarModel carModelDetail = carModelDao.getCarModelById(carModelDetailId);
%>
<div class="details-container">
  <h1>Car Details</h1>
  <form action ="${pageContext.request.contextPath}/admin/editCarModel" method ="post">
    <table>
      <tr>
        <td>Car Model ID</td>
        <td id="ModelID" name="ModelID"><%= carModelDetail.getModelId()%></td>
      </tr>
      <tr>
        <td>Brand</td>
        <td><input type="text" id="brand" name="brand" value="<%= carModelDetail.getBrand()%>"></td>
      </tr>
      <tr>
        <td>Model name</td>
        <td><input type="text" id="modelName" name="modelName" value="<%=carModelDetail.getModelName()%>"></td>
      </tr>
      <tr>
        <td>Description</td>
        <td><input type="txt" id="description" name="description" value="<%=carModelDetail.getDescription()%>"></td>
      </tr>
      <div class="centered-container">
        <input type="hidden" id="carModelId" name="carModelId" value="<%=carModelDetail.getModelId()%>"></input>
        <input type="submit" value="Update Car Model">
      </div>
    </table>
  </form>
</div>


</body>
</html>