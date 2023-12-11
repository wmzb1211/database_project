package com.EasyRide.servlet.admin;

import com.EasyRide.dao.CarModelDao;
import com.EasyRide.dao.AdministratorDao;
import com.EasyRide.entity.Administrator;
import com.EasyRide.entity.BrandStats;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

@WebServlet("/admin/statsBrands")
public class statsBrands extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 设置响应类型为CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"brand_stats.csv\"");

        try (PrintWriter out = response.getWriter()) {
            // 调用DAO层获取数据
            CarModelDao dao = new CarModelDao();
            List<BrandStats> statsList = dao.getBrandStats();

            // 写入CSV文件头
            out.println("Brand, Rental Count, Total Rental Days, Total Income");

            // 写入数据
            // 初始化总计变量
            long totalRentalCount = 0;
            long totalRentalDays = 0;
            BigDecimal totalIncome = BigDecimal.valueOf(0);

            // 写入数据并累加总计
            for (BrandStats stats : statsList) {
                out.println(stats.getBrand() + ", " + stats.getRentalCount() + ", " + stats.getTotalRentalDays() + ", " + stats.getTotalIncome());
                totalRentalCount += stats.getRentalCount();
                totalRentalDays += stats.getTotalRentalDays();
                totalIncome = totalIncome.add(stats.getTotalIncome());
            }

            // 写入总计行
            out.println("Total," + totalRentalCount + "," + totalRentalDays + "," + totalIncome);
        } catch (Exception e) {
            throw new ServletException("Error generating csv", e);
        }
        
    }
}