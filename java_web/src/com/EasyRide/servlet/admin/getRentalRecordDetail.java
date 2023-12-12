package com.EasyRide.servlet.admin;

import com.EasyRide.dao.*;
import com.EasyRide.entity.*;

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

@WebServlet("/admin/getRentalRecordDetail")
public class getRentalRecordDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String RentalRecordId = request.getParameter("rentalRecordId");
        int rentalRecordId = 0;
        if (RentalRecordId!=null) {
            rentalRecordId = Integer.parseInt(RentalRecordId);
        }else{
            PrintWriter out = response.getWriter();
            out.print("<script>alert('error');window.location.href='user.jsp';</script>");
            out.flush();
        }
        CarDao carDao = new CarDao();
        CustomerDao customerDao = new CustomerDao();
        CarModelDao carModelDao = new CarModelDao();
        RentalRecordDao rentalRecordDao = new RentalRecordDao();
        PaymentDao paymentDao = new PaymentDao();
        ReturnRecordDao returnRecordDao = new ReturnRecordDao();

        RentalRecord rentalRecord = rentalRecordDao.getRentalRecordsByID(rentalRecordId);
        Car car = carDao.getCarById(rentalRecord.getCarId());
        CarModel carModel = carModelDao.getCarModelById(car.getModelId());
        Customer customer = customerDao.getCustomerById(rentalRecord.getCustomerId());
        List<Payment> payments = paymentDao.getPaymentByRentalRecordId(rentalRecord.getRentalId());
        List<ReturnRecord> returnRecords = returnRecordDao.getReturnRecordByRentalRecordId(rentalRecord.getRentalId());
        request.setAttribute("cars", car);
        request.setAttribute("model", carModel);
        request.setAttribute("customer", customer);
        request.setAttribute("rentalRecord", rentalRecord);
        request.setAttribute("payments", payments);
        request.setAttribute("returnRecords", returnRecords);
        request.getRequestDispatcher("/admin/editRentalRecord.jsp").forward(request, response);

    }
}
