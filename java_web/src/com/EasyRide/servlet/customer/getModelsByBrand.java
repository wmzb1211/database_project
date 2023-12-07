package com.EasyRide.servlet.customer;


import com.EasyRide.dao.CarModelDao;
import com.EasyRide.entity.CarModel;
import com.EasyRide.util.HTMLUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/customer/getModelsByBrand")
public class getModelsByBrand extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<CarModel> models = new CarModelDao().getModelsByBrand(request.getParameter("brand"));

        // 构建JSON字符串
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{");
        jsonResponse.append("\"models\": [");

        for (CarModel model : models) {
            jsonResponse.append("{");
            jsonResponse.append("\"model_id\": ").append(model.getModelId()).append(",");
            jsonResponse.append("\"model_name\": \"").append(HTMLUtils.escapeHtml(model.getModelName())).append("\"");
            jsonResponse.append("},");
        }

        // 移除最后一个逗号
        if (!models.isEmpty()) {
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
