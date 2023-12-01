# 系统操作日志 (SystemLog)
#
# 日志ID (主键)
# 操作类型（如登录、添加记录、修改记录等）
# 操作描述（具体操作内容）
# 操作员ID（外键，指向管理员表）
# 操作日期和时间
# IP地址（记录操作来源）
# 结果（成功、失败、错误信息等）

# Path: datagen\systemLog\main.py
import csv
import faker
import random
import datetime
fake = faker.Faker()

# Generate systemLog records
systemLog_records = []
for systemLog_id in range(1, 51):  # Generate information for 50 systemLogs
    systemLog_records.append({
        'SystemLogID': systemLog_id,
        'OperationType': random.choice(['login', 'add record', 'modify record']),
        'OperationDescription': fake.text(),
        'AdminID': random.randint(1, 20),
        'OperationDate': fake.date_between(start_date='-10y', end_date='today'),
        'IP': fake.ipv4(),
        'Result': random.choice(['success', 'fail', 'error'])
    })

# Write to CSV file
with open('systemLogs.csv', 'w', newline='') as f:
    writer = csv.DictWriter(f, fieldnames=systemLog_records[0].keys())
    writer.writeheader()
    writer.writerows(systemLog_records)
