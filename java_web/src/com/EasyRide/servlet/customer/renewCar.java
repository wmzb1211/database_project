package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CustomerDao;
import com.EasyRide.dao.PaymentDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.Payment;
import com.EasyRide.entity.RentalRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/customer/renewCar")
public class renewCar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Customer customer = (Customer) request.getSession().getAttribute("customer");
        int customerId = customer.getCustomerId();

        int rentalRecordId = Integer.parseInt(request.getParameter("rentalRecordId"));
        int renewDuration = Integer.parseInt(request.getParameter("duration"));
        double totalRentalFee = Double.parseDouble(request.getParameter("totalFee"));

        // 修改租赁记录
        RentalRecordDao rentalRecordDao = new RentalRecordDao();
        RentalRecord rentalRecord = rentalRecordDao.getRentalRecordsByID(rentalRecordId);

        Date expectedReturnDate = new Date(rentalRecord.getExpectedReturnDate().getTime() + (long) renewDuration * 24 * 60 * 60 * 1000);
        rentalRecord.setExpectedReturnDate(expectedReturnDate);
        rentalRecord.setActualReturnDate(null);
        rentalRecord.setRentalFee(rentalRecord.getRentalFee() + totalRentalFee);
        new RentalRecordDao().updateRentalRecord(rentalRecord);

        // 创建支付记录
        PaymentDao paymentDao = new PaymentDao();
        Payment payment = paymentDao.addPayment(rentalRecordId, customerId, totalRentalFee, "cash", "Renew Fee");

        // 弹窗提示，租赁成功，返回到user界面
        PrintWriter out = response.getWriter();
        out.print("<script>alert('续借成功!');window.location.href='user.jsp';</script>");
    }
}