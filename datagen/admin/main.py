# 管理员 (Administrator)
#
# 管理员ID (主键)
# 姓名
# 用户名
# 密码（加密存储）
# 联系信息（电话号码、电子邮件等）
# 角色（如普通管理员、高级管理员等）
# 创建日期

# Path: datagen\admin\main.py
import csv
import faker
import random
import datetime
fake = faker.Faker()

# Generate admin records
admin_records = []
for admin_id in range(1, 21):  # Generate information for 20 admins
    admin_records.append({
        'AdminID': admin_id,
        'Name': fake.name(),
        'Username': fake.user_name(),
        'Password': fake.password(),
        'ContactInfo': fake.phone_number(),
        'Role': random.choice(['simple admin', 'senior admin']),
        'CreateDate': fake.date_between(start_date='-10y', end_date='today')
    })

# Write to CSV file
with open('admins.csv', 'w', newline='') as f:
    writer = csv.DictWriter(f, fieldnames=admin_records[0].keys())
    writer.writeheader()
    writer.writerows(admin_records)
