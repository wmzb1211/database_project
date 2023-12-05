package com.EasyRide.dao;

import com.EasyRide.entity.Administrator;

public interface AdministratorDao {
    Administrator getAdministrator(String username, String password);
    Administrator addAdministrator(String name, String account, String password, String contactInfo, String role);
    Administrator updateAdministrator(Administrator administrator);
    boolean deleteAdministrator(int adminId);

}
