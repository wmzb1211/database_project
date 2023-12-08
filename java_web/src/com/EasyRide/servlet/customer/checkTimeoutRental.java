package com.EasyRide.servlet.customer;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.dao.PaymentDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.Payment;
import com.EasyRide.util.HTMLUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@WebServlet("/customer/checkTimeoutRental")
public class checkTimeoutRental extends HttpServlet {
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
        if (customer == null) {
            // 未登录，跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/customer/login.jsp");
        }

        String function = request.getParameter("function");
        int carId = Integer.parseInt(request.getParameter("carId"));
        int rentalRecordId = Integer.parseInt(request.getParameter("rentalRecordId"));
        int duration = Integer.parseInt(request.getParameter("duration"));

        // 获得其中超时的记录
        List<RentalRecord> timeoutRentalRecords = checkTimeoutRental.getTimeoutRentalRecords(customer.getCustomerId());

        if (!timeoutRentalRecords.isEmpty()) {
            // 有超时的记录，弹窗提示无法还车，并展示超时的记录，重定向回主页
            PrintWriter out = response.getWriter();

            StringBuilder Msg = new StringBuilder("您有未还车的订单，请处理后再续租！" +
                    "超时订单如下：");
            for (RentalRecord rentalRecord : timeoutRentalRecords) {
                Car car = new CarDao().getCarById(rentalRecord.getCarId());
                Msg.append("\\n" + "订单号：").append(rentalRecord.getRentalId())
                        .append("车型：").append(car.getBrand()).append(car.getModelName())
                        .append("，车牌号：").append(car.getPlateNumber())
                        .append("，应还日期：").append(rentalRecord.getExpectedReturnDate());
            }

            out.println("<script type='text/javascript'>");
            out.println("alert('" + Msg +"');");
            out.println("location.href='/customer/user.jsp';");
            out.println("</script>");
            out.close();
        } else {
            Car car = new CarDao().getCarById(carId);

//            request.setAttribute("function", function);
//            request.setAttribute("carId", carId);
//            request.setAttribute("rentalRecordId", rentalRecordId);
//            request.setAttribute("paymentType", "Renew Fee");
//            request.setAttribute("paymentDetails", car.toString());
//            request.setAttribute("duration", duration);
//
//            request.getRequestDispatcher("/customer/payment.jsp").forward(request, response);

            if (function.equals("/customer/renewCar")) {
                RentalRecord rentalRecord = new RentalRecordDao().getRentalRecordsByID(rentalRecordId);
                String url = "/customer/payment.jsp?" +
                        "function=" + "/customer/renewCar" +
                        "&carId=" + rentalRecord.getCarId() +
                        "&rentalRecordId=" + rentalRecordId +
                        "&paymentType=Renew Fee" +
                        "&paymentDetails=" + " " +
                        "&dailyRentalFee=" + car.getDailyRentalFee() +
                        "&duration=" + duration;

                response.sendRedirect(url);

            } else if (function.equals("/customer/rentalCar")) {
                String url = "/customer/payment.jsp?" +
                        "function=" + "/customer/rentalCar" +
                        "&carId=" + carId +
                        "&rentalRecordId=0" +
                        "&paymentType=Rental Fee" +
                        "&paymentDetails=" + " " +
                        "&dailyRentalFee=" + car.getDailyRentalFee() +
                        "&duration=" + duration;
                System.out.println(url);
                response.sendRedirect(url);
            } else {
                response.sendRedirect("/customer/user.jsp");
            }

        }
    }

    private static List<RentalRecord> getTimeoutRentalRecords(int customerId) {
        RentalRecordDao rentalRecordDao = new RentalRecordDao();

        List<RentalRecord> rentalRecords = rentalRecordDao.getRentalRecordsByCustomerID(customerId, "Ongoing");
        List<RentalRecord> timeoutRentalRecords = new ArrayList<>();

        // 获得今天的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date today = new Date(calendar.getTimeInMillis());

        for(RentalRecord rentalRecord : rentalRecords){
            calendar.setTime(rentalRecord.getExpectedReturnDate());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Date expectedReturnDate = new Date(calendar.getTimeInMillis());
            if(today.after(expectedReturnDate)){
                timeoutRentalRecords.add(rentalRecord);
            }
        }
        return timeoutRentalRecords;
    }

}