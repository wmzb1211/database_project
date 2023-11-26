import faker
import csv
# 随机生成10个人名并编号，写入handler.csv中
fake = faker.Faker()
handler = []
for i in range(10):
    handler.append(fake.name())
with open('handler.csv', 'w', newline='') as f:
    fieldnames = ['handler ID', 'handler name']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for i in range(10):
        writer.writerow({'handler ID': i+1, 'handler name': handler[i]})
