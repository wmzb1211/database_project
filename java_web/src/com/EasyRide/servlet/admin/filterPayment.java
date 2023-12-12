package com.EasyRide.servlet.admin;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CustomerDao;
import com.EasyRide.dao.PaymentDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.Payment;

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

@WebServlet("/admin/filterPayment")
public class filterPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String RentalId = request.getParameter("rentalId");
        int rentalId = Integer.parseInt(RentalId);
        List<Payment> payments = new ArrayList<>();
        PaymentDao paymentDao = new PaymentDao();
        HttpSession session = request.getSession();

        payments = paymentDao.getPaymentByRentalRecordId(rentalId);
        request.setAttribute("payments",payments);
        request.getRequestDispatcher("/admin/editPayment.jsp").forward(request, response);

    }
}