package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.dao.PaymentDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.Customer;
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

@WebServlet("/customer/returnCar")
public class returnCar extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            response.sendRedirect("/customer/login");
            return;
        }
        int carId = Integer.parseInt(request.getParameter("carId"));
        int rentalRecordId = Integer.parseInt(request.getParameter("rentalRecordId"));
        int extraDuration = Integer.parseInt(request.getParameter("rentalDuration"));
        double extraFee = Double.parseDouble(request.getParameter("totalRentalFee"));

        // 创建还车记录


        // 创建支付记录
        if (extraDuration > 0) {
            PaymentDao paymentDao = new PaymentDao();
            Payment payment = paymentDao.addPayment(rentalRecordId, customer.getCustomerId(), extraFee, "Credit Card", "Overdue Penalty Fee");
        }

        // 更新租赁记录
        RentalRecord rentalRecord = new RentalRecordDao().endRentalRecord(rentalRecordId, extraFee);

        // 更新车辆状态
        CarDao carDao = new CarDao();
        Car car = carDao.getCarById(carId);
        car.setStatus("Available");
        carDao.updateCar(car);

        // 弹窗提示还车成功，跳转到user首页
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('还车成功！');");
        out.println("location.href='/customer/user.jsp';");
        out.println("</script>");
        out.close();

    }
}
