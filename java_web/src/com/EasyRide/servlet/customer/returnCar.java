package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.dao.PaymentDao;
import com.EasyRide.dao.ReturnRecordDao;
import com.EasyRide.entity.*;

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
            response.sendRedirect("login.jsp");
            return;
        }

        int carId = Integer.parseInt(request.getParameter("carId"));
        int rentalRecordId = Integer.parseInt(request.getParameter("rentalRecordId"));
        int extraDuration = Integer.parseInt(request.getParameter("duration"));
        double extraFee = Double.parseDouble(request.getParameter("totalFee"));

        // 创建还车记录
        ReturnRecordDao returnRecordDao = new ReturnRecordDao();
        ReturnRecord returnRecord = returnRecordDao.addReturnRecord(rentalRecordId, "Good", 1);
        if (returnRecord == null) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('还车失败！创建还车记录失败！');");
            out.println("location.href='user.jsp';");
            out.println("</script>");
            out.close();
            return;
        }

        // 创建支付记录
        if (extraDuration > 0) {
            PaymentDao paymentDao = new PaymentDao();
            Payment payment = paymentDao.addPayment(rentalRecordId, customer.getCustomerId(), extraFee, "Credit Card", "Overdue Penalty Fee");
            if (payment == null) {
                PrintWriter out = response.getWriter();
                out.println("<script type='text/javascript'>");
                out.println("alert('还车失败！创建支付记录失败！');");
                out.println("location.href='user.jsp';");
                out.println("</script>");
                out.close();
                return;
            }
        }

        // 更新租赁记录
        RentalRecord rentalRecord = new RentalRecordDao().endRentalRecord(rentalRecordId, extraFee);
        if (rentalRecord == null) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('还车失败！更新租赁记录失败！');");
            out.println("location.href='user.jsp';");
            out.println("</script>");
            out.close();
            return;
        }

        // 更新车辆状态
        CarDao carDao = new CarDao();
        Car car = carDao.getCarById(carId);
        car.setStatus("Available");
        carDao.updateCar(car);

        // 弹窗提示还车成功，跳转到user首页
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('还车成功！');");
        out.println("location.href='user.jsp';");
        out.println("</script>");
        out.close();

    }
}
