package com.EasyRide.servlet.admin;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CustomerDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.Customer;

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

@WebServlet("/admin/filterCustomer")
public class filterCustomer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String selectType = request.getParameter("selectType");
        String typeValue = request.getParameter("filterValue");
        List<Customer> customers = new ArrayList<>();
        CustomerDao customerDao = new CustomerDao();
        HttpSession session = request.getSession();
        if (selectType==""){
            customers = customerDao.getAllCustomers();
        }else{
            if("customerId".equals(selectType)){
                int customerId = Integer.parseInt(typeValue);
                customers.add(customerDao.getCustomerById(customerId));
            } else if ("account".equals(selectType)) {
                customers.add(customerDao.getCustomerByAccount(typeValue));
            } else if ("licenseNumber".equals(selectType)) {
                customers.add(customerDao.getCustomerByLicenseNumber(typeValue));
            }
        }
        request.setAttribute("customers",customers);
        request.getRequestDispatcher("filterCustomer.jsp").forward(request, response);

    }
}