# rental ID
import csv
return_ID = []
rent_ID = []
with open('../rent/rental_records.csv', 'r') as f:
    rental_records = list(csv.DictReader(f))
    i = 1
    for rental_record in rental_records:
        if rental_record['status'] == 'Done':
            return_ID.append(str(i))
            rent_ID.append(rental_record['rental ID'])

