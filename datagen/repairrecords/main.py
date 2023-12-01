import faker
import random
import datetime
import csv
fake = faker.Faker()
# Generate repair records
repair_records = []
repair_ID = []
carID = []
repair_begin_date = []
repair_end_date = []
repair_description = []
des_list = ['Car paint peeling off', 'The bumper was knocked off', 'Tires need to be repaired', 'Damaged car headlights', 'Crash the whole car was scrapped']
dick = 0
with open('../rent/rental_records.csv', 'r') as f:
    i = 1
    rental_records = list(csv.DictReader(f))
    for rental_record in rental_records:
        # print(rental_records.index(rental_record))
        if rental_record['status'] == 'Broken':
            repair_ID.append(i)
            carID.append(rental_record['car ID'])
            repair_begin_date.append(rental_record['actual finish date'])
            begin_date = None
            for rental_record_j in rental_records[dick + 1:]:
                if rental_record_j['car ID'] == rental_record['car ID']:
                    begin_date = rental_record_j['rental begin date']
                    break
            if begin_date == None:
                flag = random.randint(0, 1)
                format_string = "%Y-%m-%d"

                # 将字符串转换为datetime对象
                datetime_object = datetime.datetime.strptime(rental_record['actual finish date'], format_string)
                if flag == 0:
                    repair_end_date.append(fake.date_between(start_date=datetime_object,
                                                             end_date=datetime.datetime.today()))
                else:
                    repair_end_date.append(None)
            else:
                format_string = "%Y-%m-%d"

                # 将字符串转换为datetime对象
                datetime_object = datetime.datetime.strptime(rental_record['actual finish date'], format_string)
                begin_date_object = datetime.datetime.strptime(begin_date, format_string)
                repair_end_date.append(fake.date_between(start_date=datetime_object, end_date=begin_date_object))

            des = random.choice(des_list)
            repair_description.append(des)
            i += 1
        dick += 1
print(len(repair_ID))

for i in range(len(repair_ID)):
    print(repair_ID[i], carID[i], repair_begin_date[i], repair_end_date[i], repair_description[i])
    repair_record = {
        'repair ID': repair_ID[i],
        'car ID': carID[i],
        'repair begin date': repair_begin_date[i],
        'repair end date': repair_end_date[i],
        'repair description': repair_description[i]

    }
    repair_records.append(repair_record)

with open('repairrecords.csv', 'w', newline='') as f:
    fieldnames = ['repair ID', 'car ID', 'repair begin date', 'repair end date', 'repair description']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for repair_record_ in repair_records:
        writer.writerow(repair_record_)

