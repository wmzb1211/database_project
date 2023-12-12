# 构件：DAO
# 1.功能描述
# 2.对象模型
# 3.接口规范
## 1.源程序列表
src/com/EasyRide/DAO/AdministratorDao.java
src/com/EasyRide/DAO/CarDao.java
src/com/EasyRide/DAO/CarModelDao.java
src/com/EasyRide/DAO/CustomerDao.java
src/com/EasyRide/DAO/RentalRecordDao.java
src/com/EasyRide/DAO/ReturnRecordDao.java
src/com/EasyRide/DAO/SystemLogDao.java
src/com/EasyRide/DAO/ViolationRecordDao.java
src/com/EasyRide/DAO/PaymentDao.java
## 2.设计思想

## 3.主要设计结构
## 4.实现描述
### 4.1 类1：AdministratorDao
#### 4.1.1 类描述
AdministratorDAO是一个用于操作管理员信息的数据访问对象（DAO）。它提供了对管理员信息的检索、添加、更新和删除等操作，与数据库进行交互，实现对管理员表的 CRUD 操作。
#### 4.1.2 类的基本属性
connection: 数据库连接对象，用于与数据库建立连接。
preparedStatement: 数据库预处理语句对象，用于执行 SQL 查询。
resultSet: 数据库结果集对象，用于存储查询结果。
administrator: 管理员对象，用于存储从数据库中检索到的管理员信息。
#### 4.1.3 类的方法
##### 4.1.3.1 方法1：getAdministrator
方法原型:

public Administrator getAdministrator(String username, String password)
功能描述:
该方法通过提供的用户名和密码从数据库中检索管理员信息。

参数:

username：管理员的用户名。
password：管理员的密码。
返回值:

返回一个Administrator对象，表示匹配提供的用户名和密码的管理员。如果没有找到匹配的管理员，返回null。
异常处理:

在发生SQLException时，打印堆栈轨迹。
##### 4.1.3.2 方法2: getAdministratorByAccount
方法原型:


public Administrator getAdministratorByAccount(String account)
功能描述:
该方法通过提供的管理员账号从数据库中检索管理员信息。

参数:

account：管理员的账号。
返回值:

返回一个Administrator对象，表示匹配提供的账号的管理员。如果没有找到匹配的管理员，返回null。
异常处理:

在发生SQLException时，打印堆栈轨迹。
##### 4.1.3.3 方法3: getAdministratorById
方法原型:


public Administrator getAdministratorById(int adminID)
功能描述:
该方法通过提供的管理员ID从数据库中检索管理员信息。

参数:

adminID：管理员的ID。
返回值:

返回一个Administrator对象，表示匹配提供的管理员ID的管理员。如果没有找到匹配的管理员，返回null。
异常处理:

在发生SQLException时，打印堆栈轨迹。
##### 4.1.3.4 方法4: addAdministrator
方法原型:


public Administrator addAdministrator(Administrator administrator)
功能描述:
该方法向数据库中添加新的管理员信息。

参数:

administrator：一个Administrator对象，表示要添加的管理员信息。其中，adminId不需要设置或者设置为0。
返回值:

返回一个Administrator对象，表示成功添加到数据库中的管理员信息，包含数据库自动生成的adminId。如果添加失败，返回null。
异常处理:

在发生SQLException时，根据情况打印相应信息。
##### 4.1.3.5 方法5: updateAdministrator
方法原型:

public Administrator updateAdministrator(Administrator administrator)
功能描述:
该方法更新数据库中的管理员信息。

参数:

administrator：一个Administrator对象，其中adminId必须正确，不能修改。
返回值:

返回一个Administrator对象，表示成功更新的管理员信息。如果更新失败，返回null。
异常处理:

在发生SQLException时，根据情况打印相应信息。
##### 4.1.3.6 方法6: deleteAdministrator
方法原型:

public boolean deleteAdministrator(int adminId)
功能描述:
该方法从数据库中删除指定ID的管理员信息。

参数:

adminId：管理员的ID。
返回值:

返回true表示成功删除，返回false表示删除失败。
异常处理:

在发生SQLException时，打印堆栈轨迹。