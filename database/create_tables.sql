use cardb;

-- 创建管理员表
CREATE TABLE Administrator
(
    admin_id      INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255),
    account       VARCHAR(50) UNIQUE,
    password      VARCHAR(255),
    contact_info  VARCHAR(255),
    role          VARCHAR(50),
    creation_date DATE,
    INDEX idx_admin_id (admin_id),
    INDEX idx_account (account)
);

-- 创建客户表
CREATE TABLE Customer
(
    customer_id    INT AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(255),
    account        VARCHAR(50) UNIQUE,
    password       VARCHAR(255),
    contact_info   VARCHAR(255),
    license_number VARCHAR(20),
    address        VARCHAR(255),
    INDEX idx_customer_id (customer_id),
    INDEX idx_account (account),
    INDEX idx_license_number (license_number)
);


-- 创建汽车型号表
CREATE TABLE CarModel
(
    model_id    INT AUTO_INCREMENT PRIMARY KEY, -- 设置自增主键
    brand       VARCHAR(255),
    model_name  VARCHAR(255),
    description TEXT,
    Index idx_model_id (model_id),
    Index idx_brand (brand),
    Index idx_model_name (model_name)
);

-- 创建可租用汽车表
CREATE TABLE Car
(
    car_id           INT AUTO_INCREMENT PRIMARY KEY, -- 设置自增主键
    model_id         INT,
    plate_number     VARCHAR(20),
    color            VARCHAR(50),
    year             INT,
    status           VARCHAR(50),
    daily_rental_fee DECIMAL(16, 2),
    FOREIGN KEY (model_id) REFERENCES CarModel (model_id) on delete cascade,
    INDEX idx_car_id (car_id),
    INDEX idx_plate_number (plate_number)
);

-- 创建租赁记录表
CREATE TABLE RentalRecord
(
    rental_id            INT AUTO_INCREMENT PRIMARY KEY,
    customer_id          INT,
    car_id               INT,
    start_date           DATE,
    expected_return_date DATE,
    actual_return_date   DATE,
    rental_fee           DECIMAL(16, 2),
    status               VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id) on delete cascade,
    FOREIGN KEY (car_id) REFERENCES Car (car_id) on delete cascade,
    INDEX idx_rental_id (rental_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_car_id (car_id)
);

-- 创建违约记录表
CREATE TABLE ViolationRecord
(
    violation_id   INT AUTO_INCREMENT PRIMARY KEY,
    rental_id      INT,
    violation_type VARCHAR(255),
    description    TEXT,
    fine_amount    DECIMAL(16, 2),
    FOREIGN KEY (rental_id) REFERENCES RentalRecord (rental_id) on delete cascade,
    INDEX idx_violation_id (violation_id),
    INDEX idx_rental_id (rental_id)
);

-- 创建还车单表
CREATE TABLE ReturnRecord
(
    return_id                     INT AUTO_INCREMENT PRIMARY KEY,
    rental_id                     INT,
    return_date_time              DATETIME,
    vehicle_condition_description TEXT,
    handling_personnel            INT,
    FOREIGN KEY (rental_id) REFERENCES RentalRecord (rental_id) on delete cascade,
    FOREIGN KEY (handling_personnel) REFERENCES Administrator (admin_id) on delete cascade,
    INDEX idx_return_id (return_id),
    INDEX idx_rental_id (rental_id)
);


-- 创建支付信息表
CREATE TABLE Payment
(
    payment_id     INT AUTO_INCREMENT PRIMARY KEY,
    rental_id      INT,
    customer_id    INT,
    payment_type   VARCHAR(50),
    payment_date   DATE,
    amount         DECIMAL(16, 2),
    payment_method VARCHAR(50),
    FOREIGN KEY (rental_id) REFERENCES RentalRecord (rental_id) on delete cascade,
    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id) on delete cascade,
    INDEX idx_payment_id (payment_id),
    INDEX idx_rental_id (rental_id),
    INDEX idx_customer_id (customer_id)
);


-- 创建系统操作日志表
CREATE TABLE SystemLog
(
    log_id                INT AUTO_INCREMENT PRIMARY KEY,
    operation_type        VARCHAR(255),
    operation_description TEXT,
    operator_id           INT,
    operation_date_time   DATETIME,
    ip_address            VARCHAR(50),
    result                VARCHAR(50),
    FOREIGN KEY (operator_id) REFERENCES Administrator (admin_id),
    INDEX idx_log_id (log_id),
    INDEX idx_operator_id (operator_id)
);
