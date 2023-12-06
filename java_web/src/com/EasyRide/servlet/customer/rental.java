package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.dao.PaymentDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/rental")
public class rental extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int carId = Integer.parseInt(request.getParameter("carId"));
        int rentalDuration = Integer.parseInt(request.getParameter("rentalDuration"));
        double totalRentalFee = Double.parseDouble(request.getParameter("totalRentalFee"));

        // 检查汽车是否已经被租赁
        CarDao carDao = new CarDao();
        Car carCheck = carDao.getCarById(carId);
        if (carCheck.getStatus().equals("Rented")){
            // 弹窗提示汽车已经被租赁
            PrintWriter out = response.getWriter();
            out.print("<script>alert('Car has been rented!');window.location.href='rentalCar.jsp';</script>");
            out.flush();
            return;
        }

        // 创建租赁记录
        RentalRecordDao rentalRecordDao = new RentalRecordDao();
        RentalRecord rentalRecord = rentalRecordDao.addRentalRecord(carId, customerId, rentalDuration);

        // 创建支付记录
        PaymentDao paymentDao = new PaymentDao();
        Payment payment = paymentDao.addPayment(rentalRecord.getRentalId(), customerId, totalRentalFee, "Credit Card", "Rental Fee");

        // 更新车辆状态

        Car car = carDao.getCarById(carId);
        car.setStatus("Rented");
        carDao.updateCar(car);

        // 跳转到成功页面，设置参数，表示租赁成功
        request.setAttribute("rentalRecord", rentalRecord);
        request.setAttribute("payment", payment);
        request.setAttribute("paymentStatus", "Success");

        request.getRequestDispatcher("success.jsp").forward(request, response);

    }
}
