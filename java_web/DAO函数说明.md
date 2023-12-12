# 构件：DAO
# 1.功能描述

#### 1.1 CarDao

- 功能描述:
  - 提供对车辆信息的增、删、改、查等数据库操作。
  - 支持按照车型、品牌、状态等条件查询车辆信息。
  - 实现车辆租赁状态的更新和租赁记录的获取。

#### 1.2 CarModelDao

- 功能描述:
  - 提供对车型信息的增、删、改、查等数据库操作。
  - 支持按照品牌、车型名称等条件查询车型信息。

#### 1.3 RentalRecordDao

- 功能描述:
  - 提供对租赁记录的增、删、改、查等数据库操作。
  - 支持按照租赁记录ID、顾客ID、车辆ID、状态等条件查询租赁记录。
  - 实现根据顾客ID和状态获取租赁记录、获取指定车辆的租赁记录、获取品牌租赁统计等操作。
  - 实现租赁记录的添加、结束和更新。

#### 1.4 ReturnRecordDao

- 功能描述:
  - 提供对归还记录的增、查等数据库操作。
  - 支持按照租赁记录ID查询归还记录。
  - 实现归还记录的添加。

#### 1.5 SystemLogDao

- 功能描述:
  - 提供对系统日志的增、查等数据库操作。
  - 支持按照操作员ID查询系统日志。
  - 实现系统日志的添加。

#### 1.6 ViolationRecordDao

- 功能描述:
  - 提供对违规记录的增、查等数据库操作。
  - 支持按照归还记录ID查询违规记录。
  - 实现违规记录的添加。

# 2.对象模型

#### 2.1 CarDao

- 对象模型:
  - `Car`: 车辆实体类。
  - `DBConnectionPool`: 数据库连接池，用于获取数据库连接。
- 其他说明:
  - CarDao 使用 Car 对象表示车辆信息。

#### 2.2 CarModelDao

- 对象模型:
  - `CarModel`: 车型实体类。
  - `DBConnectionPool`: 数据库连接池，用于获取数据库连接。
- 其他说明:
  - CarModelDao 使用 CarModel 对象表示车型信息。

#### 2.3 RentalRecordDao

- 对象模型:
  - `RentalRecord`: 租赁记录实体类。
  - `Brand_Count`: 统计品牌租赁次数的实体类。
  - `DBConnectionPool`: 数据库连接池，用于获取数据库连接。
- 其他说明:
  - RentalRecordDao 使用 RentalRecord 和 Brand_Count 对象表示租赁记录和品牌租赁统计。

#### 2.4 ReturnRecordDao

- 对象模型:
  - `ReturnRecord`: 归还记录实体类。
  - `DBConnectionPool`: 数据库连接池，用于获取数据库连接。
- 其他说明:
  - ReturnRecordDao 使用 ReturnRecord 对象表示归还记录。

#### 2.5 SystemLogDao

- 对象模型:
  - `SystemLog`: 系统日志实体类。
  - `DBConnectionPool`: 数据库连接池，用于获取数据库连接。
- 其他说明:
  - SystemLogDao 使用 SystemLog 对象表示系统日志。

#### 2.6 ViolationRecordDao

- 对象模型:
  - `ViolationRecord`: 违规记录实体类。
  - `DBConnectionPool`: 数据库连接池，用于获取数据库连接。
- 其他说明:
  - ViolationRecordDao 使用 ViolationRecord 对象表示违规记录。

# 3.接口规范

#### 3.1 数据库连接池接口

- 接口规范:
  - `DBConnectionPool`: 提供数据库连接的获取和释放方法。

#### 3.2 DAO 接口

- 接口规范:
  - `CarDao`: 提供车辆信息的增、删、改、查等方法。
  - `CarModelDao`: 提供车型信息的增、删、改、查等方法。
  - `RentalRecordDao`: 提供租赁记录的增、删、改、查等方法。
  - `ReturnRecordDao`: 提供归还记录的增、查等方法。
  - `SystemLogDao`: 提供系统日志的增、查等方法。
  - `ViolationRecordDao`: 提供违规记录的增、查等方法。

#### 3.3 实体类接口

- 接口规范:
  - `Car`: 车辆实体类。
  - `CarModel`: 车型实体类。
  - `RentalRecord`: 租赁记录实体类。
  - `ReturnRecord`: 归还记录实体类。
  - `SystemLog`: 系统日志实体类。
  - `ViolationRecord`: 违规记录实体类。

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

`AdministratorDAO` 是一个用于操作管理员信息的数据访问对象（DAO）。它提供了对管理员信息的检索、添加、更新和删除等操作，与数据库进行交互，实现对管理员表的 CRUD 操作。

#### 4.1.2 类的基本属性

- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。
- `administrator`: 管理员对象，用于存储从数据库中检索到的管理员信息。

#### 4.1.3 类的方法

##### 4.1.3.1 方法1：`getAdministrator`

**方法原型:**

```java
public Administrator getAdministrator(String username, String password)
```

**功能描述:**
该方法通过提供的用户名和密码从数据库中检索管理员信息。

**参数:**
- `username`: 管理员的用户名。
- `password`: 管理员的密码。

**返回值:**
返回一个 `Administrator` 对象，表示匹配提供的用户名和密码的管理员。如果没有找到匹配的管理员，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.1.3.2 方法2: `getAdministratorByAccount`

**方法原型:**
```java
public Administrator getAdministratorByAccount(String account)
```

**功能描述:**
该方法通过提供的管理员账号从数据库中检索管理员信息。

**参数:**
- `account`: 管理员的账号。

**返回值:**
返回一个 `Administrator` 对象，表示匹配提供的账号的管理员。如果没有找到匹配的管理员，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.1.3.3 方法3: `getAdministratorById`

**方法原型:**
```java
public Administrator getAdministratorById(int adminID)
```

**功能描述:**
该方法通过提供的管理员ID从数据库中检索管理员信息。

**参数:**
- `adminID`: 管理员的ID。

**返回值:**
返回一个 `Administrator` 对象，表示匹配提供的管理员ID的管理员。如果没有找到匹配的管理员，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.1.3.4 方法4: `addAdministrator`

**方法原型:**
```java
public Administrator addAdministrator(Administrator administrator)
```

**功能描述:**
该方法向数据库中添加新的管理员信息。

**参数:**
- `administrator`: 一个 `Administrator` 对象，表示要添加的管理员信息。其中，`adminId` 不需要设置或者设置为0。

**返回值:**
返回一个 `Administrator` 对象，表示成功添加到数据库中的管理员信息，包含数据库自动生成的 `adminId`。如果添加失败，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，根据情况打印相应信息。

##### 4.1.3.5 方法5: `updateAdministrator`

**方法原型:**
```java
public Administrator updateAdministrator(Administrator administrator)
```

**功能描述:**
该方法更新数据库中的管理员信息。

**参数:**
- `administrator`: 一个 `Administrator` 对象，其中 `adminId` 必须正确，不能修改。

**返回值:**
返回一个 `Administrator` 对象，表示成功更新的管理员信息。如果更新失败，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，根据情况打印相应信息。

##### 4.1.3.6 方法6: `deleteAdministrator`

**方法原型:**
```java
public boolean deleteAdministrator(int adminId)
```

**功能描述:**
该方法从数据库中删除指定ID的管理员信息。

**参数:**
- `adminId`: 管理员的ID。

**返回值:**
返回 `true` 表示成功删除，返回 `false` 表示删除失败。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

### 4.2 类2：CarDao

#### 4.2.1 类描述

`CarDao` 是一个用于操作车辆信息的数据访问对象（DAO）。它提供了对车辆信息的检索、添加、更新和删除等操作，与数据库进行交互，实现对车辆表和车型表的 CRUD 操作。

#### 4.2.2 类的基本属性

- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。
- `Car`: 车辆对象，用于存储从数据库中检索到的车辆信息。

#### 4.2.3 类的方法

##### 4.2.3.1 方法1：`getAllCars`

**方法原型:**
```java
public List<Car> getAllCars()
```

**功能描述:**
该方法返回所有车辆的信息，包括已出租和未出租的。

**返回值:**
返回一个 `List<Car>` 对象，包含所有车辆的信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.2 方法2：`getCarById`

**方法原型:**
```java
public Car getCarById(int id)
```

**功能描述:**
通过提供的车辆ID从数据库中检索车辆信息。

**参数:**
- `int id`: 车辆ID。

**返回值:**
返回一个 `Car` 对象，表示匹配提供的ID的车辆。如果没有找到匹配的车辆，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.3 方法3：`getCarsByStatus`

**方法原型:**
```java
public List<Car> getCarsByStatus(String status)
```

**功能描述:**
返回某状态

的所有车辆。

**参数:**
- `String status`: 车辆状态。

**返回值:**
返回一个 `List<Car>` 对象，包含符合提供状态的所有车辆的信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.4 方法4：`getCarsByModelId`

**方法原型:**
```java
public List<Car> getCarsByModelId(int modelId)
```

**功能描述:**
返回某车型的所有车辆。

**参数:**
- `int modelId`: 车型ID。

**返回值:**
返回一个 `List<Car>` 对象，包含符合提供车型ID的所有车辆的信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.5 方法5：`getCarsByFilter`

**方法原型:**
```java
public List<Car> getCarsByFilter(Map<String, String> filterParams)
```

**功能描述:**
返回符合筛选条件的所有车辆，支持多种筛选条件，如品牌、颜色、年份、状态等。

**参数:**
- `Map<String, String> filterParams`: 筛选条件，其中 `key` 为筛选项，`value` 为对应的值。

**返回值:**
返回一个 `List<Car>` 对象，包含符合提供筛选条件的所有车辆的信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.6 方法6：`addCar`

**方法原型:**
```java
public Car addCar(Car car)
```

**功能描述:**
添加车辆到数据库中。

**参数:**
- `Car car`: 要添加的车辆对象。

**返回值:**
返回一个 `Car` 对象，表示成功添加的车辆，包含数据库自动生成的车辆ID。如果添加失败，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.7 方法7：`updateCar`

**方法原型:**
```java
public Car updateCar(Car car)
```

**功能描述:**
更新数据库中的车辆信息。

**参数:**
- `Car car`: 包含更新信息的车辆对象，其中车辆ID必须存在，不允许更新ID，可以更新其他信息。

**返回值:**
返回一个 `Car` 对象，表示成功更新的车辆。如果更新失败，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.2.3.8 方法8：`deleteCar`

**方法原型:**
```java
public boolean deleteCar(int id)
```

**功能描述:**
从数据库中删除指定ID的车辆。

**参数:**
- `int id`: 要删除的车辆的ID。

**返回值:**
返回一个 `boolean` 值，表示删除成功（`true`）或删除失败（`false`）。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

### 4.3 类3：CarModelDao

#### 4.3.1 类描述

`CarModelDao` 是一个用于操作车型信息的数据访问对象（DAO）。它提供了对车型信息的检索、添加、更新和删除等操作，与数据库进行交互，实现对车型表的 CRUD 操作。

#### 4.3.2 类的基本属性

- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。
- `CarModel`: 车型对象，用于存储从数据库中检索到的车型信息。

#### 4.3.3 类的方法

##### 4.3.3.1 方法1：`getCarModelById`

**方法原型:**
```java
public CarModel getCarModelById(int id)
```

**功能描述:**
通过提供的车型ID从数据库中检索车型信息。

**参数:**
- `int id`: 车型ID。

**返回值:**
返回一个 `CarModel` 对象，表示匹配提供的ID的车型。如果没有找到匹配的车型，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.3.3.2 方法2：`getCarModelByBrandAndModelName`

**方法原型:**

```java
public CarModel getCarModelByBrandAndModelName(String brand, String modelName)
```

**功能描述:**
通过提供的品牌和车型名称从数据库中检索车型信息。

**参数:**
- `String brand`: 车型品牌。
- `String modelName`: 车型名称。

**返回值:**
返回一个 `CarModel` 对象，表示匹配提供的品牌和车型名称的车型。如果没有找到匹配的车型，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.3.3.3 方法3：`getAllCarModels`

**方法原型:**
```java
public List<CarModel> getAllCarModels()
```

**功能描述:**
返回所有车型的信息。

**返回值:**
返回一个 `List<CarModel>` 对象，包含所有车型的信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.3.3.4 方法4：`addCarModel`

**方法原型:**

```java
public CarModel addCarModel(String brand, String modelName, String description)
```

**功能描述:**
添加车型到数据库中。

**参数:**
- `String brand`: 车型品牌。
- `String modelName`: 车型名称。
- `String description`: 车型描述。

**返回值:**
返回一个 `CarModel` 对象，表示成功添加的车型，包含数据库自动生成的车型ID。如果添加失败，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

---

##### 4.3.3.5 方法5：`updateCarModel`

**方法原型:**

```java
public CarModel updateCarModel(CarModel carModel)
```

**功能描述:**
更新数据库中的车型信息。

**参数:**
- `CarModel carModel`: 包含更新信息的车型对象，其中车型ID必须存在，不允许更新ID，可以更新其他信息。

**返回值:**
返回一个 `CarModel` 对象，表示成功更新的车型。如果更新失败，返回 `null`。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

---

##### 4.3.3.6 方法6：`getAllBrands`

**方法原型:**

```java
public List<String> getAllBrands()
```

**功能描述:**
获取所有车型的品牌。

**返回值:**
返回一个 `List<String>` 对象，包含所有车型的品牌。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

---

##### 4.3.3.7 方法7：`getModelsByBrand`

**方法原型:**
```java
public List<CarModel> getModelsByBrand(String brand)
```

**功能描述:**
获取指定品牌的所有车型。

**参数:**
- `String brand`: 车型品牌。

**返回值:**
返回一个 `List<CarModel>` 对象，包含指定品牌的所有车型的信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

---

##### 4.3.3.8 方法8：`getBrandStats`

**方法原型:**
```java
public List<BrandStats> getBrandStats()
```

**功能描述:**
获取每个品牌的统计信息，包括租赁数量、总租赁天数和总收入。

**返回值:**
返回一个 `List<BrandStats>` 对象，包含每个品牌的统计信息。

**异常处理:**
在发生 `SQLException` 时，打印堆栈轨迹。

---

### 4.4 类4：CustomerDao

#### 4.4.1 类描述

`CustomerDao` 是一个用于操作客户信息的数据访问对象（DAO）。它提供了对客户信息的检索、添加、更新和删除等操作，与数据库进行交互，实现对客户表的 CRUD 操作。

#### 4.4.2 类的基本属性

- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。
- `Customer`: 客户对象，用于存储从数据库中检索到的客户信息。

#### 4.4.3 类的方法

##### 4.4.3.1 方法1：`getAllCustomers`

**方法原型:**
```java
public List<Customer> getAllCustomers()
```

**功能描述:**
返回所有客户的信息。

**返回值:**
返回一个 `List<Customer>` 对象，包含所有客户的信息。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹。

---

##### 4.4.3.2 方法2：`getCustomerById`

**方法原型:**
```java
public Customer getCustomerById(int id)
```

**功能描述:**
通过提供的客户ID从数据库中检索客户信息。

**参数:**
- `int id`: 客户ID。

**返回值:**
返回一个 `Customer` 对象，表示匹配提供的ID的客户。如果没有找到匹配的客户，返回 `null`。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹。

---

##### 4.4.3.3 方法3：`getCustomerByLicenseNumber`

**方法原型:**
```java
public Customer getCustomerByLicenseNumber(String licenseNumber)
```

**功能描述:**
通过提供的驾驶证号从数据库中检索客户信息。

**参数:**
- `String licenseNumber`: 驾驶证号。

**返回值:**
返回一个 `Customer` 对象，表示匹配提供的驾驶证号的客户。如果没有找到匹配的客户，返回 `null`。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹。

---

##### 4.4.3.4 方法4：`getCustomerByAccount`

**方法原型:**
```java
public Customer getCustomerByAccount(String account)
```

**功能描述:**
通过提供的账号从数据库中检索客户信息。

**参数:**
- `String account`: 客户账号。

**返回值:**
返回一个 `Customer` 对象，表示匹配提供的账号的客户。如果没有找到匹配的客户，返回 `null`。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹。

---

##### 4.4.3.5 方法5：`getCustomer`

**方法原型:**
```java
public Customer getCustomer(String account, String password)
```

**功能描述:**
通过提供的账号和密码从数据库中检索客户信息。

**参数:**
- `String account`: 客户账号。
- `String password`: 客户密码。

**返回值:**
返回一个 `Customer` 对象，表示匹配提供的账号和密码的客户。如果没有找到匹配的客户，或密码不正确，返回 `null`。

**异常处理:**
在发生 `Exception` 时，打

印堆栈轨迹。

---

##### 4.4.3.6 方法6：`addCustomer`

**方法原型:**
```java
public Customer addCustomer(Customer customer)
```

**功能描述:**
添加客户到数据库中。

**参数:**
- `Customer customer`: 要添加的客户对象。

**返回值:**
返回一个 `Customer` 对象，表示成功添加的客户，包含数据库自动生成的客户ID。如果添加失败，返回 `null`。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹，并检查是否为账号已存在的异常，如果是则输出 "Account already exists"。

---

##### 4.4.3.7 方法7：`updateCustomer`

**方法原型:**
```java
public Customer updateCustomer(Customer customer)
```

**功能描述:**
更新数据库中的客户信息。

**参数:**
- `Customer customer`: 包含更新信息的客户对象，其中客户ID必须存在，不允许更新ID，可以更新其他信息。

**返回值:**
返回一个 `Customer` 对象，表示成功更新的客户。如果更新失败，返回 `null`。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹，并检查是否为账号已存在的异常，如果是则输出 "Account already exists"。

---

##### 4.4.3.8 方法8：`deleteCustomer`

**方法原型:**

```java
public boolean deleteCustomer(int customerId)
```

**功能描述:**
通过提供的客户ID删除客户。如果客户不存在，则返回 `false`，否则返回 `true`。

**参数:**
- `int customerId`: 要删除的客户ID。

**返回值:**
返回一个 `boolean` 值，表示删除操作是否成功。

**异常处理:**
在发生 `Exception` 时，打印堆栈轨迹。

### 4.5 类5：PaymentDao

#### 4.5.1 类描述
`PaymentDao` 是一个用于操作支付信息的数据访问对象（DAO）。它提供了对支付信息的检索、添加和更新等操作，与数据库进行交互，实现对支付表的相应操作。

#### 4.5.2 类的基本属性
- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。

#### 4.5.3 类的方法

##### 4.5.3.1 方法1：`getPaymentByRentalRecordId`
- **方法原型:**
  ```java
  public List<Payment> getPaymentByRentalRecordId(int rentalRecordId)
  ```
- **功能描述:**
  通过租赁记录ID从数据库中检索支付信息列表。
- **参数:**
  - `int rentalRecordId`: 租赁记录ID。
- **返回值:**
  返回一个 `List<Payment>` 对象，包含所有匹配提供的租赁记录ID的支付信息。如果没有找到匹配的支付信息，返回空列表。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.5.3.2 方法2：`getPaymentByStartDate`
- **方法原型:**
  ```java
  public double getPaymentByStartDate(Date startDate)
  ```
- **功能描述:**
  通过提供的开始日期从数据库中检索支付信息的总和。
- **参数:**
  - `Date startDate`: 开始日期。
- **返回值:**
  返回一个 `double` 值，表示从提供的开始日期开始的支付信息总和。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.5.3.3 方法3：`getPaymentInPastYear`
- **方法原型:**
  ```java
  public List<Double> getPaymentInPastYear()
  ```
- **功能描述:**
  获取过去一年内每个月的支付总额。
- **返回值:**
  返回一个 `List<Double>` 对象，包含过去一年内每个月的支付总额。列表顺序按照时间逆序排列。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.5.3.4 方法4：`addPayment`
- **方法原型:**
  ```java
  public Payment addPayment(int rentalRecordId, int customerId, double amount, String paymentMethod, String paymentType)
  ```
- **功能描述:**
  向数据库中添加一条支付信息。
- **参数:**
  - `int rentalRecordId`: 租赁记录ID。
  - `int customerId`: 客户ID。
  - `double amount`: 支付金额。
  - `String paymentMethod`: 支付方式。
  - `String paymentType`: 支付类型。
- **返回值:**
  返回一个 `Payment` 对象，表示成功添加的支付信息。如果添加失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.5.3.5 方法5：`updatePayment`
- **方法原型:**
  ```java
  public Payment updatePayment(Payment payment)
  ```
- **功能描述:**
  更新数据库中的支付信息。
- **参数:**
  - `Payment payment`: 包含更新信息的支付对象，其中支付ID必须存在，不允许更新ID，可以更新其他信息。
- **返回值:**
  返回一个 `Payment` 对象，表示成功更新的支付信息。如果更新失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

#### 4.5.4 补充说明
1. 在 `getPaymentByStartDate` 方法中，通过提供的开始日期从数据库中检索支付信息的总和，返回的是一个 `double` 类型的值，表示从提供的开始日期开始的支付信息总和。

2. 在 `getPaymentInPastYear` 方法中，获取过去一年内每个月的支付总额，返回的是一个 `List<Double>` 对象，该列表包含过去一年内每个月的支付总额。列表顺序按照时间逆序排列。

3. 在 `addPayment` 方法中，添加一条支付信息到数据库中，通过 `returnGeneratedKeys()` 方法获取自增的 `paymentId`，用 `affectedRows` 判断是否添加成功。成功则返回 `Payment` 对象，其中包含数据库自动生成的 `paymentId`，否则抛出异常。

4. 在 `updatePayment` 方法中，更新数据库中的支付信息，如果更新成功，返回更新后的 `Payment` 对象，否则返回 `null`。

### 4.6 类6：RentalRecordDao

#### 4.6.1 类描述
`RentalRecordDao` 是一个用于操作租赁记录的数据访问对象（DAO）。它提供了对租赁记录的检索、添加、更新以及处理违规和支付等操作，与数据库进行交互，实现对租赁记录表的相应操作。

#### 4.6.2 类的基本属性
- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。

#### 4.6.3 类的方法

##### 4.6.3.1 方法1：`getRentalRecordsByID`
- **方法原型:**
  ```java
  public RentalRecord getRentalRecordsByID(int id)
  ```
- **功能描述:**
  通过租赁记录ID从数据库中检索租赁记录。
- **参数:**
  - `int id`: 租赁记录ID。
- **返回值:**
  返回一个 `RentalRecord` 对象，包含与提供的租赁记录ID相匹配的租赁记录。如果没有找到匹配的租赁记录，返回 `null`。
- **异常处理:**
  在发生 `Exception` 时，打印堆栈轨迹。

##### 4.6.3.2 方法2：`getRentalRecordsByCustomerID`
- **方法原型:**
  ```java
  public List<RentalRecord> getRentalRecordsByCustomerID(int customerId, String status)
  ```
- **功能描述:**
  通过客户ID和状态（可选）从数据库中检索租赁记录列表。
- **参数:**
  - `int customerId`: 客户ID。
  - `String status`: 租赁记录状态，可为 `null`。
- **返回值:**
  返回一个 `List<RentalRecord>` 对象，包含与提供的客户ID和状态相匹配的租赁记录。如果没有找到匹配的租赁记录，返回空列表。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.3 方法3：`getRentalRecordsByCarID`
- **方法原型:**
  ```java
  public List<RentalRecord> getRentalRecordsByCarID(int carId)
  ```
- **功能描述:**
  通过车辆ID从数据库中检索租赁记录列表。
- **参数:**
  - `int carId`: 车辆ID。
- **返回值:**
  返回一个 `List<RentalRecord>` 对象，包含与提供的车辆ID相匹配的租赁记录。如果没有找到匹配的租赁记录，返回空列表。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.4 方法4：`getBrandCountAccordingBrand`
- **方法原型:**
  ```java
  public List<Brand_Count> getBrandCountAccordingBrand()
  ```
- **功能描述:**
  获取不同品牌的租赁记录数量统计。
- **返回值:**
  返回一个 `List<Brand_Count>` 对象，包含每个品牌的租赁记录数量统计。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.5 方法5：`getRentalRecordByFilter`
- **方法原型:**
  ```java
  public List<RentalRecord> getRentalRecordByFilter(Map<String, String> filterParams)
  ```
- **功能描述:**
  根据过滤参数从数据库中检索租赁记录列表。
- **参数:**
  - `Map<String, String> filterParams`: 过滤参数的键值对。
- **返回值:**
  返回一个 `List<RentalRecord>` 对象，包含与提供的过滤参数相匹配的租赁记录。如果没有找到匹配的租赁记录，返回空列表。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.6 方法6：`getAllRentalRecords`
- **方法原型:**
  ```java
  public List<RentalRecord> getAllRentalRecords()
  ```
- **功能描述:**
  从数据库中检索所有租赁记录。
- **返回值:**
  返回一个 `List<RentalRecord>` 对象，包含所有租赁记录。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.7 方法7：`addRentalRecord`
- **方法原型:**
  ```java
  public RentalRecord addRentalRecord(int carId, int customerId, int duration)
  ```
- **功能描述:**
  添加一条租赁记录到数据库。
- **参数:**
  - `int carId`: 车辆ID。
  - `int customerId`: 客户ID。
  - `int duration`: 租赁时长（天数）。
- **返回值:**
  返回一个 `RentalRecord` 对象，表示成功添加的租赁记录。如果添加失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.8 方法8：`endRentalRecord`
- **方法原型:**
  ```java
  public RentalRecord endRentalRecord(int id, double extraFee)
  ```
- **功能描述:**
  结束租赁记录，更新实际归还日期、租金和状态。
- **参数:**
  - `int id`: 租赁记录ID。
  - `double extraFee`: 额外费用。
- **返回值:**
  返回一个 `RentalRecord` 对象，表示成功更新的租赁记录。如果更新失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.

9 方法9：`updateRentalRecord`
- **方法原型:**
  ```java
  public RentalRecord updateRentalRecord(RentalRecord rentalRecord)
  ```
- **功能描述:**
  更新数据库中的租赁记录。
- **参数:**
  - `RentalRecord rentalRecord`: 包含更新信息的租赁记录对象，其中租赁记录ID必须存在，不允许更新ID，可以更新其他信息。
- **返回值:**
  返回一个 `RentalRecord` 对象，表示成功更新的租赁记录。如果更新失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.6.3.10 方法10：`processViolationAndPayment`
- **方法原型:**
  ```java
  public boolean processViolationAndPayment(int rentalRecordId, String violationType, String violationDescription, double fineAmount, String paymentMethod)
  ```
- **功能描述:**
  处理违规记录和支付，更新租赁记录状态。
- **参数:**
  - `int rentalRecordId`: 租赁记录ID。
  - `String violationType`: 违规类型。
  - `String violationDescription`: 违规描述。
  - `double fineAmount`: 罚款金额。
  - `String paymentMethod`: 支付方式。
- **返回值:**
  返回一个 `boolean` 值，表示处理是否成功。成功返回 `true`，失败返回 `false`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

#### 4.6.4 补充说明
1. 在 `getRentalRecordsByCustomerID` 方法中，通过提供的客户ID和状态（可选）从数据库中检索租赁记录列表，返回的是一个 `List<RentalRecord>` 对象，包含与提供的客户ID和状态相匹配的租赁记录。如果状态为 `null`，则返回所有状态的租赁记录。

2. 在 `getBrandCountAccordingBrand` 方法中，获取不同品牌的租赁记录数量统计，返回的是一个 `List<Brand_Count>` 对象，该列表包含每个品牌的租赁记录数量统计。

3. 在 `getRentalRecordByFilter` 方法中，根据过滤参数从数据库中检索租赁记录列表，返回的是一个 `List<RentalRecord>` 对象，包含与提供的过滤参数相匹配的租赁记录。

4. 在 `addRentalRecord` 方法中，添加一条租赁记录到数据库，通过 `returnGeneratedKeys()` 方法获取自增的 `rentalId`，用 `affectedRows` 判断是否添加成功。成功则返回 `RentalRecord` 对象，其中包含数据库自动生成的 `rentalId`，否则返回 `null`。

5. 在 `endRentalRecord` 方法中，结束租赁记录，更新实际归还日期、租金和状态，返回的是一个 `RentalRecord` 对象，表示成功更新的租赁记录。如果更新失败，返回 `null`。

6. 在 `updateRentalRecord` 方法中，更新数据库中的租赁记录，返回的是一个 `RentalRecord` 对象，表示成功更新的租赁记录。如果更新失败，返回 `null`。

7. 在 `processViolationAndPayment` 方法中，处理违规记录和支付，更新租赁记录状态，返回的是一个 `boolean` 值，表示处理是否成功。成功返回 `true`，失败返回 `false`。

### 4.7 类7：ReturnRecordDao

#### 4.7.1 类描述
`ReturnRecordDao` 是一个用于操作还车记录的数据访问对象（DAO）。它提供了对还车记录的检索、添加、更新以及添加违规记录等操作，与数据库进行交互，实现对还车记录表的相应操作。

#### 4.7.2 类的基本属性
- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。

#### 4.7.3 类的方法

##### 4.7.3.1 方法1：`getReturnRecordByRentalRecordId`
- **方法原型:**
  ```java
  public List<ReturnRecord> getReturnRecordByRentalRecordId(int rentalRecordId)
  ```
- **功能描述:**
  通过租赁记录ID从数据库中检索对应的还车记录列表。
- **参数:**
  - `int rentalRecordId`: 租赁记录ID。
- **返回值:**
  返回一个 `List<ReturnRecord>` 对象，包含与提供的租赁记录ID相匹配的还车记录。如果没有找到匹配的还车记录，返回空列表。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.7.3.2 方法2：`addReturnRecord`
- **方法原型:**
  ```java
  public ReturnRecord addReturnRecord(int rentalRecordId, String vehicleConditionDescription, int adminId)
  ```
- **功能描述:**
  添加一条还车记录到数据库。
- **参数:**
  - `int rentalRecordId`: 租赁记录ID。
  - `String vehicleConditionDescription`: 车况描述。
  - `int adminId`: 处理人员ID。
- **返回值:**
  返回一个 `ReturnRecord` 对象，表示成功添加的还车记录。如果添加失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.7.3.3 方法3：`updateReturnRecord`
- **方法原型:**
  ```java
  public ReturnRecord updateReturnRecord(ReturnRecord returnRecord)
  ```
- **功能描述:**
  更新数据库中的还车记录。
- **参数:**
  - `ReturnRecord returnRecord`: 包含更新信息的还车记录对象，其中还车记录ID必须存在，不允许更新ID，可以更新其他信息。
- **返回值:**
  返回一个 `ReturnRecord` 对象，表示成功更新的还车记录。如果更新失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.7.3.4 方法4：`addViolationRecord`
- **方法原型:**
  ```java
  public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount)
  ```
- **功能描述:**
  添加一条违规记录到数据库。
- **参数:**
  - `int returnRecordId`: 还车记录ID。
  - `String violationType`: 违规类型。
  - `String violationDescription`: 违规描述。
  - `double fineAmount`: 罚款金额。
- **返回值:**
  返回一个 `ViolationRecord` 对象，表示成功添加的违规记录。如果添加失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

#### 4.7.4 补充说明
1. 在 `getReturnRecordByRentalRecordId` 方法中，通过提供的租赁记录ID从数据库中检索对应的还车记录列表，返回的是一个 `List<ReturnRecord>` 对象，包含与提供的租赁记录ID相匹配的还车记录。

2. 在 `addReturnRecord` 方法中，添加一条还车记录到数据库，通过 `returnGeneratedKeys()` 方法获取自增的 `returnId`，用 `affectedRows` 判断是否添加成功。成功则返回 `ReturnRecord` 对象，其中包含数据库自动生成的 `returnId`，否则返回 `null`。

3. 在 `updateReturnRecord` 方法中，更新数据库中的还车记录，返回的是一个 `ReturnRecord` 对象，表示成功更新的还车记录。如果更新失败，返回 `null`。

4. 在 `addViolationRecord` 方法中，添加一条违规记录到数据库，返回的是一个 `ViolationRecord` 对象，表示成功添加的违规记录。如果添加失败，返回 `null`。

### 4.8 类8：SystemLogDao

#### 4.8.1 类描述
`SystemLogDao` 是一个用于操作系统日志的数据访问对象（DAO）。它提供了对系统日志的检索和添加操作，与数据库进行交互，实现对系统日志表的相应操作。

#### 4.8.2 类的基本属性
- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。

#### 4.8.3 类的方法

##### 4.8.3.1 方法1：`getSystemLogbyOperatorId`
- **方法原型:**
  ```java
  public List<SystemLog> getSystemLogbyOperatorId(int operatorId)
  ```
- **功能描述:**
  通过操作员ID从数据库中检索相应的系统日志列表。
- **参数:**
  - `int operatorId`: 操作员ID。
- **返回值:**
  返回一个 `List<SystemLog>` 对象，包含与提供的操作员ID相匹配的系统日志。如果没有找到匹配的系统日志，返回空列表。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.8.3.2 方法2：`addSystemLog`
- **方法原型:**
  ```java
  public SystemLog addSystemLog(String operationType, String operationDescription, int operatorId, Timestamp operationDateTime, String ipAddress, String result)
  ```
- **功能描述:**
  添加一条系统日志到数据库。
- **参数:**
  - `String operationType`: 操作类型。
  - `String operationDescription`: 操作描述。
  - `int operatorId`: 操作员ID。
  - `Timestamp operationDateTime`: 操作日期时间。
  - `String ipAddress`: IP地址。
  - `String result`: 操作结果。
- **返回值:**
  返回一个 `SystemLog` 对象，表示成功添加的系统日志。如果添加失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

#### 4.8.4 补充说明
1. 在 `getSystemLogbyOperatorId` 方法中，通过提供的操作员ID从数据库中检索相应的系统日志列表，返回的是一个 `List<SystemLog>` 对象，包含与提供的操作员ID相匹配的系统日志。

2. 在 `addSystemLog` 方法中，添加一条系统日志到数据库，通过 `returnGeneratedKeys()` 方法获取自增的 `logId`，用 `affectedRows` 判断是否添加成功。成功则返回 `SystemLog` 对象，其中包含数据库自动生成的 `logId`，否则返回 `null`。

### 4.9 类9：ViolationRecordDao

#### 4.9.1 类描述
`ViolationRecordDao` 是一个用于操作违规记录的数据访问对象（DAO）。它提供了对违规记录的添加和检索操作，与数据库进行交互，实现对违规记录表的相应操作。

#### 4.9.2 类的基本属性
- `connection`: 数据库连接对象，用于与数据库建立连接。
- `preparedStatement`: 数据库预处理语句对象，用于执行 SQL 查询。
- `resultSet`: 数据库结果集对象，用于存储查询结果。

#### 4.9.3 类的方法

##### 4.9.3.1 方法1：`addViolationRecord`
- **方法原型:**
  ```java
  public ViolationRecord addViolationRecord(int returnRecordId, String violationType, String violationDescription, double fineAmount)
  ```
- **功能描述:**
  添加一条违规记录到数据库。
- **参数:**
  - `int returnRecordId`: 归还记录ID。
  - `String violationType`: 违规类型。
  - `String violationDescription`: 违规描述。
  - `double fineAmount`: 罚款金额。
- **返回值:**
  返回一个 `ViolationRecord` 对象，表示成功添加的违规记录。如果添加失败，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

##### 4.9.3.2 方法2：`getViolationByRentalRecordId`
- **方法原型:**
  ```java
  public ViolationRecord getViolationByRentalRecordId(int returnRecordId)
  ```
- **功能描述:**
  通过归还记录ID从数据库中检索相应的违规记录。
- **参数:**
  - `int returnRecordId`: 归还记录ID。
- **返回值:**
  返回一个 `ViolationRecord` 对象，包含与提供的归还记录ID相匹配的违规记录。如果没有找到匹配的违规记录，返回 `null`。
- **异常处理:**
  在发生 `SQLException` 时，打印堆栈轨迹。

#### 4.9.4 补充说明
1. 在 `addViolationRecord` 方法中，添加一条违规记录到数据库，通过 `returnGeneratedKeys()` 方法获取自增的 `violationId`，用 `affectedRows` 判断是否添加成功。成功则返回 `ViolationRecord` 对象，其中包含数据库自动生成的 `violationId`，否则返回 `null`。

2. 在 `getViolationByRentalRecordId` 方法中，通过提供的归还记录ID从数据库中检索相应的违规记录，返回的是一个 `ViolationRecord` 对象，包含与提供的归还记录ID相匹配的违规记录。如果没有找到匹配的违规记录，返回 `null`。
