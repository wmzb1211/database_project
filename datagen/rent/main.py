import csv
from faker import Faker
import random
from datetime import timedelta, datetime
fake = Faker()

# Generate rental records
rental_records = []
carID = []
DailyRentalFee = []
compensation = []
with open('../car/cars.csv', 'r') as f:
    cars = list(csv.DictReader(f))
    # 获取carID和DailyRentalFee
    for car in cars:
        carID.append(car['CarID'])
        DailyRentalFee.append(car['DailyRentalFee'])
        compensation.append(car['Compensation'])

# 创建 faker 对象
fake = Faker()
# 生成2500个精确到日的随机日期
random_dates = [fake.date_between(start_date='-10y', end_date='today') for _ in range(2500)]
# 打印前十个日期示例
begin_date = sorted(random_dates)
# print(begin_date.__len__())
car_ID = [random.choice(carID) for _ in range(2500)]
# print(car_ID.__len__())
car_begin_date = []
for _ in range(200):
    car_begin_date.append([])
for i in range(2500):
    car_begin_date[int(car_ID[i])-1].append(begin_date[i])
for i in range(200):
    print(car_begin_date[i])
car_end_date = [[] for _ in range(200)]
end_date = []
for i in range(200):
    for j in range(len(car_begin_date[i])):
        # 生成随机的租车天数
        # 在car_begin_date[i][j]和car_begin_date[i][j+1]之间生成一个数
        flag = random.randint(0, 5)
        if flag == 0:
            # cancelled
            temp = car_begin_date[i][j]
        else:
            if j == len(car_begin_date[i])-1:
                temp = fake.date_between(start_date=car_begin_date[i][j], end_date=datetime.now()+timedelta(days=10))
                if datetime.combine(temp, datetime.min.time()) > datetime.now():
                    temp = None
            else:
                temp = fake.date_between(start_date=car_begin_date[i][j], end_date=car_begin_date[i][j+1])
        car_end_date[i].append(temp)
        end_date.append(temp)
# end_date = sorted(end_date)
# for i in range(len(car_end_date)):
#     print(i, car_end_date[i])
j = [0 for _ in range(200)]
for rental_id in range(1, 2501):  # Generate information for 2500 rentals
    temp2 = fake.date_between(start_date=begin_date[rental_id-1],
                              end_date=begin_date[rental_id-1]+timedelta(days=random.randint(5, 80)))
    # if rental_id == 201:
    #     print(int(car_ID[rental_id-1])-1)
    #     # print(car_end_date[int(car_ID[rental_id - 1]) - 1])
    #     print(j[int(car_ID[rental_id-1])-1])
    #     print('=====')
    #     print(car_end_date[int(car_ID[rental_id-1])-1][j[int(car_ID[rental_id-1])-1]])
    if car_end_date[int(car_ID[rental_id-1])-1][j[int(car_ID[rental_id-1])-1]] is None:
        payment = None
        status = 'Rented'
    elif temp2 > car_end_date[int(car_ID[rental_id-1])-1][j[int(car_ID[rental_id-1])-1]]:
        payment = int(DailyRentalFee[int(car_ID[rental_id-1])-1]) * (temp2 - begin_date[rental_id-1]).days
        flag = random.randint(0,5)
        if flag == 0:
            status = 'Broken'
            payment += int(compensation[int(car_ID[rental_id-1])-1])
        else:
            status = 'Done'
    else:
        payment = int(DailyRentalFee[int(car_ID[rental_id-1])-1]) * ((temp2 - begin_date[rental_id-1]).days +
                                                                     3 * (car_end_date[int(car_ID[rental_id-1])-1][j[int(car_ID[rental_id-1])-1]] - temp2).days)
        flag = random.randint(0, 5)
        if flag == 0:
            status = 'Broken'
            payment += int(compensation[int(car_ID[rental_id - 1]) - 1])
        else:
            status = 'Done'

    # if car_end_date[int(car_ID[rental_id-1])-1][j[int(car_ID[rental_id-1])-1]] is None:
    #     status = 'Rented'
    # elif datetime.combine(car_end_date[int(car_ID[rental_id - 1])-1][j[int(car_ID[rental_id-1])-1]], datetime.min.time()) == datetime.combine(begin_date[rental_id-1], datetime.min.time()):
    #     status = 'Cancelled'
    # else:
    #     status = 'Done'
    rental_record = {
        'rental ID': rental_id,
        'customer ID': random.randint(1, 500),
        'car ID': car_ID[rental_id-1],
        'rental begin date': begin_date[rental_id-1],
        'predict finish date': temp2,
        'actual finish date': str(car_end_date[int(car_ID[rental_id-1])-1][j[int(car_ID[rental_id-1])-1]]),  # Default to None
        'rental payment': payment,
        'status': status,
    }
    rental_records.append(rental_record)
    j[int(car_ID[rental_id-1])-1] += 1
    # print(rental_id)

# Write to CSV file
csv_file_path = 'rental_records.csv'
fieldnames = ['rental ID', 'customer ID', 'car ID', 'rental begin date', 'predict finish date', 'actual finish date', 'rental payment', 'status']

with open(csv_file_path, mode='w', newline='') as file:
    writer = csv.DictWriter(file, fieldnames=fieldnames)

    # Write header
    writer.writeheader()

    # Write data
    writer.writerows(rental_records)

print(f'Generated rental records have been saved to {csv_file_path}')
