package com.EasyRide.servlet.admin;


import com.EasyRide.dao.CarDao;
import com.EasyRide.entity.Customer;
import com.EasyRide.entity.Car;
import com.EasyRide.dao.CarModelDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/admin/addCar")
public class addCar extends HttpServlet {

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
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");

        String color = request.getParameter("color");
        String description = request.getParameter("description");
        CarModelDao carModelDao = new CarModelDao();
        int modelId = carModelDao.getCarModelByBrandAndModelName(brand,model).getModelId();
        int year = Integer.parseInt(request.getParameter("year"));
        double dailyFee = Double.parseDouble(request.getParameter("dailyFee"));
        String plateNumber = request.getParameter("plateNumber");
        CarDao carDao = new CarDao();
        Car carTmp = carDao.getCarByPlateNumber(plateNumber);
        if (carTmp != null){
            // 弹窗提示车牌已经存在
            PrintWriter out = response.getWriter();
            out.print("<script>alert('Plate Number already exists!');window.location.href='updateProfile.jsp';</script>");
            out.flush();
            return;
        }

        Car car = new Car();
        car.setStatus("Available");
        car.setBrand(brand);
        car.setModelName(model);
        car.setPlateNumber(plateNumber);
        car.setModelId(modelId);
        car.setColor(color);
        car.setDescription(description);
        car.setYear(year);
        car.setDailyRentalFee(dailyFee);
//        carDao.addCar(car);

        if (carDao.addCar(car) != null){
            // 弹窗提示修改成功

            session.setAttribute("car", car);

            PrintWriter out = response.getWriter();
            out.print("<script>alert('Add successfully!');</script>");
            request.getRequestDispatcher("/admin/filterCars").forward(request, response);
            out.flush();
        }

    }
}


