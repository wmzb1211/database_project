package com.EasyRide.servlet.admin;

import com.EasyRide.dao.AdministratorDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.entity.Administrator;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.RentalRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/updateProfile")
public class updateProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
//        Customer originalCustomer = (Customer) session.getAttribute("admin");
        Administrator originalAdministrator = (Administrator) session.getAttribute("admin");
        String username = request.getParameter("username");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (!password.equals(password2)) {
            PrintWriter out = response.getWriter();
            out.print("<script>alert('Password not match!');window.location.href='updateProfile.jsp';</script>");
            out.flush();
            return;
        }
        String contactInfo = request.getParameter("ContactInfo");

        AdministratorDao admin = new AdministratorDao();
        Administrator adminTmp = admin.getAdministratorByAccount(account);
        if (adminTmp != null && adminTmp.getAdminId() != originalAdministrator.getAdminId()) {
            // 弹窗提示账号已经存在
            PrintWriter out = response.getWriter();
            out.print("<script>alert('Account already exists!');window.location.href='updateProfile.jsp';</script>");
            out.flush();
            return;
        }

        Administrator administrator = admin.getAdministratorById(originalAdministrator.getAdminId());
        administrator.setName(username);
        administrator.setAccount(account);
        administrator.setPassword(password);
        administrator.setContactInfo(contactInfo);
        administrator.setCreationDate(originalAdministrator.getCreationDate());
        administrator.setRole(originalAdministrator.getRole());
        admin.updateAdministrator(administrator);

        if (admin.updateAdministrator(administrator) != null) {
            session.setAttribute("admin", administrator);
            PrintWriter out = response.getWriter();

//            跳转到user.jsp
            session.setAttribute("admin", administrator);
            response.sendRedirect("user.jsp");
            out.print("<script>alert('Update successfully!');window.location.href='updateProfile.jsp';</script>");
            out.flush();

            return;
        } else {
            PrintWriter out = response.getWriter();
            out.print("<script>alert('Update failed!');window.location.href='updateProfile.jsp';</script>");
            out.flush();
            return;
        }

    }
}
