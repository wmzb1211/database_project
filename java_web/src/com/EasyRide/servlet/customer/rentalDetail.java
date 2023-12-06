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

@WebServlet("/customer/rentalDetail")
public class rentalDetail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("customerId") == null) {
            response.sendRedirect("/customer/login.jsp");
            return;
        }

        Customer customer = (Customer) session.getAttribute("customer");

        int rentalRecordId = Integer.parseInt(request.getParameter("rentalRecordId"));
        int customerId = customer.getCustomerId();





    }
}
