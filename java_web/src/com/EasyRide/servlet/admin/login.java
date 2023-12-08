package com.EasyRide.servlet.admin;

import com.EasyRide.dao.AdministratorDao;
import com.EasyRide.dao.AdministratorDao;
import com.EasyRide.entity.Administrator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.*;

@WebServlet("/admin/login")
public class login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String name = request.getParameter("account");
        String password = request.getParameter("password");

        AdministratorDao ud = new AdministratorDao();
        //调用添加接口
        Administrator a = ud.getAdministrator(name, password);

        if (a == null) {
            String str = "用户名或密码错误";
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='login.jsp'");
            out.print("</script>");
            out.close();
        } else {
            // 登录成功
            // 重定向到user.jsp
            // 将用户信息存储到Session
            HttpSession session = request.getSession();
            session.setAttribute("admin", a);

            response.sendRedirect("user.jsp");
        }
    }
}
