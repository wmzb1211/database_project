Administrator对象模型：

int adminId: 管理员的唯一标识符。
String name: 管理员的姓名。
String account: 管理员的登录账户。
String password: 管理员的登录密码。
String contactInfo: 管理员的联系信息。
String role: 管理员的角色。
Date creationDate: 管理员账户创建的日期。



Brand_Count对象模型：

String Brand: 品牌的名称。 

int Count: 与品牌相关的计数。



BrandStats对象模型：

String brand: 品牌的名称。
long rentalCount: 品牌的租赁次数统计。
long totalRentalDays: 品牌的总租赁天数统计。
BigDecimal totalIncome: 品牌的总收入统计。



Car对象模型：

int carId: 汽车的唯一标识符。
int modelId: 汽车型号的唯一标识符。
String brand: 汽车的品牌。
String modelName: 汽车型号的名称。
String description: 汽车的描述信息。
String plateNumber: 汽车的车牌号。
String color: 汽车的颜色。
int year: 汽车的制造年份。
String status: 汽车的状态。
double dailyRentalFee: 汽车的每日租金费用。



CarModel对象模型：

int modelId: 汽车型号的唯一标识符。
String brand: 汽车型号所属品牌。
String modelName: 汽车型号的名称。
String description: 汽车型号的描述信息。



Customer对象模型：

int customerId: 客户的唯一标识符。
String name: 客户的姓名。
String account: 客户的登录账户。
String password: 客户的登录密码。
String contactInfo: 客户的联系信息。
String licenseNumber: 客户的驾驶证号码。
String address: 客户的地址。



Payment对象模型：

int paymentId: 付款记录的唯一标识符。
int rentalId: 相关租赁的唯一标识符。
String paymentType: 付款类型。
Date paymentDate: 付款日期。
double amount: 付款金额。
String paymentMethod: 付款方式。

RentalRecord对象模型：

int rentalId: 租赁记录的唯一标识符。
int customerId: 租赁的客户的唯一标识符。
int carId: 租赁的汽车的唯一标识符。
Date startDate: 租赁开始日期。
Date expectedReturnDate: 预计归还日期。
Date actualReturnDate: 实际归还日期。
double rentalFee: 租赁费用。
String status: 租赁状态。



ReturnRecord对象模型：

int returnId: 归还记录的唯一标识符。
int rentalId: 相关租赁的唯一标识符。
Date returnDateTime: 归还日期和时间。
String vehicleConditionDescription: 车辆状况描述。
int adminId: 处理归还的管理员的唯一标识符。



SystemLog对象模型：

int logId: 日志记录的唯一标识符。
String operationType: 操作类型。
String operationDescription: 操作描述。
int operatorId: 执行操作的操作员的唯一标识符。
Timestamp operationDateTime: 操作发生的日期和时间。
String ipAddress: 执行操作的IP地址。
String result: 操作执行结果。



ViolationRecord对象模型：

int violationId: 违规记录的唯一标识符。
int rentalId: 相关租赁的唯一标识符。
String violationType: 违规类型。
double fineAmount: 罚款金额。

