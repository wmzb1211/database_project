package com.EasyRide.servlet.admin;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CarModelDao;
import com.EasyRide.dao.RentalRecordDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.CarModel;
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

@WebServlet("/admin/updateCar")
public class updateCar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session = request.getSession();
        String delete = request.getParameter("delete");
        if (delete!=null){
            int deleteId = Integer.parseInt(delete);
            CarDao carDao = new CarDao();
            carDao.deleteCar(deleteId);
            request.setAttribute("status","Available");
            response.sendRedirect("filterCars");
        }else{
            String plateNumber = request.getParameter("plateNumber");
            int carId = Integer.parseInt(request.getParameter("carId"));
            int modelId = Integer.parseInt(request.getParameter("carModelId"));
            String color = request.getParameter("color");
            int year = Integer.parseInt(request.getParameter("year"));
            String status = request.getParameter("status");
            String description = request.getParameter("description");
            double dailyFee = Double.parseDouble(request.getParameter("fee"));

            CarDao carDao = new CarDao();
            Car carTmp = carDao.getCarById(carId);
            CarModelDao carModelDao = new CarModelDao();
            int idTmp = carTmp.getModelId();
            CarModel carModelTmp = carModelDao.getCarModelById(idTmp);
            if (carTmp != null){
                if(carTmp.getCarId() != carId){
                    // 弹窗提示车牌已经存在
                    PrintWriter out = response.getWriter();
                    out.print("<script>alert('Plate number already exists!');window.location.href='updateProfile.jsp';</script>");
                    out.flush();
                    return;}
            }
            Car car = carDao.getCarById(carId);
            car.setModelId(modelId);
            car.setPlateNumber(plateNumber);
            car.setColor(color);
            car.setYear(year);
            car.setStatus(status);
            car.setDailyRentalFee(dailyFee);
            car.setDescription(description);
            if (carDao.updateCar(car) != null){
                // 弹窗提示修改成功


                PrintWriter out = response.getWriter();
                out.print("<script>alert('Update successfully!');</script>");
                //            response.sendRedirect("/targetPage?carDetailId=" + car.getCarId());
                request.getRequestDispatcher("filterCars").forward(request, response);
                out.flush();
                return;
            }
        }
    }
}


