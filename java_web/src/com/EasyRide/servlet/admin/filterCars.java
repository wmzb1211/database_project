package com.EasyRide.servlet.admin;

import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CarModelDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.RentalRecord;

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

@WebServlet("/admin/filterCars")
public class filterCars extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // 重写doPost方法
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        Enumeration<String> parameterNames = request.getParameterNames();

        Map<String, String> filterParams = new HashMap();
        List<String> availableParameters = Arrays.asList("brand", "model", "color", "year", "status", "minDailyRentalFee", "maxDailyRentalFee");

        if (parameterNames == null) {
            filterParams.put("status", "Available");
        }

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);

            if (paramValue == null || paramValue.trim().isEmpty() || paramValue.equals("")) {
                continue;
            }
            if (availableParameters.contains(paramName)) {
                filterParams.put(paramName, paramValue);
                System.out.println(paramName + " : " + paramValue);
            }
        }

        CarDao carDao = new CarDao();
        CarModelDao carModelDao = new CarModelDao();
        List<Car> cars = carDao.getCarsByFilter(filterParams);
        List<String> models = carModelDao.getAllBrands();
        Collections.sort(models);
        List<String> colors = carDao.getAllFilterOptions("color");
        Collections.sort(colors);
        List<String> brands = carModelDao.getAllBrands();
        Collections.sort(brands);
        List<String> years = carDao.getAllFilterOptions("year");
        Collections.sort(years);
        List<String> statuses = carDao.getAllFilterOptions("status");

        HttpSession session = request.getSession();
        session.setAttribute("filterParams", filterParams);

        request.setAttribute("cars", cars);
        request.setAttribute("brands", models);
        request.setAttribute("colors", colors);
        request.setAttribute("brands", brands);
        request.setAttribute("years", years);
        request.setAttribute("statuses", statuses);
        request.getRequestDispatcher("filterCar.jsp").forward(request, response);

    }
}