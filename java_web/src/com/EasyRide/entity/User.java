package com.EasyRide.entity;

import com.EasyRide.dao.UserDaoImpl;
import java.util.Date;

public class User {
//    CREATE TABLE User (
//            user_id INT PRIMARY KEY,
//            username VARCHAR(50) NOT NULL UNIQUE,
//    password_hash VARCHAR(255) NOT NULL,
//    email VARCHAR(100) NOT NULL UNIQUE,
//    registration_date DATE NOT NULL,
//    last_login_date DATETIME,
//    role ENUM('customer', 'employee', 'admin') NOT NULL
//  );
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private String registrationDate;
    private String lastLoginDate;
    private String role;

    public User(int id, String username, String passwordHash, String email, String registrationDate, String lastLoginDate, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.registrationDate = registrationDate;
        this.lastLoginDate = lastLoginDate;
        this.role = role;
    }

    public User(String username, String passwordHash, String email) {
        this.id = -1;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = "customer";

        UserDaoImpl userDao = new UserDaoImpl();
        User newUser = userDao.addUser(this);

        this.id = newUser.getId();
        this.registrationDate = newUser.getRegistrationDate();
        this.lastLoginDate = newUser.getLastLoginDate();
    }

    public String getUserByUsername(String username) {
        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.getUserByUsername(username);
        return user.toString();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(String lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        // 返回JSON格式的字符串
        return "{\"id\":" + id +
                ",\"username\":\"" + username +
                "\",\"passwordHash\":\"" + passwordHash +
                "\",\"email\":\"" + email +
                "\",\"registrationDate\":\"" + registrationDate +
                "\",\"lastLoginDate\":\"" + lastLoginDate +
                "\",\"role\":\"" + role + "\"}";
    }

    public static void main(String[] args) {
        User user = new User("test", "test", "test@test.com");
        System.out.println(user.toString());

        User user2 = new User("test2", "test2", "test2@test.com");
        System.out.println(user2.toString());

        System.out.println(user.getUserByUsername("test"));
    }

}
