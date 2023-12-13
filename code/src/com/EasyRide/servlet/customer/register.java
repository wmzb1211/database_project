package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CustomerDao;
import com.EasyRide.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/customer/register")
public class register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String name = request.getParameter("name");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String contactInfo = request.getParameter("contactInfo");
        String licenseNumber = request.getParameter("licenseNumber");
        String address = request.getParameter("address");

        CustomerDao customerDao = new CustomerDao();

        Customer customerTmp = customerDao.getCustomerByAccount(account);
        if (customerTmp!= null) {
            String str = "该账号已被注册";
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='register.jsp'");
            out.print("</script>");
            out.close();
            return;
        }

        customerTmp = customerDao.getCustomerByLicenseNumber(licenseNumber);
        if (customerTmp!= null) {
            String str = "该车牌号已被注册";
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='register.jsp'");
            out.print("</script>");
            out.close();
            return;
        }

        Customer customer = new Customer(0, name, account, password, contactInfo, licenseNumber, address);

        //调用添加接口
        customer = customerDao.addCustomer(customer);

        if (customer == null) {
            String str = "注册失败";
            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='register.jsp'");
            out.print("</script>");
            out.close();
        } else {
            // 注册成功，弹窗提示注册成功
            // 将用户信息存储到Session
            // 重定向到user.jsp

            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            response.sendRedirect("user.jsp");

        }
    }
}
