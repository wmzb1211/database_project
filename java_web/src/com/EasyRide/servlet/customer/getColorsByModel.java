package com.EasyRide.servlet.customer;


import com.EasyRide.dao.CarDao;
import com.EasyRide.dao.CarModelDao;
import com.EasyRide.entity.Car;
import com.EasyRide.entity.CarModel;
import com.EasyRide.util.HTMLUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/customer/getColorsByModel")
public class getColorsByModel extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String brand = request.getParameter("brand");
        String model = request.getParameter("model");

        CarModel carModel = new CarModelDao().getCarModelByBrandAndModelName(brand, model);
        List<Car> cars = new CarDao().getCarsByModelId(carModel.getModelId());
        List<String> colors = new ArrayList<>();

        for (Car car : cars) {
            if (!colors.contains(car.getColor())) {
                colors.add(car.getColor());
            }
        }

        // 构建JSON字符串
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{");
        jsonResponse.append("\"colors\": [");

        for (String color : colors) {
            jsonResponse.append("{");
            jsonResponse.append("\"color_id\": ").append(color).append(",");
            jsonResponse.append("\"color\": \"").append(HTMLUtils.escapeHtml(color)).append("\"");
            jsonResponse.append("},");
        }

        // 移除最后一个逗号
        if (!colors.isEmpty()) {
            jsonResponse.setLength(jsonResponse.length() - 1);
        }

        jsonResponse.append("]");
        jsonResponse.append("}");

        // 设置响应类型为JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 发送JSON响应
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
