package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.RentalRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet("/customer/checkReturnTime")
public class checkReturnTime extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        int rentalId = Integer.parseInt(request.getParameter("rentalRecordId"));
        RentalRecord rentalRecord = new RentalRecordDao().getRentalRecordsByID(rentalId);

        if (rentalRecord.getStatus().equals("Completed")) {
            // 弹窗提示已经还车，返回到user.jsp
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('该车已经还车，请勿重复操作！');");
            out.println("location.href='/customer/user.jsp';");
            out.println("</script>");
        }
        if (rentalRecord.getStatus().equals("Cancelled")) {
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('该订单已经被取消，无法还车！');");
            out.println("location.href='/customer/user.jsp';");
            out.println("</script>");
        }

        Date expectedReturnDate = rentalRecord.getExpectedReturnDate();
        // 获取当前时间
        Date actualReturnDate = new Date(System.currentTimeMillis());
        // 以日为单位进行比较
        // 将日期转换为毫秒数
        long expectedReturnTime = expectedReturnDate.getTime();
        long actualReturnTime = actualReturnDate.getTime();
        // 计算日期差异（以天为单位）
        long dayDifference = (actualReturnTime - expectedReturnTime) / (24 * 60 * 60 * 1000);


        if (dayDifference > 0) {
            PrintWriter out = response.getWriter();
            // 计算还车所需的额外费用
            Car car = new CarDao().getCarById(rentalRecord.getCarId());
            double extraFee = dayDifference * car.getDailyRentalFee() * 3;
//            String script = "if (confirm('还车已经超时，您需要支付的额外费用为：" + extraFee + "元，是否继续还车？')) {";
//            script += "  window.location.href ='payment.jsp?" +
//                    "function=" + "/customer/returnCar" +
//                    "&carId=" + car.getCarId() +
//                    "&rentalRecordId=" + rentalId +
//                    "&paymentType=Overdue Penalty Fee" +
//                    "&paymentDetails=" + "You are " + dayDifference + " day(s) late to return the car." +
//                    "&dailyRentalFee=" +  extraFee +
//                    "&duration=" + dayDifference +
//                    "';";
//            script += "} else {";
//            script += "  history.back();"; // 如果用户点击否，显示提示消息
//            script += "}";
//            out.println("<script>" + script + "</script>");

            request.setAttribute("function", "/customer/returnCar");
            request.setAttribute("car", car);
            request.setAttribute("rentalRecordId", rentalRecord.getRentalId());
            request.setAttribute("paymentType", "Overdue Penalty Fee");
            request.setAttribute("paymentDetails", "You are " + dayDifference + " day(s) late to return the car.");
            request.setAttribute("duration", dayDifference);

            request.getRequestDispatcher("/customer/payment.jsp").forward(request, response);

        } else {
            // 还车，弹窗确认是否要还车
            // 使用JavaScript弹窗
//            PrintWriter out = response.getWriter();
//            String script = "if (confirm('您确定要还车吗？')) {";
//            script += "  window.location.href = 'returnCar?" +
//                    "rentalRecordId=" + rentalId +
//                    "&carId=" + rentalRecord.getCarId() +
//                    "&duration=0" +
//                    "&totalFee=0" +
//                    "';";
//            script += "} else {";
//            script += "  history.back();"; // 如果用户点击否，显示提示消息
//            script += "}";
//            out.println("<script>" + script + "</script>");

            request.getRequestDispatcher("/customer/returnCar?" +
                            "rentalRecordId=" + rentalId +
                            "&carId=" + rentalRecord.getCarId() +
                            "&duration=0" +
                            "&totalFee=0")
                    .forward(request, response);


        }

    }
}