import csv
from faker import Faker
import random

fake = Faker()

# Generate information for rentable cars
cars = []
carID_Status = ['' for _ in range(200)]
with open('../rent/rental_records.csv', 'r') as f2:
    rental_records = list(csv.DictReader(f2))

    for rental_record in rental_records:
        if rental_record['status'] == 'Done':
            carID_Status[int(rental_record['car ID'])-1] = rental_record['status']
i = 0
for carstatus in carID_Status:
    flag = random.randint(0, 5)
    if carstatus == 'Done':
        if flag == 0:
            carstatus = 'Under Repair'
        else:
            carstatus = 'Available'
    carID_Status[i] = carstatus
    i += 1

for car_id in range(1, 201):  # Generate information for 500 cars

    car = {
        'CarID': car_id,
        'ModelID': random.randint(1, 89),
        'LicensePlate': fake.random_int(min=1000, max=9999),
        'Color': fake.color_name(),
        'Year': fake.random_int(min=2000, max=2013),
        'Status': carID_Status[car_id-1],
        'DailyRentalFee': fake.random_int(min=50, max=200),
        'Compensation': fake.random_int(min=100000, max=500000),
    }
    cars.append(car)

# Write to CSV file
csv_file_path = 'cars.csv'
fieldnames = ['CarID', 'ModelID', 'LicensePlate', 'Color', 'Year', 'Status', 'DailyRentalFee', 'Compensation']

with open(csv_file_path, mode='w', newline='') as file:
    writer = csv.DictWriter(file, fieldnames=fieldnames)

    # Write header
    writer.writeheader()

    # Write data
    writer.writerows(cars)

print(f'Generated rentable car information has been saved to {csv_file_path}')
