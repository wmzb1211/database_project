import csv
import faker
import random
import datetime
rental_IDs = []
status = []
ViolationRecord = []
return_date = []
predict_finish_date = []
car_IDs = []
with open('../datagen/rent/rental_records.csv', 'r') as f2:
    rental_records = list(csv.DictReader(f2))
    for rental_record in rental_records:
        car_IDs.append(rental_record['car ID'])
        rental_IDs.append(rental_record['rental ID'])
        begin_date = rental_record['rental begin date']
        return_date.append(rental_record['actual finish date'])
        status.append(rental_record['status'])
        predict_finish_date.append(rental_record['predict finish date'])
        payment = rental_record['rental payment']
        # status有Done、Broken、Cancelled、Rented
Compensations = {}
dailyfee = {}
with open('../datagen/car/cars.csv', 'r') as f3:
    cars = list(csv.DictReader(f3))
    for car in cars:
        Compensations[int(car['CarID'])] = car['Compensation']
        dailyfee[int(car['CarID'])] = car['DailyRentalFee']
ViolationID = 1
for i in range(rental_IDs.__len__()):
    # Rdate = return_date[i].split('-')
    # Pdate = predict_finish_date[i].split('-')
    format_string = "%Y-%m-%d"
    if status[i] == 'Rented' or status[i] == 'Cancelled':
        continue
    else:
        Rdate = datetime.datetime.strptime(return_date[i], format_string)
        Pdate = datetime.datetime.strptime(predict_finish_date[i], format_string)
        if status[i] == 'Broken' or Rdate > Pdate:
            if Rdate > Pdate:
                penalty = int(dailyfee[int(car_IDs[i])]) * int((Rdate - Pdate).days) * 3
            else:
                penalty = int(Compensations[int(car_IDs[i])])
            ViolationRecords = {
                'violation ID': ViolationID,
                'rental ID': rental_IDs[i],
                'Violation type': 'Broken' if status[i] == 'Broken' else 'Overdue',
                'penalty': penalty
            }
            ViolationID += 1
            ViolationRecord.append(ViolationRecords)
with open('violation_records.csv', 'w', newline='') as f:
    fieldnames = ['violation ID', 'rental ID', 'Violation type', 'penalty']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for ViolationRecords in ViolationRecord:
        writer.writerow(ViolationRecords)
