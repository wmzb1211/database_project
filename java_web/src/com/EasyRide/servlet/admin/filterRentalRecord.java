package com.EasyRide.servlet.admin;

import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.entity.RentalRecord;
import com.EasyRide.entity.Customer;

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

@WebServlet("/admin/filterRentalRecord")
public class filterRentalRecord extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String selectType = request.getParameter("selectType");
        String typeValue = request.getParameter("filterValue");
        List<RentalRecord> rentalRecords = new ArrayList<>();
        RentalRecordDao rentalRecordDao = new RentalRecordDao();
        HttpSession session = request.getSession();
        if (selectType==""){
            rentalRecords = rentalRecordDao.getAllRentalRecords();
        }else{
            if("rentalId".equals(selectType)){
                int rentalRecordId = Integer.parseInt(typeValue);
                rentalRecords.add( rentalRecordDao.getRentalRecordsByID(rentalRecordId));
            } else if ("customerId".equals(selectType)) {
                int customerId = Integer.parseInt(typeValue);
                rentalRecords = rentalRecordDao.getRentalRecordsByCustomerID(customerId,null);
            } else if ("carId".equals(selectType)) {
                int carId = Integer.parseInt(typeValue);
                rentalRecords = rentalRecordDao.getRentalRecordsByCarID(carId);
            } else if ("status".equals(selectType)) {
                rentalRecords = rentalRecordDao.getRentalRecordsByStatus(typeValue);
            }

        }
        request.setAttribute("rentalRecords",rentalRecords);
        request.getRequestDispatcher("/admin/filterRentalRecord.jsp").forward(request, response);

    }
}