import csv
import faker
import random
rental_IDs = []
status = []
ViolationRecord = []
return_date = []
with open('../rent/rental_records.csv', 'r') as f2:
    rental_records = list(csv.DictReader(f2))
    for rental_record in rental_records:
        rental_IDs.append(rental_record['rental ID'])
        return_date.append(rental_record['actual finish date']  )
        status.append(rental_record['status'])



returnID = 1
handlerNames = []
ReturnRecords = []
with open('../../handler/handler.csv', 'r') as fh:
    handler = list(csv.DictReader(fh))
    handlerIDs = []

    for h in handler:
        handlerIDs.append(h['handler ID'])
        handlerNames.append(h['handler name'])
for i in range(rental_IDs.__len__()):

    if status[i] == 'Done' or 'Broken':
        if status[i] == 'Done':
            temp = 'the order completed'
        else:
            temp = 'the car broken'
        ReturnRecord = {
            'return ID': returnID,
            'rental ID': rental_IDs[i],
            'Violation record': None if status[i] == 'Done' else 'Broken',
            'return date': return_date[i],
            'Vehicle condition description': temp,
            'Handler name': random.choice(handlerNames)

        }
        returnID += 1
        ReturnRecords.append(ReturnRecord)
with open('return_records.csv', 'w', newline='') as f:
    fieldnames = ['return ID', 'rental ID', 'Violation record', 'return date', 'Vehicle condition description', 'Handler name']
    writer = csv.DictWriter(f, fieldnames=fieldnames)
    writer.writeheader()
    for ReturnRecord in ReturnRecords:
        writer.writerow(ReturnRecord)