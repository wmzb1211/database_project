package com.EasyRide.servlet;

import com.EasyRide.dao.UserDao;
import com.EasyRide.dao.UserDaoImpl;
import com.EasyRide.entity.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// UserServlet.java
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private UserDao userDao = new UserDaoImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("login")) {
            // 处理登录请求
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            // 调用DAO层进行身份验证
            User user = userDao.getUserByUsername(username);
            if (user != null && user.getPasswordHash().equals(password)) {
                // 登录成功，执行相关操作
                // 将用户信息存储到Session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // 重定向到用户首页或其他页面
                response.sendRedirect("user_home.jsp");
            } else {
                // 登录失败，返回错误信息
                request.setAttribute("error", "用户名或密码错误");

                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else if (action.equals("register")) {
            // 处理注册请求
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");

            // 调用DAO层进行注册
            User user = new User(username, password, email);
            if (user != null) {
                // 注册成功，执行相关操作
                // 将用户信息存储到Session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // 重定向到用户首页或其他页面
                response.sendRedirect("user_home.jsp");
            } else {
                // 注册失败，返回错误信息
                request.setAttribute("error", "注册失败");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else if (action.equals("logout")) {
            // 处理登出请求
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            response.sendRedirect("index.jsp");

        }
    }
}
