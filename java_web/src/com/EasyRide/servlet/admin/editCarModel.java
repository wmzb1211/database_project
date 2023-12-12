package com.EasyRide.servlet.admin;


import com.EasyRide.dao.CarDao;
import com.EasyRide.entity.CarModel;
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


@WebServlet("/admin/editCarModel")
public class editCarModel extends HttpServlet {

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
        String ModelId = request.getParameter("carModelId");
        if (ModelId=="") {

            String brand = request.getParameter("brandName");
            String model = request.getParameter("modelName");

            String description = request.getParameter("description");
            CarModelDao carModelDao = new CarModelDao();
            CarModel carModelTmp = carModelDao.getCarModelByBrandAndModelName(brand,model);
            if (carModelTmp != null) {
                PrintWriter out = response.getWriter();
                out.print("<script>alert('Car model already exists!');window.location.href='filterCarModel.jsp';</script>");
                out.flush();
                return;
            }
//        carDao.addCar(car);

            if (carModelDao.addCarModel(brand,model,description) != null) {
                // 弹窗提示添加成功
                PrintWriter out = response.getWriter();
                out.print("<script>alert('Add successfully!');</script>");
//                request.getRequestDispatcher("/admin/filterCarModel").forward(request, response);
                response.sendRedirect("filterCarModel?brandSelect=" + brand);
                out.flush();
            }
        } else {
            int carModelId = Integer.parseInt(ModelId);

            String brand = request.getParameter("brand");
            String modelName = request.getParameter("modelName");
            String description = request.getParameter("description");

            CarModelDao carModelDao = new CarModelDao();
            CarModel carModel = carModelDao.getCarModelById(carModelId);
            carModel.setModelName(modelName);
            carModel.setBrand(brand);
            carModel.setModelId(carModelId);
            carModel.setDescription(description);
            if (carModelDao.updateCarModel(carModel) != null){
                // 弹窗提示修改成功

                PrintWriter out = response.getWriter();
                out.print("<script>alert('Update successfully!');");
//                request.getRequestDispatcher("/admin/filterCarModel").forward(request, response);
                response.sendRedirect("filterCarModel?brandSelect=" + carModel.getBrand());
                out.flush();
                return;
            }

        }
    }
}


