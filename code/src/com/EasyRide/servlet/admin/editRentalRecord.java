package com.EasyRide.servlet.admin;


import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.entity.CarModel;
import com.EasyRide.entity.Car;
import com.EasyRide.dao.CarModelDao;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.RentalRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/admin/editRentalRecord")
public class editRentalRecord extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering doPost method");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
        String RentalRecordId = request.getParameter("rentalRecordId");
        int rentalRecordId = Integer.parseInt(RentalRecordId);
        RentalRecordDao rentalRecordDao = new RentalRecordDao();
        String RentalFee = request.getParameter("fee");
        double rentalFee = Double.parseDouble(RentalFee);
        RentalRecord newRentalRecord = rentalRecordDao.getRentalRecordsByID(rentalRecordId);
        newRentalRecord.setRentalFee(rentalFee);

        if (rentalRecordDao.updateRentalRecord(newRentalRecord) != null){
            // 弹窗提示修改成功

            PrintWriter out = response.getWriter();
            out.print("<script>alert('Update successfully!');");
//              request.getRequestDispatcher("/admin/filterCarModel").forward(request, response);
            response.sendRedirect("filterRentalRecord?selectType=rentalId&filterValue=" + rentalRecordId);
            out.flush();
            return;
        }
    }
}


