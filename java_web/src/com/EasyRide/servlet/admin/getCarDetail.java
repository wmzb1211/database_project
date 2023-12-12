package com.EasyRide.servlet.admin;

import com.EasyRide.dao.*;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.CarModel;
import com.EasyRide.entity.Payment;
import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.ReturnRecord;
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

@WebServlet("/admin/getCarDetail")
public class getCarDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String CarID = request.getParameter("carId");
        int carID = 0;
        if (CarID!=null) {
            carID = Integer.parseInt(CarID);
        }else{
            PrintWriter out = response.getWriter();
            out.print("<script>alert('error');window.location.href='user.jsp';</script>");
            out.flush();
        }
        CarDao carDao = new CarDao();
        CarModelDao carModelDao = new CarModelDao();
        RentalRecordDao rentalRecordDao = new RentalRecordDao();
        PaymentDao paymentDao = new PaymentDao();
        ReturnRecordDao returnRecordDao = new ReturnRecordDao();

        Car car = carDao.getCarById(carID);
        CarModel carModel = carModelDao.getCarModelById(car.getModelId());
        List<RentalRecord> rentalRecords = rentalRecordDao.getRentalRecordsByCarID(car.getCarId());
//        List<Payment> payments = null;
//        List<ReturnRecord> returnRecords = null;
//        for (RentalRecord rentalRecord : rentalRecords) {
//            payments = paymentDao.getPaymentByRentalRecordId(rentalRecord.getRentalId());
//            returnRecords = returnRecordDao.getReturnRecordByRentalRecordId(rentalRecord.getRentalId());
//        }
//        request.setAttribute("car", car);
//        request.setAttribute("model", carModel);
//        request.setAttribute("rentalRecords", rentalRecords);
//        request.setAttribute("",payments);
//        request.setAttribute("returnRecords",returnRecords);
//        request.getRequestDispatcher("/admin/editCar.jsp").forward(request, response);
        List<List<Payment>> allPayments = new ArrayList<>();
        List<List<ReturnRecord>> allReturnRecords = new ArrayList<>();
        for (RentalRecord rentalRecord : rentalRecords) {
            List<Payment> payments = paymentDao.getPaymentByRentalRecordId(rentalRecord.getRentalId());
            List<ReturnRecord> returnRecords = returnRecordDao.getReturnRecordByRentalRecordId(rentalRecord.getRentalId());
            allPayments.add(payments);
            allReturnRecords.add(returnRecords);
        }
        request.setAttribute("car", car);
        request.setAttribute("model", carModel);
        request.setAttribute("rentalRecords", rentalRecords);
        request.setAttribute("allPayments", allPayments);
        request.setAttribute("allReturnRecords", allReturnRecords);
        request.getRequestDispatcher("/admin/addCar.jsp").forward(request, response);

    }
}
