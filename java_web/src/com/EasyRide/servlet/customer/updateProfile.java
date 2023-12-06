package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CustomerDao;
import com.EasyRide.dao.RentalRecordDao;
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

@WebServlet("/customer/updateProfile")
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
        Customer originalCustomer = (Customer) session.getAttribute("customer");


        String account = request.getParameter("account");
        String licenseNumber = request.getParameter("licenseNumber");

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String contactInfo = request.getParameter("contactInfo");
        String address = request.getParameter("address");

        CustomerDao customerDao = new CustomerDao();
        Customer customerTmp = customerDao.getCustomerByAccount(account);
        if (customerTmp != null && customerTmp.getCustomerId() != originalCustomer.getCustomerId()){
            // 弹窗提示账号已经存在
            PrintWriter out = response.getWriter();
            out.print("<script>alert('Account already exists!');window.location.href='updateProfile.jsp';</script>");
            out.flush();
            return;
        }

        customerTmp = customerDao.getCustomerByLicenseNumber(licenseNumber);
        if (customerTmp != null && customerTmp.getCustomerId() != originalCustomer.getCustomerId()){
            // 弹窗提示驾照号码已经存在
            PrintWriter out = response.getWriter();
            out.print("<script>alert('License number already exists!');window.location.href='updateProfile.jsp';</script>");
            out.flush();
            return;
        }

        Customer customer = customerDao.getCustomerById(originalCustomer.getCustomerId());
        customer.setAccount(account);
        customer.setLicenseNumber(licenseNumber);
        customer.setName(name);
        customer.setPassword(password);
        customer.setContactInfo(contactInfo);
        customer.setAddress(address);
        customerDao.updateCustomer(customer);

        if (customerDao.updateCustomer(customer) != null){
            // 弹窗提示修改成功

            session.setAttribute("customer", customer);

            PrintWriter out = response.getWriter();
            out.print("<script>alert('Update successfully!');window.location.href='user.jsp';</script>");
            out.flush();
            return;
        }

    }
}


