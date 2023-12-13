package com.EasyRide.servlet.admin;


import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CustomerDao;
import com.EasyRide.entity.CarModel;
import com.EasyRide.entity.Car;
import com.EasyRide.dao.CarModelDao;
import com.EasyRide.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/admin/editCustomer")
public class editCustomer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering doPost method");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
        String CustomerId = request.getParameter("customerId");
        if ("".equals(CustomerId)) {
            String name = request.getParameter("name");
            String account = request.getParameter("account");
            String password = request.getParameter("password");
            String contactInfo = request.getParameter("contactInfo");
            String licenseNumber = request.getParameter("licenseNumber");
            String address = request.getParameter("address");
            CustomerDao customerDao = new CustomerDao();
            Customer customerTmp = customerDao.getCustomerByAccount(account);
            if (customerTmp != null) {
                PrintWriter out = response.getWriter();
                out.print("<script>alert('Account already exists!');</script>");
                out.flush();
                return;
            }
            customerTmp = customerDao.getCustomerByLicenseNumber(licenseNumber);
            if (customerTmp != null) {
                PrintWriter out = response.getWriter();
                out.print("<script>alert('License Number already exists!');</script>");
                out.flush();
                return;
            }

            Customer newCustomer = new Customer();
            newCustomer.setAccount(account);
            newCustomer.setName(name);
            newCustomer.setPassword(password);
            newCustomer.setContactInfo(contactInfo);
            newCustomer.setLicenseNumber(licenseNumber);
            newCustomer.setAddress(address);
            if (customerDao.addCustomer(newCustomer) != null) {
                // 弹窗提示添加成功
                PrintWriter out = response.getWriter();
                out.print("<script>alert('Add successfully!');</script>");
//                request.getRequestDispatcher("/admin/filterCustomer.jsp").forward(request, response);
//                /admin/filterCustomer?selectType=licenseNumber&filterValue=f
                response.sendRedirect("filterCustomer?selectType=account&filterValue=" + account);
                out.flush();

            }
        } else {
            int customerId = Integer.parseInt(CustomerId);
            CustomerDao customerDao = new CustomerDao();
            String customerName = request.getParameter("customerName");
            String account = request.getParameter("account");
            String licenseNumber = request.getParameter("licenseNumber");
            String contactInfo = request.getParameter("contactInfo");
            String address = request.getParameter("address");

            Customer newCustomer = customerDao.getCustomerById(customerId);

            newCustomer.setAccount(account);
            newCustomer.setName(customerName);
            newCustomer.setContactInfo(contactInfo);
            newCustomer.setLicenseNumber(licenseNumber);
            newCustomer.setAddress(address);

            if (customerDao.updateCustomer(newCustomer) != null){
                // 弹窗提示修改成功

                PrintWriter out = response.getWriter();
                out.print("<script>alert('Update successfully!');");
//               request.getRequestDispatcher("/admin/filterCarModel").forward(request, response);
                request.getRequestDispatcher("editCustomer.jsp").forward(request, response);
                out.flush();
                return;
            }

        }
    }
}


