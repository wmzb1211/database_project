//package com.EasyRide.servlet.admin;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Date;
//import java.util.*;
//
//@WebServlet("/admin/operation")
//public class operation extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doPost(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        String operation = request.getParameter("operation");
//        switch (operation) {
//            case "addCar":
//                addCar(request, response);
//                addCarModel(request, response);
//                break;
//
//        }a