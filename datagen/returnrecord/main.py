import csv
rental_IDs = []
status = []
ViolationRecord = []
with open('../rent/rental_records.csv', 'r') as f2:
    rental_records = list(csv.DictReader(f2))
    for rental_record in rental_records:
        rental_IDs.append(rental_record['rental ID'])
        status.append(rental_record['status'])



returnID = 1
for i in range(rental_IDs.__len__()):

    if status[i] == 'Done':
        ReturnRecords = {
            'return ID': returnID,
            'rental ID': rental_IDs[i],

        }
