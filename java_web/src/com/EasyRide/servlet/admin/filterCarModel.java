package com.EasyRide.servlet.admin;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CarModelDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.CarModel;

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

@WebServlet("/admin/filterCarModel")
public class filterCarModel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String brand = request.getParameter("brandSelect");
        List<CarModel> models = new ArrayList<>();
        CarModelDao carModelDao = new CarModelDao();
        List<String> brands = carModelDao.getAllBrands();
        Collections.sort(brands);
        HttpSession session = request.getSession();
        if (brand==""){
            models = carModelDao.getAllCarModels();
        }else{
            models =carModelDao.getModelsByBrand(brand);
        }
        request.setAttribute("brands",brands);
        request.setAttribute("models",models);
        request.getRequestDispatcher("filterCarModel.jsp").forward(request, response);

    }
}