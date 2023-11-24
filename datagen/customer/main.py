import csv
from faker import Faker

fake = Faker()

# 生成500个客户信息
customers = []
for _ in range(500):
    customer = {
        'customer ID': str(_+1),
        'customer name': fake.name(),
        'phone number': fake.phone_number(),
        'email': fake.email(),
        'drive license': fake.random_int(min=100000, max=999999),
        'address': fake.address(),
    }
    customers.append(customer)

# 写入CSV文件
csv_file_path = 'customer.csv'
fieldnames = ['customer ID', 'customer name', 'phone number', 'email', 'drive license', 'address']

with open(csv_file_path, mode='w', newline='') as file:
    writer = csv.DictWriter(file, fieldnames=fieldnames)

    # 写入标题
    writer.writeheader()

    # 写入数据
    writer.writerows(customers)

print(f'生成的客户信息已保存到 {csv_file_path}')
